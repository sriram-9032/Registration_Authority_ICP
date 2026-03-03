/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.service.iface.implementation;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.ra.asserts.RAServiceAsserts;
import ug.daes.ra.config.SecureUrlValidator;
import ug.daes.ra.dto.ApiResponses;
import ug.daes.ra.dto.LogModelDTO;
import ug.daes.ra.request.entity.SetPinModelDto;
import ug.daes.ra.enums.CertificateStatus;
import ug.daes.ra.enums.CertificateType;
import ug.daes.ra.enums.LogMessageType;
import ug.daes.ra.enums.ServiceName;
import ug.daes.ra.enums.SignatureType;
import ug.daes.ra.enums.TransactionType;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.model.OrganizationCertificates;
import ug.daes.ra.model.OrganizationDetails;
import ug.daes.ra.model.OrganizationWrappedKey;
import ug.daes.ra.model.Subscriber;
import ug.daes.ra.model.SubscriberCertificatePinHistory;
import ug.daes.ra.model.SubscriberCertificates;
import ug.daes.ra.model.SubscriberStatusModel;
import ug.daes.ra.model.SubscriberWrappedKey;
import ug.daes.ra.repository.iface.OrganizationCertificatesRepository;
import ug.daes.ra.repository.iface.OrganizationDetailsRepository;
import ug.daes.ra.repository.iface.OrganizationWrappedKeyRepository;
import ug.daes.ra.repository.iface.SubscriberCertificatePinHistoryRepository;
import ug.daes.ra.repository.iface.SubscriberCertificatesRepository;
import ug.daes.ra.repository.iface.SubscriberRepository;
import ug.daes.ra.repository.iface.SubscriberStatusRepository;
import ug.daes.ra.repository.iface.SubscriberWrappedKeyRepository;
import ug.daes.ra.request.entity.AuthenticatePKIModel;
import ug.daes.ra.request.entity.GenerateSignature;
import ug.daes.ra.request.entity.LogModel;
import ug.daes.ra.request.entity.PostRequest;
import ug.daes.ra.request.entity.RequestEntity;
import ug.daes.ra.request.entity.SetPinModel;
import ug.daes.ra.response.entity.ServiceResponse;
import ug.daes.ra.service.iface.RAPKIServiceIface;
import ug.daes.ra.utils.Constant;
import ug.daes.ra.utils.NativeUtils;
import ug.daes.ra.utils.PropertiesConstants;
import ug.daes.ra.utils.KafkaSender;


@Service
public class RAPKIServiceIfaceImpl implements RAPKIServiceIface {

	@Value("${com.dt.pin.history.size}")
	private int pinHistorySize;

	@Value("${url.unlocked.AuthResetPin}")
	private String urlunlockedAuthResetPin;


	private static final String CLASS = "RAPKIServiceIfaceImpl";


	private static final Logger logger = LoggerFactory.getLogger(RAPKIServiceIfaceImpl.class);


	private static final ObjectMapper objectMapper = new ObjectMapper();


	private static String signingPin;


	private static String authenticationPin;


	private static List<String> pinList;


	private static Subscriber subscriber;


	private static LogModelDTO logModelDTO;

	private static SubscriberCertificates certificate;


	private static List<SubscriberCertificates> subscriberCertificates;


	private static SubscriberCertificatePinHistory subscriberCertificatePinHistory;


	private static SubscriberWrappedKey subscriberWrappedKey;

	private static SecureUrlValidator secureUrlValidator;


private final KafkaSender rabbitMQSender;
	private final RestTemplate restTemplate;
	private final SubscriberRepository subscriberRepository;
	private final SubscriberCertificatesRepository subscriberCertificatesRepository;
	private final SubscriberCertificatePinHistoryRepository pinHistoryRepository;
	private final SubscriberStatusRepository subscriberStatusRepository;
	private final SubscriberWrappedKeyRepository subscriberWrappedKeyRepository;
	private final OrganizationDetailsRepository organizationDetailsRepository;
	private final OrganizationCertificatesRepository organizationCertificatesRepository;
	private final OrganizationWrappedKeyRepository organizationWrappedKeyRepository;
	private final MessageSource messageSource;

