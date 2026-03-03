package ug.daes.ra.service.iface.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ug.daes.ra.asserts.RAServiceAsserts;
import ug.daes.ra.dto.LogModelDTO;
import ug.daes.ra.enums.CertificateStatus;
import ug.daes.ra.enums.LogMessageType;
import ug.daes.ra.enums.ServiceName;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.model.*;
import ug.daes.ra.repository.iface.*;
import ug.daes.ra.request.entity.GenerateSignature;
import ug.daes.ra.request.entity.LogModel;
import ug.daes.ra.request.entity.PostRequest;
import ug.daes.ra.request.entity.RequestEntity;
import ug.daes.ra.response.entity.ServiceResponse;
import ug.daes.ra.service.iface.RALocalGenrateSignatureIface;
import ug.daes.ra.utils.Constant;
import ug.daes.ra.utils.NativeUtils;
import ug.daes.ra.utils.PropertiesConstants;
import ug.daes.ra.utils.KafkaSender;

import java.util.*;

@Service
public class RALocalGenrateSignatureIfaceImpl implements RALocalGenrateSignatureIface {

	@Value("${com.dt.pin.history.size}")
	private int pinHistorySize;

	private static final String CLASS = "RAPKIServiceIfaceImpl";
	private static final Logger logger =
			LoggerFactory.getLogger(RALocalGenrateSignatureIfaceImpl.class);

	private static String signingPin;
	private static String authenticationPin;
	private static List<String> pinList;
	private static Subscriber subscriber;
	private static LogModelDTO logModelDTO;
	private static SubscriberCertificates certificate;
	private static SubscriberCertificates subscriberCertificates;
	private static SubscriberCertificatePinHistory subscriberCertificatePinHistory;
	private static SubscriberWrappedKey subscriberWrappedKey;

	private final ObjectMapper objectMapper;
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