	public RAPKIServiceIfaceImpl(
			KafkaSender rabbitMQSender,
			RestTemplate restTemplate,
			SubscriberRepository subscriberRepository,
			SubscriberCertificatesRepository subscriberCertificatesRepository,
			SubscriberCertificatePinHistoryRepository pinHistoryRepository,
			SubscriberStatusRepository subscriberStatusRepository,
			SubscriberWrappedKeyRepository subscriberWrappedKeyRepository,
			OrganizationDetailsRepository organizationDetailsRepository,
			OrganizationCertificatesRepository organizationCertificatesRepository,
			OrganizationWrappedKeyRepository organizationWrappedKeyRepository,
			MessageSource messageSource) {

		this.rabbitMQSender = rabbitMQSender;
		this.restTemplate = restTemplate;
		this.subscriberRepository = subscriberRepository;
		this.subscriberCertificatesRepository = subscriberCertificatesRepository;
		this.pinHistoryRepository = pinHistoryRepository;
		this.subscriberStatusRepository = subscriberStatusRepository;
		this.subscriberWrappedKeyRepository = subscriberWrappedKeyRepository;
		this.organizationDetailsRepository = organizationDetailsRepository;
		this.organizationCertificatesRepository = organizationCertificatesRepository;
		this.organizationWrappedKeyRepository = organizationWrappedKeyRepository;
		this.messageSource = messageSource;
	}

@Override
public String setPin(SetPinModel setPinModel) throws Exception {
	try {
		logger.info("{} :: Pin history size :: {}", CLASS, pinHistorySize);
		logger.info("{} :: setPin() :: request :: {}", CLASS, setPinModel);

		// 1. Load context (Helper methods defined below)
		Subscriber subscriber = getSubscriber(setPinModel.getSubscriberUniqueId());
		SubscriberCertificates activeCert = findRequestedActiveCertificate(setPinModel);
		SubscriberCertificatePinHistory pinHistory = getPinHistory(subscriber.getSubscriberUid());

		// 2. Logging & Audit Preparation
		LogModelDTO logDto = prepareLogModel(subscriber, setPinModel);

		// 3. Validation based on operation type (Change/Reset/Set)
		validatePinOperation(setPinModel, activeCert, pinHistory);

		// 4. External Service Call (PKI)
		ServiceResponse pkiResponse = callPkiService(setPinModel, logDto, activeCert);

		// 5. Update Database Records
		finalizePinUpdate(setPinModel, activeCert, pinHistory, pkiResponse);

		return Constant.SUCCESS;

	} catch (JDBCConnectionException | ConstraintViolationException | DataException |
			 LockAcquisitionException | PessimisticLockException | QueryTimeoutException |
			 SQLGrammarException | GenericJDBCException e) {
		logger.error("{} :: Database Exception :: {}", CLASS, e.getMessage());
		throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH));
	} catch (RAServiceException e) {
		logger.error("{} :: Service Exception :: {}", CLASS, e.getMessage());
		throw e;
	}
}

// --- HELPER METHODS TO REDUCE COMPLEXITY ---

	private Subscriber getSubscriber(String suid) throws RAServiceException {
		Subscriber sub = subscriberRepository.findBysubscriberUid(suid);
		RAServiceAsserts.notNullorEmpty(sub, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
		return sub;
	}

	private SubscriberCertificates findRequestedActiveCertificate(SetPinModel model) throws RAServiceException {
		List<SubscriberCertificates> certificates = subscriberCertificatesRepository
				.findByCertificateStatusAndsubscriberUniqueId(CertificateStatus.ACTIVE.toString(), model.getSubscriberUniqueId());

		RAServiceAsserts.notNullorEmpty(certificates, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);

		for (SubscriberCertificates cert : certificates) {
			boolean isSignCert = model.getCertType() == 0 && CertificateType.SIGN.toString().equals(cert.getCertificateType());
			boolean isAuthCert = model.getCertType() == 1 && CertificateType.AUTH.toString().equals(cert.getCertificateType());

			if (isSignCert || isAuthCert) {
				return cert;
			}
		}
		throw new RAServiceException(ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
	}

	private SubscriberCertificatePinHistory getPinHistory(String suid) {
		SubscriberCertificatePinHistory history = pinHistoryRepository.findBysubscriberUniqueId(suid);
		if (history == null) {
			history = new SubscriberCertificatePinHistory();
			history.setSubscriberUniqueId(suid);
		}
		return history;
	}

	private LogModelDTO prepareLogModel(Subscriber subscriber, SetPinModel model) {
		LogModelDTO dto = new LogModelDTO();
		dto.setStartTime(NativeUtils.getTimeStampString());
		dto.setIdentifier(subscriber.getSubscriberUid());
		dto.setLogMessage(Constant.REQUEST);
		dto.setLogMessageType(LogMessageType.INFO.toString());
		dto.setTransactionType(TransactionType.BUSINESS.toString());
		dto.setCorrelationID(NativeUtils.getUUId());
		dto.setTransactionID(NativeUtils.getUUId());
		dto.setGeoLocation(model.getGeoLocation());
		dto.seteSealUsed(false);

		// Map the call stack based on logic in your original snippet
		dto.setCallStack(model.isChangePin() ? model.getSettingPin() : model.getChangePin());
		return dto;
	}

	private void validatePinOperation(SetPinModel model, SubscriberCertificates cert, SubscriberCertificatePinHistory history) throws RAServiceException {
		boolean isSign = (model.getCertType() == 0);
		String currentPins = isSign ? history.getSigningCertificatePinList() : history.getAuthenticationCertificatePinList();
		String otherPins = isSign ? history.getAuthenticationCertificatePinList() : history.getSigningCertificatePinList();

		if (model.isResetPIN() || model.isChangePin()) {
			// Ensure PIN exists before trying to change/reset it
			RAServiceAsserts.notNullorEmpty(currentPins, isSign ? ErrorCodes.E_SIGNING_CERTIFICATE_PIN_NOT_SET : ErrorCodes.E_AUTHENTICATION_CERTIFICATE_PIN_NOT_SET);

			// Uniqueness checks
			validatePasswordUniqueness(model, currentPins, otherPins);

			if (model.isChangePin()) {
				NativeUtils.checkOldCurrentPasswords(currentPins, model.getOldSigningPassword(),
						isSign ? ErrorCodes.E_SIGNING_PIN_NOT_MATCHED : ErrorCodes.E_AUTH_PIN_NOT_MATCHED);
			}

			if (model.isResetPIN() && currentPins != null) {
				List<String> list = Arrays.asList(currentPins.split(", "));
				model.setCurrentSigningPassword(list.get(list.size() - 1));
			}
		}
	}

	private void validatePasswordUniqueness(SetPinModel model, String current, String other) throws RAServiceException {
		boolean isSign = (model.getCertType() == 0);
		if (current != null) {
			NativeUtils.checkOldPasswords(current, model.getSigningPassword(),
					isSign ? ErrorCodes.E_NEW_SIGNING_PIN_MATCHED_WITH_OLD_SIGNING_PIN : ErrorCodes.E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_OLD_AUTHENTICATION_PIN);
		}
		if (other != null) {
			NativeUtils.checkCurrentPassword(other, model.getSigningPassword(),
					isSign ? ErrorCodes.E_NEW_SIGNING_PIN_MATCHED_WITH_CURRENT_AUTHENTICATION_PIN : ErrorCodes.E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_CURRENT_SIGNING_PIN);
		}
	}

	private ServiceResponse callPkiService(SetPinModel model, LogModelDTO logDto, SubscriberCertificates cert) throws Exception {
		RequestEntity request = new RequestEntity();
		PostRequest post = new PostRequest();
		post.setRequestBody(logDto.toString());
		post.setHashdata(logDto.toString().hashCode());
		request.setPostRequest(post);
		request.setTransactionType("SetPin");

		logger.info("{} :: PKI Call :: {}", CLASS, post.getRequestBody());
		ResponseEntity<String> response = restTemplate.postForEntity(PropertiesConstants.getPkiUrl(), request, String.class);
		String body = response.getBody();

		if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(body)) throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
		if (Constant.REQUEST_IS_NOT_VALID.equals(body)) throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);

		ServiceResponse sr = objectMapper.readValue(body, ServiceResponse.class);
		if (Constant.FAIL.equals(sr.getStatus())) {
			throw new RAServiceException(sr.getError_message());
		}
		return sr;
	}

	private void finalizePinUpdate(SetPinModel model, SubscriberCertificates cert, SubscriberCertificatePinHistory history, ServiceResponse sr) throws Exception {
		boolean isSign = (model.getCertType() == 0);
		String currentList = isSign ? history.getSigningCertificatePinList() : history.getAuthenticationCertificatePinList();

		// Determine if we are performing a Reset, Change, or Initial Set
		boolean isOperationRequiringHistory = model.isResetPIN() || model.isChangePin();
		String newList = updatePinListHistory(currentList, model.getSigningPassword(), isOperationRequiringHistory);

		if (isSign) {
			history.setSigningCertificatePinList(newList);
			history.setSignPinSetDate(NativeUtils.getTimeStamp());
		} else {
			if (model.isResetPIN()) unlockedAuthResetPin(model.getSubscriberUniqueId());
			history.setAuthenticationCertificatePinList(newList);
			history.setAuthPinSetDate(NativeUtils.getTimeStamp());
		}

		pinHistoryRepository.save(history);

		// Update Wrapped Key
		SubscriberWrappedKey wk = subscriberWrappedKeyRepository.findBycertificateSerialNumber(cert.getCertificateSerialNumber());
		if (wk != null) {
			wk.setWrappedKey(sr.getWrappedKey());
			subscriberWrappedKeyRepository.save(wk);
		}

		// Only for Initial Set (not Reset/Change), update status if both pins exist
		if (!model.isResetPIN() && !model.isChangePin()) {
			checkAndUpdateSubscriberStatus(model, history);
		}

		logger.info("{} :: Operation Success.", CLASS);
	}

	private String updatePinListHistory(String currentList, String newPin, boolean isHistoryOp) {
		if (currentList == null || currentList.isEmpty()) return newPin;

		List<String> pins = new LinkedList<>(Arrays.asList(currentList.split(", ")));

		if (isHistoryOp && pins.size() >= pinHistorySize) {
			pins.remove(0);
		}
		pins.add(newPin);
		return String.join(", ", pins);
	}

	private void checkAndUpdateSubscriberStatus(SetPinModel model, SubscriberCertificatePinHistory history) throws ParseException {
		if (history.getSignPinSetDate() != null && history.getAuthPinSetDate() != null) {
			SubscriberStatusModel status = subscriberStatusRepository.findBysubscriberUid(model.getSubscriberUniqueId());
			if (status != null) {
				status.setSubscriberStatus(Constant.SUBSCRIBER_STATUS);
				status.setUpdatedDate(NativeUtils.getTimeStamp());
				status.setSubscriberStatusDescription(Constant.SET_PIN_SUCCESS);
				subscriberStatusRepository.save(status);
			}
		}
	}

	public void unlockedAuthResetPin(String suid) throws Exception {
		try {
			logger.info("unlockedAuthResetPin() invoked for subscriberId: {}", suid);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

			String unlockAuthPinUrl = urlunlockedAuthResetPin + "/" + suid;
			secureUrlValidator.validate(unlockAuthPinUrl);

			ResponseEntity<ApiResponses> res = restTemplate.exchange(
					unlockAuthPinUrl, HttpMethod.POST, requestEntity, ApiResponses.class);

			HttpStatusCode statusCode = res.getStatusCode();  // returns HttpStatusCode
			logger.info("unlockedAuthResetPin() response status: {}", statusCode.value());
			handleResetPinStatus(statusCode.value(), suid);

		} catch (HttpServerErrorException ex) {
			int statusCode = ex.getStatusCode().value();
			logger.info("unlockedAuthResetPin() :: HttpServerErrorException with status {}", statusCode);
			handleResetPinStatus(statusCode, suid);

		} catch (HttpClientErrorException ex) {
			int statusCode = ex.getStatusCode().value();
			logger.info("unlockedAuthResetPin() :: HttpClientErrorException with status {}", statusCode);
			handleResetPinStatus(statusCode, suid);

		} catch (Exception e) {
			logger.error("unlockedAuthResetPin() :: unexpected exception", e);
			handleResetPinStatus(0, suid);
		}
	}
	private void handleResetPinStatus(int statusCode, String suid) {
		String message;
		switch (statusCode) {
			case 400: message = "Invalid request"; break;
			case 401: message = "Access denied - unauthorized"; break;
			case 403: message = "Access denied - forbidden"; break;
			case 404: message = "Resource not found"; break;
			case 415: message = "Unsupported content type"; break;
			case 500: message = "Server error"; break;
			case 501: message = "Service unavailable"; break;
			default:  message = "Unexpected error"; break;
		}

		// ✅ Replace deprecated resetAuthPinlog with new method
		auditAuthPinReset(statusCode, suid);

		logger.info("unlockedAuthResetPin() :: {}", message);
	}
	private void auditAuthPinReset(int statusCode, String suid) {
		try {
			String message = "RESET_AUTH_PIN_LOCKED | " + statusCode;
			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setIdentifier(suid);
			logModelDTO.setServiceName(ServiceName.OTHER.toString());
			logModelDTO.setLogMessage(message);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setCorrelationID(NativeUtils.getUUId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.seteSealUsed(false);

			LogModel logModel = NativeUtils.getLogModel(logModelDTO);
			rabbitMQSender.send(logModel);
		} catch (Exception e) {
			logger.error("auditAuthPinReset() :: Unexpected exception", e);
		}
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.dtt.ra.service.iface.RAPKIServiceIface#generateSignature(com.dtt.ra.
	 * request.entity.GenerateSignature)
	 */
	@Override
	public String generateSignature(GenerateSignature generateSignature) throws RAServiceException, Exception {
		try {
			logger.info("generateSignature() request initiated for subscriberId: {}", generateSignature);
			subscriber = subscriberRepository.findBysubscriberUid(generateSignature.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			boolean result = false;
			logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setIdentifier(subscriber.getSubscriberUid());
			logModelDTO.setLogMessage(Constant.REQUEST);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setServiceName(ServiceName.OTHER.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(generateSignature.getCorrelationId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(null);
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(SignatureType.DATA.toString());
			logModelDTO.seteSealUsed(false);
			subscriberCertificates = subscriberCertificatesRepository.findByCertificateStatusAndsubscriberUniqueId(
					CertificateStatus.ACTIVE.toString(), generateSignature.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriberCertificates.size(), ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			for (SubscriberCertificates subscriberCertificate : subscriberCertificates) {
				boolean isAuth = generateSignature.getCertType() == 1
						&& CertificateType.AUTH.toString().equals(subscriberCertificate.getCertificateType());
				boolean isSign = generateSignature.getCertType() == 0
						&& CertificateType.SIGN.toString().equals(subscriberCertificate.getCertificateType());

				if (isAuth || isSign) {
					subscriberCertificatePinHistory = pinHistoryRepository
							.findBysubscriberUniqueId(subscriberCertificate.getSubscriberUniqueId());

					generateSignature.setKeyId(subscriberCertificate.getPkiKeyId());

					subscriberWrappedKey = subscriberWrappedKeyRepository
							.findBycertificateSerialNumber(subscriberCertificate.getCertificateSerialNumber());
					generateSignature.setWrappedKey(subscriberWrappedKey.getWrappedKey());
					generateSignature.setCertificate(subscriberCertificate.getCertificateData());
					generateSignature.setSerialNumber(subscriberCertificate.getCertificateSerialNumber());

					String pinListString = isAuth
							? subscriberCertificatePinHistory.getAuthenticationCertificatePinList()
							: subscriberCertificatePinHistory.getSigningCertificatePinList();

					pinList = new LinkedList<>(Arrays.asList(pinListString.split(", ")));
					generateSignature.setCurrentSigningPassword(pinList.get(pinList.size() - 1));

					result = true;
					break;
				}
			}
			if (!result)
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);

			logModelDTO.setCallStack(generateSignature.getGenerateSignatureData());

			PostRequest issueCertificatePostRequest = new PostRequest();
			issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
			issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());

			RequestEntity requestEntity = new RequestEntity();
			requestEntity.setPostRequest(issueCertificatePostRequest);
			requestEntity.setTransactionType(Constant.GENERATE_SIGNATURE);
			logger.info("generateSignature() request initiated for subscriberId: {}",
					requestEntity.getPostRequest().getRequestBody());
			ResponseEntity<String> httpResponse = restTemplate.postForEntity(PropertiesConstants.getPkiUrl(), requestEntity,
					String.class);
			if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody())) {
				throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
			}
			if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody())) {
				throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
			}
			String responseBody = httpResponse.getBody();
			ServiceResponse serviceResponse =
					objectMapper.readValue(responseBody, ServiceResponse.class);
			logger.info("generateSignature() :: Native response :: {}", serviceResponse.getStatus());
			logModelDTO.setCallStack(null);
			logModelDTO.setLogMessage(Constant.GENERATE_SIGNATURE);
			logModelDTO.setEndTime(NativeUtils.getTimeStampString());
			if (serviceResponse.getStatus().equals(Constant.FAIL)) {
				ErrorCodes.setResponse(serviceResponse);
				logger.error("generateSignature() failed with errorCode: {}", serviceResponse.getError_message());
				logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
				logModelDTO.setServiceName(ServiceName.DIGITALLY_SIGNING_FAILED.toString());
				LogModel logModel = NativeUtils.getLogModel(logModelDTO);

				throw new RAServiceException(serviceResponse.getError_message());
			}
			logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
			LogModel logModel = NativeUtils.getLogModel(logModelDTO);

			return serviceResponse.getSignature();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " setPin() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new RAServiceException(e.getMessage());

		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " setPin() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " setPin() :: exception {} ", e.getMessage());
			throw new RAServiceException(e.getMessage());

		}
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dtt.ra.service.iface.RAPKIServiceIface#generateSignatureOrganiztion(com.
	 * dtt.ra. request.entity.GenerateSignature)
	 */
	@Override
	public String generateSignatureOrganization(GenerateSignature generateSignature)
			throws RAServiceException, Exception {
		try {
			logger.info("generateSignatureOrganization() request initiated for subscriberId: {}",
					generateSignature.getSubscriberUniqueId());
			OrganizationDetails organizationDetails = organizationDetailsRepository
					.findByOrganizationUid(generateSignature.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(organizationDetails, ErrorCodes.E_ORGANIZATION_DATA_NOT_FOUND);

			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setIdentifier(organizationDetails.getOrganizationUid());
			logModelDTO.setLogMessage(Constant.REQUEST);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setServiceName(ServiceName.OTHER.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(generateSignature.getCorrelationId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(null);
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(SignatureType.DATA.toString());
			logModelDTO.seteSealUsed(false);

			OrganizationCertificates organizationCertificates = organizationCertificatesRepository
					.findByCertificateStatusAndOrganizationUniqueId(CertificateStatus.ACTIVE.toString(),
							generateSignature.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(organizationCertificates, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			if ((generateSignature.getCertType() == 0)
					&& (organizationCertificates.getCertificateType().equals(CertificateType.SIGN.toString()))) {
				generateSignature.setKeyId(organizationCertificates.getPkiKeyId());

				OrganizationWrappedKey organizationWrappedKey = organizationWrappedKeyRepository
						.findBycertificateSerialNumber(organizationCertificates.getCertificateSerialNumber());

				generateSignature.setWrappedKey(organizationWrappedKey.getWrappedKey());
				generateSignature.setCertificate(organizationCertificates.getCertificateData());
				generateSignature.setSerialNumber(organizationCertificates.getCertificateSerialNumber());

				logModelDTO.setCallStack(generateSignature.getGenerateSignatureData());

				PostRequest issueCertificatePostRequest = new PostRequest();
				issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
				issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());

				RequestEntity requestEntity = new RequestEntity();
				requestEntity.setPostRequest(issueCertificatePostRequest);
				requestEntity.setTransactionType(Constant.GENERATE_SIGNATURE);
				ResponseEntity<String> httpResponse = restTemplate.postForEntity(PropertiesConstants.getPkiUrl(),
						requestEntity, String.class);
				String responseBody = httpResponse.getBody();

				if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(responseBody)) {
					throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
				}

				if (Constant.REQUEST_IS_NOT_VALID.equals(responseBody)) {
					throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
				}
				ServiceResponse serviceResponse = objectMapper.readValue(responseBody, ServiceResponse.class);
				logger.info("generateSignatureOrganization() :: Native response :: {}",
						serviceResponse.getStatus());
				logModelDTO.setCallStack(null);
				logModelDTO.setLogMessage(Constant.GENERATE_SIGNATURE);
				logModelDTO.setEndTime(NativeUtils.getTimeStampString());
				if (serviceResponse.getStatus().equals(Constant.FAIL)) {
					ErrorCodes.setResponse(serviceResponse);
					logger.error("generateSignatureOrganization() :: error :: {}",
							serviceResponse.getError_message());
					logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
					logModelDTO.setServiceName(ServiceName.OTHER.toString());
					LogModel logModel = NativeUtils.getLogModel(logModelDTO);

					String msg = ErrorCodes
							.getErrorMessage(ErrorCodes.getErrorCode(serviceResponse.getError_message()));
					if (msg == null) {
						throw new RAServiceException(
								"Something went wrong. (Code : " + serviceResponse.getError_code() + ")");
					}
					throw new RAServiceException(msg);
				}
				LogModel logModel = NativeUtils.getLogModel(logModelDTO);

				return serviceResponse.getSignature();
			} else {
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureOrganization() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureOrganization() :: IN RAServiceException{}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureOrganization() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dtt.ra.service.iface.RAPKIServiceIface#authenticatePKI(com.dtt.ra.request
	 * .entity.AuthenticatePKIModel)
	 */
	@Override
	public String authenticatePKI(AuthenticatePKIModel authenticatePKIModel) throws RAServiceException, Exception {
		try {
			logger.info(CLASS + " :: authenticatePKI() :: request.");
			subscriber = subscriberRepository.findBysubscriberUid(authenticatePKIModel.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			boolean result = false;
			logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setIdentifier(subscriber.getSubscriberUid());
			logModelDTO.setServiceName(ServiceName.PKI_AUTHENTICATED.toString());
			logModelDTO.setLogMessage(Constant.REQUEST);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(authenticatePKIModel.getCorrelationId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(null);
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(null);
			logModelDTO.seteSealUsed(false);
			subscriberCertificates = subscriberCertificatesRepository.findByCertificateStatusAndsubscriberUniqueId(
					CertificateStatus.ACTIVE.toString(), authenticatePKIModel.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriberCertificates.size(), ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			for (SubscriberCertificates subscriberCertificate : subscriberCertificates) {
				boolean isAuth = authenticatePKIModel.getCertType() == 1
						&& CertificateType.AUTH.toString().equals(subscriberCertificate.getCertificateType());
				boolean isSign = authenticatePKIModel.getCertType() == 0
						&& CertificateType.SIGN.toString().equals(subscriberCertificate.getCertificateType());

				if (isAuth || isSign) {
					authenticatePKIModel.setKeyId(subscriberCertificate.getPkiKeyId());
					authenticatePKIModel.setCertificate(subscriberCertificate.getCertificateData());
					result = true;
					break;
				}
			}
			if (!result)
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);

			logModelDTO.setCallStack(authenticatePKIModel.getauthenticatePKIData());
			PostRequest issueCertificatePostRequest = new PostRequest();
			issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
			issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());

			RequestEntity requestEntity = new RequestEntity();
			requestEntity.setPostRequest(issueCertificatePostRequest);
			requestEntity.setTransactionType(Constant.AUTHENTICATE_PKI);
			logger.info("authenticatePKI() request sent for subscriberId: {}",
					requestEntity.getPostRequest().getRequestBody());

			ResponseEntity<String> httpResponse =
					restTemplate.postForEntity(PropertiesConstants.getPkiUrl(),
							requestEntity,
							String.class);

			String responseBody = httpResponse.getBody();

			if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(responseBody)) {
				throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
			}

			if (Constant.REQUEST_IS_NOT_VALID.equals(responseBody)) {
				throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
			}

			ServiceResponse serviceResponse =
					objectMapper.readValue(responseBody, ServiceResponse.class);
			logger.info("authenticatePKI() :: Native response :: {}",
					serviceResponse.getStatus());
			logModelDTO.setCallStack(null);
			logModelDTO.setLogMessage(Constant.RESPONSE);
			logModelDTO.setEndTime(NativeUtils.getTimeStampString());
			if (serviceResponse.getStatus().equals(Constant.FAIL)) {
				ErrorCodes.setResponse(serviceResponse);
				logger.error("authenticatePKI :: error :: {}",
						serviceResponse.getError_message());
				logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
				LogModel logModel = NativeUtils.getLogModel(logModelDTO);
				rabbitMQSender.send(logModel);
				throw new RAServiceException(serviceResponse.getError_message());
			}
			logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
			LogModel logModel = NativeUtils.getLogModel(logModelDTO);
			rabbitMQSender.send(logModel);
			return serviceResponse.getStatus();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " authenticatePKI() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " authenticatePKI() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " authenticatePKI() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String setPins(SetPinModelDto setPinModel) throws Exception {
		try {
			logger.info("{} :: setPin() :: request :: {}", CLASS, setPinModel);

			// Validate subscriber exists
			getSubscriber(setPinModel);

			List<SubscriberCertificates> certificates = getActiveCertificates(setPinModel);
			RAServiceAsserts.notNullorEmpty(certificates.size(), ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);

			for (SubscriberCertificates certificate : certificates) {
				processCertificate(setPinModel, certificate);
			}

			return Constant.SUCCESS;

		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("{} :: DATABASE EXCEPTION :: {}", CLASS, e.getMessage(), e);
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("{} :: RAServiceException :: {}", CLASS, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("{} :: General Exception :: {}", CLASS, e.getMessage(), e);
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	private void processCertificate(SetPinModelDto dto, SubscriberCertificates certificate) throws RAServiceException {
		if (CertificateType.SIGN.toString().equals(certificate.getCertificateType())) {
			handleSignCertificate(dto, certificate);
		} else if (CertificateType.AUTH.toString().equals(certificate.getCertificateType())) {
			handleAuthCertificate(dto, certificate);
		}
	}

	private void handleSignCertificate(SetPinModelDto dto, SubscriberCertificates certificate) throws RAServiceException {
		validateSignPin(dto, certificate);
		SubscriberCertificatePinHistory history = updatePinHistoryForSign(dto);
		updateWrappedKey(certificate);

		// Only update status if BOTH pins are now present (matches original logic)
		if (history.getSignPinSetDate() != null && history.getAuthPinSetDate() != null) {
			updateSubscriberStatus(dto);
		}
		logger.info("{} :: setPin(SIGN) :: success", CLASS);
	}

	private void handleAuthCertificate(SetPinModelDto dto, SubscriberCertificates certificate) throws RAServiceException {
		validateAuthPin(dto, certificate);
		SubscriberCertificatePinHistory history = updatePinHistoryForAuth(dto);
		updateWrappedKey(certificate);

		// Only update status if BOTH pins are now present (matches original logic)
		if (history.getSignPinSetDate() != null && history.getAuthPinSetDate() != null) {
			updateSubscriberStatus(dto);
		}
		logger.info("{} :: setPin(AUTH) :: success", CLASS);
	}

	private void validateSignPin(SetPinModelDto dto, SubscriberCertificates certificate) throws RAServiceException {
		SubscriberCertificatePinHistory history = pinHistoryRepository.findBysubscriberUniqueId(dto.getSubscriberUniqueId());
		if (history != null && history.getAuthenticationCertificatePinList() != null) {
			NativeUtils.checkCurrentPassword(
					history.getAuthenticationCertificatePinList(),
					dto.getSigningPassword(),
					ErrorCodes.E_NEW_SIGNING_PIN_MATCHED_WITH_CURRENT_AUTHENTICATION_PIN
			);
		}
	}

	private void validateAuthPin(SetPinModelDto dto, SubscriberCertificates certificate) throws RAServiceException {
		SubscriberCertificatePinHistory history = pinHistoryRepository.findBysubscriberUniqueId(dto.getSubscriberUniqueId());
		if (history != null && history.getSigningCertificatePinList() != null) {
			NativeUtils.checkCurrentPassword(
					history.getSigningCertificatePinList(),
					dto.getAuthPassword(),
					ErrorCodes.E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_CURRENT_SIGNING_PIN
			);
		}
	}

	private SubscriberCertificatePinHistory updatePinHistoryForSign(SetPinModelDto dto) {
		SubscriberCertificatePinHistory history = pinHistoryRepository.findBysubscriberUniqueId(dto.getSubscriberUniqueId());
		if (history == null) history = new SubscriberCertificatePinHistory();

		history.setSubscriberUniqueId(dto.getSubscriberUniqueId());
		history.setSigningCertificatePinList(dto.getSigningPassword());

		// Use consistent date logic
		Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		history.setSignPinSetDate(now);

		return pinHistoryRepository.save(history);
	}

	private SubscriberCertificatePinHistory updatePinHistoryForAuth(SetPinModelDto dto) {
		SubscriberCertificatePinHistory history = pinHistoryRepository.findBysubscriberUniqueId(dto.getSubscriberUniqueId());
		if (history == null) history = new SubscriberCertificatePinHistory();

		history.setSubscriberUniqueId(dto.getSubscriberUniqueId());
		history.setAuthenticationCertificatePinList(dto.getAuthPassword());

		Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		history.setAuthPinSetDate(now);

		return pinHistoryRepository.save(history);
	}

	private void updateWrappedKey(SubscriberCertificates certificate) {
		SubscriberWrappedKey wrappedKey = subscriberWrappedKeyRepository.findBycertificateSerialNumber(certificate.getCertificateSerialNumber());
		if (wrappedKey != null) {
			logger.debug("Updating Wrapped Key for SN: {}", wrappedKey.getCertificateSerialNumber());
			// Original code just saved the object back to the DB to update timestamps/triggers
			subscriberWrappedKeyRepository.save(wrappedKey);
		}
	}

	private void updateSubscriberStatus(SetPinModelDto dto) {
		SubscriberStatusModel status = subscriberStatusRepository.findBysubscriberUid(dto.getSubscriberUniqueId());
		if (status != null) {
			try {
				status.setUpdatedDate(NativeUtils.getTimeStamp());
				status.setSubscriberStatus(Constant.SUBSCRIBER_STATUS);
				status.setSubscriberStatusDescription(Constant.SET_PIN_SUCCESS);
				subscriberStatusRepository.save(status);
			} catch (Exception e) {
				logger.error("Failed to update subscriber status", e);
			}
		}
	}

	private Subscriber getSubscriber(SetPinModelDto dto) throws RAServiceException {
		Subscriber subscriber = subscriberRepository.findBysubscriberUid(dto.getSubscriberUniqueId());
		RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
		return subscriber;
	}

	private List<SubscriberCertificates> getActiveCertificates(SetPinModelDto dto) {
		return subscriberCertificatesRepository.findByCertificateStatusAndsubscriberUniqueId(
				CertificateStatus.ACTIVE.toString(), dto.getSubscriberUniqueId());
	}


}