	public RALocalGenrateSignatureIfaceImpl(
			ObjectMapper objectMapper,
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

		this.objectMapper = objectMapper;
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
	public String generateSignatureForAgentSubscriber(LogModelDTO logModelDTO) throws RAServiceException, Exception {
		try {

			logger.info("{} :: generateSignatureForAgentSubscriber() :: request. {}",
					CLASS,
					logModelDTO);
			if (!Objects.isNull(logModelDTO)) {

				JsonNode jsonNode = objectMapper.readTree(logModelDTO.getCallStack());
				String suId = jsonNode.get("subscriberUniqueId").asText();
				String hashData = jsonNode.get("hash").asText();

				logger.info("generateSignatureForAgentSubscriber - suId: {}, hashData: {}",
						suId,
						hashData);
				if (suId != null) {
					GenerateSignature generateSignature = new GenerateSignature();

					logger.info("{} :: generateSignatureForAgentSubscriber() :: request. {}",
							CLASS,
							suId);
					subscriber = subscriberRepository.findBysubscriberUid(suId);
					RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);

					subscriberCertificates = subscriberCertificatesRepository
							.findByCertificateStatusAndsubscriberUniqueIdAndCertificateType(
									CertificateStatus.ACTIVE.toString(), suId, "SIGN");
					RAServiceAsserts.notNullorEmpty(subscriberCertificates, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);

					subscriberCertificatePinHistory = pinHistoryRepository
							.findBysubscriberUniqueId(subscriberCertificates.getSubscriberUniqueId());
					generateSignature.setKeyId(subscriberCertificates.getPkiKeyId());
					subscriberWrappedKey = subscriberWrappedKeyRepository
							.findBycertificateSerialNumber(subscriberCertificates.getCertificateSerialNumber());
					generateSignature.setWrappedKey(subscriberWrappedKey.getWrappedKey());
					generateSignature.setCertificate(subscriberCertificates.getCertificateData());
					generateSignature.setSerialNumber(subscriberCertificates.getCertificateSerialNumber());
					subscriberCertificatePinHistory = pinHistoryRepository
							.findBysubscriberUniqueId(subscriberCertificates.getSubscriberUniqueId());
					signingPin = subscriberCertificatePinHistory.getSigningCertificatePinList();
					pinList = new LinkedList<>(Arrays.asList(signingPin.split(", ")));
					generateSignature.setCurrentSigningPassword(pinList.get(pinList.size() - 1));
					generateSignature.setSubscriberUniqueId(suId);
					generateSignature.setHash(hashData);
					generateSignature.setHashData(true);

					logModelDTO.setCallStack(generateSignature.getGenerateSignatureData());

					PostRequest issueCertificatePostRequest = new PostRequest();
					issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
					issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());

					RequestEntity requestEntity = new RequestEntity();
					requestEntity.setPostRequest(issueCertificatePostRequest);
					requestEntity.setTransactionType(Constant.GENERATE_SIGNATURE);
					logger.info("{} :: generateSignatureForAgentSubscriber() :: request :: {}",
							CLASS,
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
							objectMapper.readValue(httpResponse.getBody(), ServiceResponse.class);
					logger.info("{} :: generateSignatureForAgentSubscriber() :: Native response :: {}",
							CLASS,
							serviceResponse.getStatus());
					logModelDTO.setCallStack(null);
					logModelDTO.setLogMessage(Constant.RESPONSE);
					logModelDTO.setEndTime(NativeUtils.getTimeStampString());
					if (serviceResponse.getStatus().equals(Constant.FAIL)) {
						ErrorCodes.setResponse(serviceResponse);
						logger.info("{} :: generateSignatureForAgentSubscriber() :: error :: {}",
								CLASS,
								serviceResponse.getError_message());
						logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
						logModelDTO.setServiceName(ServiceName.DIGITALLY_SIGNING_FAILED.toString());
						LogModel logModel = NativeUtils.getLogModel(logModelDTO);
						rabbitMQSender.send(logModel);
						throw new RAServiceException(serviceResponse.getError_message());
					}
					logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
					LogModel logModel = NativeUtils.getLogModel(logModelDTO);
					rabbitMQSender.send(logModel);
					return serviceResponse.getSignature();
				} else {
					throw new RAServiceException("Subscirber Id cant be null or empty");
				}
			} else {
				throw new RAServiceException("logModel cant be null or empty");
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public String generateSignatureForAgentOrganization(LogModelDTO logModelDTO) throws RAServiceException, Exception {
		try {
			if (!Objects.isNull(logModelDTO)) {

				JsonNode jsonNode = objectMapper.readTree(logModelDTO.getCallStack());
				String orgId = jsonNode.get("subscriberUniqueId").asText();
				String hashData = jsonNode.get("hash").asText();
				logger.info("generateSignatureForAgentOrganization :: {} hashData :: {}",
						orgId,
						hashData);
				if (orgId != null) {
					GenerateSignature generateSignature = new GenerateSignature();
					OrganizationDetails organizationDetails = organizationDetailsRepository
							.findByOrganizationUid(orgId);
					RAServiceAsserts.notNullorEmpty(organizationDetails, ErrorCodes.E_ORGANIZATION_DATA_NOT_FOUND);

					OrganizationCertificates organizationCertificates = organizationCertificatesRepository
							.findByCertificateStatusAndOrganizationUniqueId(CertificateStatus.ACTIVE.toString(), orgId);
					RAServiceAsserts.notNullorEmpty(organizationCertificates,
							ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);

					generateSignature.setKeyId(organizationCertificates.getPkiKeyId());

					OrganizationWrappedKey organizationWrappedKey = organizationWrappedKeyRepository
							.findBycertificateSerialNumber(organizationCertificates.getCertificateSerialNumber());

					generateSignature.setWrappedKey(organizationWrappedKey.getWrappedKey());
					generateSignature.setCertificate(organizationCertificates.getCertificateData());
					generateSignature.setSerialNumber(organizationCertificates.getCertificateSerialNumber());
					generateSignature.setSubscriberUniqueId(orgId);
					generateSignature.setHash(hashData);
					generateSignature.setHashData(true);

					logModelDTO.setCallStack(generateSignature.getGenerateSignatureData());

					PostRequest issueCertificatePostRequest = new PostRequest();
					issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
					issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());

					RequestEntity requestEntity = new RequestEntity();
					requestEntity.setPostRequest(issueCertificatePostRequest);
					requestEntity.setTransactionType(Constant.GENERATE_SIGNATURE);
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
							objectMapper.readValue(httpResponse.getBody(), ServiceResponse.class);
					logger.info(
							"RALocalGenrateSignatureIfaceImpl :: generateSignatureForAgentOrganization() :: Native response :: {}",
							serviceResponse.getStatus());
					logModelDTO.setCallStack(null);
					logModelDTO.setLogMessage(Constant.RESPONSE);
					logModelDTO.setEndTime(NativeUtils.getTimeStampString());
					if (serviceResponse.getStatus().equals(Constant.FAIL)) {
						ErrorCodes.setResponse(serviceResponse);
						logger.info(
								"RALocalGenrateSignatureIfaceImpl :: generateSignatureForAgentOrganization() :: error :: {}",
								serviceResponse.getError_message());
						logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
						logModelDTO.setServiceName(ServiceName.OTHER.toString());
						LogModel logModel = NativeUtils.getLogModel(logModelDTO);
						rabbitMQSender.send(logModel);
						String msg = ErrorCodes
								.getErrorMessage(ErrorCodes.getErrorCode(serviceResponse.getError_message()));
						if (msg == null) {
							throw new RAServiceException(
									"Something went wrong. (Code : " + serviceResponse.getError_code() + ")");
						}
						throw new RAServiceException(msg);
					}
					LogModel logModel = NativeUtils.getLogModel(logModelDTO);
					rabbitMQSender.send(logModel);
					return serviceResponse.getSignature();

				} else {
					throw new RAServiceException("Organization Id cant be null or empty");
				}

			} else {
				throw new RAServiceException("logModel cant be null or empty");
			}

		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " generateSignatureForAgentSubscriber() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

}
