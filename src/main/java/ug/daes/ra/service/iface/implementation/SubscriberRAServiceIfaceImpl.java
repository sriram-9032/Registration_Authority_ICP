/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.service.iface.implementation;

import java.math.BigInteger;
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
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.ra.asserts.RAServiceAsserts;
import ug.daes.ra.config.SecureUrlValidator;
import ug.daes.ra.dto.*;
import ug.daes.ra.dto.ApiResponses;
import ug.daes.ra.enums.LogMessageType;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.model.*;
import ug.daes.ra.model.SubscriberStatusModel;
import ug.daes.ra.repository.iface.SubscriberCertificatesRepository;
import ug.daes.ra.repository.iface.SubscriberCompleteDetailsForAssistedRepo;
import ug.daes.ra.repository.iface.SubscriberCompleteDetailsRepositoy;
import ug.daes.ra.repository.iface.SubscriberDeviceRepository;
import ug.daes.ra.repository.iface.SubscriberRepository;
import ug.daes.ra.repository.iface.SubscriberStatusRepository;
import ug.daes.ra.request.entity.*;
import ug.daes.ra.response.entity.ApiResponse_CertCount;
import ug.daes.ra.response.entity.DashboardDetails;
import ug.daes.ra.response.entity.Response;
import ug.daes.ra.response.entity.ServiceResponse;
import ug.daes.ra.service.iface.SubscriberRAServiceIface;
import ug.daes.ra.utils.AppUtil;
import ug.daes.ra.utils.Constant;
import ug.daes.ra.utils.NativeUtils;
import ug.daes.ra.utils.PropertiesConstants;

/**
 * The Class RASubscriberServiceIfaceImpl.
 */

@Service
public class SubscriberRAServiceIfaceImpl implements SubscriberRAServiceIface {

	private static final String CLASS = "SubscriberRAServiceIfaceImpl";
	private static final Logger logger = LoggerFactory.getLogger(SubscriberRAServiceIfaceImpl.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static SecureUrlValidator secureUrlValidator;
	@Value(value = "${ugpass.code}")
	private boolean ugPassCode;

	private final RAServiceIfaceImpl raServiceIfaceImpl;
	private final SubscriberStatusRepository subscriberStatusRepository;
	private final SubscriberRepository subscriberRepository;
	private final SubscriberCompleteDetailsRepositoy subscriberCompleteDetailsRepositoy;
	private final SubscriberCompleteDetailsForAssistedRepo subscriberCompleteDetailsForAssistedRepo;
	private final SubscriberDeviceRepository subscriberDeviceRepository;
	private final SubscriberCertificatesRepository subscriberCertificatesRepository;
	private final RestTemplate restTemplate;
	private final MessageSource messageSource;
	private final RAPKIServiceIfaceImpl rapkiServiceIfaceImpl;
	public SubscriberRAServiceIfaceImpl(
			RAServiceIfaceImpl raServiceIfaceImpl,
			SubscriberStatusRepository subscriberStatusRepository,
			SubscriberRepository subscriberRepository,
			SubscriberCompleteDetailsRepositoy subscriberCompleteDetailsRepositoy,
			SubscriberCompleteDetailsForAssistedRepo subscriberCompleteDetailsForAssistedRepo,
			SubscriberDeviceRepository subscriberDeviceRepository,
			SubscriberCertificatesRepository subscriberCertificatesRepository,
			RestTemplate restTemplate,
			MessageSource messageSource,
			RAPKIServiceIfaceImpl rapkiServiceIfaceImpl) {
		this.raServiceIfaceImpl = raServiceIfaceImpl;
		this.subscriberStatusRepository = subscriberStatusRepository;
		this.subscriberRepository = subscriberRepository;
		this.subscriberCompleteDetailsRepositoy = subscriberCompleteDetailsRepositoy;
		this.subscriberCompleteDetailsForAssistedRepo = subscriberCompleteDetailsForAssistedRepo;
		this.subscriberDeviceRepository = subscriberDeviceRepository;
		this.subscriberCertificatesRepository = subscriberCertificatesRepository;
		this.restTemplate = restTemplate;
		this.messageSource = messageSource;
		this.rapkiServiceIfaceImpl = rapkiServiceIfaceImpl;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.RASubscriberServiceIface#
	 * getSubscriberDetailsBySubscriberDigitalId(java.lang.String)
	 */
	@Override
	public String getSubscriberDetailsBySearchType(int searchType, String searchValue)
			throws RAServiceException, Exception {
		try {
			logger.info(
					"{} :: getSubscriberDetailsBySearchType() :: req :: searchType and searchValue :: {} | {}",
					CLASS,
					searchType,
					searchValue
			);
			String subscriberUniqueId = null;
			switch (searchType) {
				case 1:
					subscriberUniqueId = subscriberRepository.findByidDocTypeAndidDocNumber("1", searchValue);
					RAServiceAsserts.notNullorEmpty(subscriberUniqueId, ErrorCodes.E_NIN_NOT_FOUND);
					break;
				case 2:
					subscriberUniqueId = subscriberRepository.findByidDocTypeAndidDocNumber("3", searchValue);
					RAServiceAsserts.notNullorEmpty(subscriberUniqueId, ErrorCodes.E_PASSPORT_NOT_FOUND);
					break;
				case 3:
					subscriberUniqueId = subscriberRepository.findByemailId(searchValue);
					RAServiceAsserts.notNullorEmpty(subscriberUniqueId, ErrorCodes.E_EMAIL_NOT_FOUND);
					break;
				case 4:
					subscriberUniqueId = subscriberRepository.findBymobileNumber(searchValue);
					RAServiceAsserts.notNullorEmpty(subscriberUniqueId, ErrorCodes.E_MOBILE_NUMBER_NOT_FOUND);
					break;
				default:
					throw new RAServiceException(ErrorCodes.E_INVALID_REQUEST);
			}
			SubscriberCompleteDetails subscriberCompleteDetails = subscriberCompleteDetailsRepositoy.findBysubscriberUid(subscriberUniqueId);

			if (subscriberCompleteDetails == null ) {
				SubscriberCompleteDetailsForAssisted assisted = subscriberCompleteDetailsForAssistedRepo.findBysubscriberUid(subscriberUniqueId);
				RAServiceAsserts.notNullorEmpty(assisted,ErrorCodes.E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND);

				assisted.setPhoto(getBase64String(assisted.getPhoto()));
				assisted.setDateOfBirth(NativeUtils.getDate(assisted.getDateOfBirth()));
				String jsonToString = objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(assisted);


				SubscriberCompleteDetails subscriberCompleteDetail2 = objectMapper.readValue(jsonToString, SubscriberCompleteDetails.class);

				subscriberCompleteDetail2.setDeviceStatus("NA");
				subscriberCompleteDetail2.setDeviceRegistrationTime("NA");

				String jsonToString2 = objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(subscriberCompleteDetail2);

				logger.info(CLASS + " :: getSubscriberDetailsBySearchType() FOR NON SMART PHONE :: Success.");
				return jsonToString2;

			}else {
				if(ugPassCode) {

					subscriberCompleteDetails.setPhoto(getBase64String(subscriberCompleteDetails.getPhoto()));
				}else {

					subscriberCompleteDetails.setPhoto(subscriberCompleteDetails.getPhoto());
				}

				subscriberCompleteDetails.setDateOfBirth(NativeUtils.getDate(subscriberCompleteDetails.getDateOfBirth()));
				String jsonToString = objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(subscriberCompleteDetails);
				logger.info(CLASS + " :: getSubscriberDetailsBySearchType() FOR SMART PHONE :: Success.");
				return jsonToString;

			}



		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberDetailsBySearchType() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberDetailsBySearchType() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberDetailsBySearchType() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	public String getBase64String(String uri) throws NoSuchMessageException, Exception {
		try {
			logger.info("{} :: getBase64String() :: URI :: {}", CLASS, uri);
			HttpHeaders headersForGet = new HttpHeaders();
			HttpEntity<Object> requestEntityForGet = new HttpEntity<>(headersForGet);
			secureUrlValidator.validate(uri);
			ResponseEntity<Resource> downloadUrlResult = restTemplate.exchange(uri, HttpMethod.GET, requestEntityForGet,
					Resource.class);
			logger.info(
					"{} :: getBase64String() :: downloadUrlResult :: {}",
					CLASS,
					downloadUrlResult.getStatusCode()
			);
			byte[] buffer = restTemplate.getForObject(uri, byte[].class);

			if (buffer == null) {
				throw new IllegalStateException("Failed to download content");
			}
			return new String(Base64.getEncoder().encode(buffer));
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getBase64String() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dt.ra.service.iface.RASubscriberServiceIface#getCountOfAllSubscribers ()
	 */
	@Override
	public String getCountOfAllSubscribers() throws RAServiceException, Exception {
		try {
			logger.info(CLASS + " :: getCountOfAllSubscribers().");
			DashboardDetails dashboardDetails = new DashboardDetails();
			int totalCount = subscriberStatusRepository.getSubscriberCount();
			dashboardDetails.setSubscriberCount(totalCount);
			int activeCount = subscriberStatusRepository.getActiveSubscriberCount();
			dashboardDetails.setActiveSubscriberCount(activeCount);
			dashboardDetails.setInActiveSubscriberCount(totalCount - activeCount);
			logger.info(CLASS + " :: getCountOfAllSubscribers() :: Success.");
			return dashboardDetails.toString();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCountOfAllSubscribers() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCountOfAllSubscribers() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dt.ra.service.iface.SubscriberRAServiceIface#updateSubscriberStatus(com.
	 * dt.ra.service.requestentity.UpdateSubscriberStatus)
	 */
	@Override
	public String updateSubscriberStatus(RARequestDTO detailsRequest) throws RAServiceException, Exception {
		try {

			logger.info("{} :: updateSubscriberStatus() :: req :: detailsRequest :: {}", CLASS, detailsRequest);
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(detailsRequest.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberStatusModel subscriberStatus = subscriberStatusRepository
					.findBysubscriberUid(detailsRequest.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriberStatus, ErrorCodes.E_SUBSCRIBER_STATUS_DATA_NOT_FOUND);
			subscriberStatus.setSubscriberStatus(detailsRequest.getSubscriberStatus());
			subscriberStatus.setSubscriberStatusDescription(detailsRequest.getDescription());
			subscriberStatus.setUpdatedDate(NativeUtils.getTimeStamp());
			subscriberStatusRepository.save(subscriberStatus);
			logger.info(CLASS + " :: updateSubscriberStatus(). Success.");
			return subscriberStatus.getSubscriberStatus();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateSubscriberStatus() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateSubscriberStatus() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateSubscriberStatus() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.SubscriberRAServiceIface#
	 * updateDeviceStatusAndSubscriberStatus(java.lang.String)
	 */
	@Override
	public String updateDeviceStatusAndSubscriberStatus(String subscriberUniqueId)
			throws RAServiceException, Exception {
		try {
			logger.info(CLASS + " :: updateDeviceStatusAndSubscriberStatus().");
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberStatusModel subscriberStatus = subscriberStatusRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriberStatus, ErrorCodes.E_SUBSCRIBER_STATUS_DATA_NOT_FOUND);
			SubscriberDevice subscriberDevice = subscriberDeviceRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriberDevice, ErrorCodes.E_SUBSCRIBER_DEVICE_DATA_NOT_FOUND);
			if (subscriberStatus.getSubscriberStatus().equals(Constant.SUBSCRIBER_STATUS)
					&& (subscriberDevice.getDeviceStatus().equalsIgnoreCase(Constant.SUBSCRIBER_STATUS))) {
				RARequestDTO raRequestModel = new RARequestDTO();
				raRequestModel.setSubscriberUniqueId(subscriberUniqueId);
				raRequestModel.setReasonId("5");
				subscriberDevice.setDeviceStatus(Constant.DISABLED);
				subscriberDevice.setUpdatedDate(NativeUtils.getTimeStampString());
				subscriberDeviceRepository.save(subscriberDevice);
				logger.info(CLASS + " :: updateDeviceStatusAndSubscriberStatus() :: Success.");
				return messageSource.getMessage("api.response.subscriber.device.de.registeration.successful", null,
						Locale.ENGLISH);

			} else {

				subscriberDevice.setDeviceStatus(Constant.DISABLED);
				subscriberDevice.setUpdatedDate(NativeUtils.getTimeStampString());
				subscriberDeviceRepository.save(subscriberDevice);
				logger.info(CLASS + " :: updateDeviceStatusAndSubscriberStatus() :: Success.");
				return messageSource.getMessage("api.response.subscriber.device.de.registeration.successful", null,
						Locale.ENGLISH);
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateDeviceStatusAndSubscriberStatus() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateDeviceStatusAndSubscriberStatus() :: IN RAServiceException {}",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " updateDeviceStatusAndSubscriberStatus() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public String getSubscriberList(int type, String value) throws RAServiceException, Exception {
		try {
			logger.info(CLASS + " :: getSubscriberDetailsBySearchType().");
			List<String> subscriberList = null;
			switch (type) {
				case 1:
					subscriberList = subscriberRepository.getSubscriberListByDocTypeAndDocNumber("1", value);
					RAServiceAsserts.notNullorEmpty(subscriberList, ErrorCodes.E_NIN_NOT_FOUND);
					break;
				case 2:
					subscriberList = subscriberRepository.getSubscriberListByDocTypeAndDocNumber("3", value);
					RAServiceAsserts.notNullorEmpty(subscriberList, ErrorCodes.E_PASSPORT_NOT_FOUND);
					break;
				case 3:
					subscriberList = subscriberRepository.getSubscriberListByEmailId(value);
					RAServiceAsserts.notNullorEmpty(subscriberList, ErrorCodes.E_EMAIL_NOT_FOUND);
					break;
				case 4:
					subscriberList = subscriberRepository.getSubscriberListByMobileNo(value);
					RAServiceAsserts.notNullorEmpty(subscriberList, ErrorCodes.E_MOBILE_NUMBER_NOT_FOUND);
					break;
				default:
					throw new RAServiceException(ErrorCodes.E_INVALID_REQUEST);
			}
			String jsonToString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(subscriberList);
			return jsonToString;
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberList() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberList() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberList() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public ApiResponse_CertCount getCountOfAllCertificates() throws RAServiceException, Exception {
		try {
			int count = subscriberCertificatesRepository.getAllCertificateCount();
			return new ApiResponse_CertCount(true, "Certficate count fetched successfully", count);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCountOfAllCertificates() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCountOfAllCertificates() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public ApiResponse_CertCount getSubscriberAndCertCount() {
		try {
			Object[] result = subscriberCertificatesRepository.getSubscriberAndCertCount();

			Map<String, BigInteger> subAndCertCountDTO = buildSubscriberAndCertMap(result);

			CountResponseDTO countResponseDTO = new CountResponseDTO();

			SubscriberCountDTO subscriberCountDTO = new SubscriberCountDTO(
					subAndCertCountDTO.get("subscriber_count"),
					subAndCertCountDTO.get("active_subscriber_count"),
					subAndCertCountDTO.get("inactive_subscriber_count"),
					subAndCertCountDTO.get("disable_subscriber_count"),
					subAndCertCountDTO.get("cert_revoke_subscriber_count"),
					subAndCertCountDTO.get("cert_expired_subscriber_count"),
					subAndCertCountDTO.get("onboarded_subscriber_count")
			);

			CertificateCountDTO certificateCountDTO = new CertificateCountDTO(
					subAndCertCountDTO.get("active_cert_count"),
					subAndCertCountDTO.get("revoke_cert_count"),
					subAndCertCountDTO.get("expired_cert_count"),
					subAndCertCountDTO.get("cert_count")
			);

			countResponseDTO.setSubscriberCount(subscriberCountDTO);
			countResponseDTO.setCertificateCount(certificateCountDTO);

			return new ApiResponse_CertCount(
					true,
					messageSource.getMessage(
							"api.response.subscribers.and.certificates.count.fetched.successfully",
							null, Locale.ENGLISH
					),
					countResponseDTO
			);

		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			return new ApiResponse_CertCount(
					false,
					messageSource.getMessage(
							"api.error.subscribers.and.certificates.count.fetched.failed",
							null, Locale.ENGLISH
					),
					null
			);
		}
	}
	private Map<String, BigInteger> buildSubscriberAndCertMap(Object r) {

		Map<String, BigInteger> map = new HashMap<>();

		Object[] row = switch (r) {

			case Object[] outer when outer.length > 0 && outer[0] instanceof Object[] inner
					-> inner;

			case Object[] outer
					-> outer;

			default
					-> throw new IllegalArgumentException("Unexpected type: " + r.getClass());
		};

		map.put("subscriber_count",              toBig(row[0]));
		map.put("active_subscriber_count",       toBig(row[1]));
		map.put("inactive_subscriber_count",     toBig(row[2]));
		map.put("disable_subscriber_count",      toBig(row[3]));
		map.put("cert_revoke_subscriber_count",  toBig(row[4]));
		map.put("cert_expired_subscriber_count", toBig(row[5]));
		map.put("onboarded_subscriber_count",    toBig(row[6]));
		map.put("active_cert_count",             toBig(row[7]));
		map.put("revoke_cert_count",             toBig(row[8]));
		map.put("expired_cert_count",            toBig(row[9]));
		map.put("cert_count",                    toBig(row[10]));

		return map;
	}

	private BigInteger toBig(Object o) {
		if (o == null) return BigInteger.ZERO;

		if (o instanceof BigInteger bigInteger)
			return bigInteger;

		if (o instanceof Number number)
			return BigInteger.valueOf(number.longValue());

		throw new IllegalArgumentException("Unexpected type: " + o.getClass());
	}



	@Override
	public String getSubscriberName(String subscriberUniqueId) throws RAServiceException, Exception {
		try {
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			String jsonToString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(subscriber.getFullName());
			return jsonToString;
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberName() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberName() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getSubscriberName() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public ApiResponses compareImage(String suid, String capturedImage) throws Exception {
		SubscriberCompleteDetailsForAssisted subscriberCompleteDetails = subscriberCompleteDetailsForAssistedRepo.findBysubscriberUid(suid);
		RAServiceAsserts.notNullorEmpty(subscriberCompleteDetails, ErrorCodes.E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND);
		String originalPhoto = null;
		if(ugPassCode) {

			originalPhoto = getBase64String(subscriberCompleteDetails.getPhoto());
		}else {

			originalPhoto = subscriberCompleteDetails.getPhoto();
		}


		CompareFaceRequest compareFaceRequest = new CompareFaceRequest();
		compareFaceRequest.setStoredImage(originalPhoto);
		compareFaceRequest.setCapturedImage(capturedImage);

		LogModelDTO logModelDTO = new LogModelDTO();
		logModelDTO.setCallStack(compareFaceRequest.toStringForNative());

		PostRequest postCompareRequest = new PostRequest();
		postCompareRequest.setRequestBody(logModelDTO.toString());
		postCompareRequest.setHashdata(logModelDTO.toString().hashCode());

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPostRequest(postCompareRequest);
		requestEntity.setTransactionType(Constant.COMPARE_IMAGE);

		logger.info("{} :: compareImage() :: request :: {}", CLASS, requestEntity.getPostRequest().getRequestBody());
		ResponseEntity<String> httpResponse = restTemplate.postForEntity(PropertiesConstants.getPkiUrl(), requestEntity, String.class);

		if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody())) {
			throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
		}
		if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody())) {
			throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
		}

		ServiceResponse serviceResponse = objectMapper.readValue(
				httpResponse.getBody(),
				ServiceResponse.class
		);

		logger.info("{} :: compareImage() :: Native response :: {}", CLASS, serviceResponse.getStatus());
		logModelDTO.setCallStack(null);
		logModelDTO.setLogMessage(Constant.RESPONSE);
		logModelDTO.setEndTime(NativeUtils.getTimeStampString());
		if (serviceResponse.getStatus().equals(Constant.FAIL)) {
			ErrorCodes.setResponse(serviceResponse);
			logger.info("{} :: compareImage :: error :: {}", CLASS, serviceResponse.getError_message());
			logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
			LogModel logModel = NativeUtils.getLogModel(logModelDTO);
			throw new RAServiceException(serviceResponse.getError_message());
		}

		return AppUtil.createApiResponse(true,"Status",serviceResponse);
	}

	@Override
	public ApiResponses getFeatures(String suid, String capturedImage) throws Exception {

		try {
			CompareFaceRequest compareFaceRequest = new CompareFaceRequest();
			compareFaceRequest.setCapturedImage(capturedImage);

			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setCallStack(compareFaceRequest.toStringForNative());

			PostRequest postCompareRequest = new PostRequest();
			postCompareRequest.setRequestBody(logModelDTO.toString());
			postCompareRequest.setHashdata(logModelDTO.toString().hashCode());

			RequestEntity requestEntity = new RequestEntity();
			requestEntity.setPostRequest(postCompareRequest);
			requestEntity.setTransactionType(Constant.GET_FEATURES);

			logger.info("{} :: getFeatures() :: request :: {}", CLASS, requestEntity.getPostRequest().getRequestBody());
			ResponseEntity<String> httpResponse = restTemplate.postForEntity(PropertiesConstants.getPkiUrl(), requestEntity, String.class);

			if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody()))
				throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);

			if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody()))
				throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
			ServiceResponse serviceResponse = objectMapper.readValue(httpResponse.getBody(), ServiceResponse.class);
			logger.error("{} :: getFeatures() :: native response body :: {}", CLASS, httpResponse.getBody());
			logger.info("{} :: getFeatures() :: Native response :: {}", CLASS, serviceResponse.getStatus());
			logModelDTO.setCallStack(null);
			logModelDTO.setLogMessage(Constant.RESPONSE);
			logModelDTO.setEndTime(NativeUtils.getTimeStampString());
			if (serviceResponse.getStatus().equals(Constant.FAIL)) {
				ErrorCodes.setResponse(serviceResponse);

				logger.info("{} :: getFeatures :: error :: {}", CLASS, serviceResponse.getError_message());
				logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
				LogModel logModel = NativeUtils.getLogModel(logModelDTO);
				throw new RAServiceException(serviceResponse.getError_message());
			}

			return AppUtil.createApiResponse(true,"Status",serviceResponse);
		} catch (Exception e) {
			logger.error("Unexpected exception");
			logger.error("{} :: getFeatures() :: Exception", CLASS, e);
			return AppUtil.createApiResponse(false,"Status",null);
		}

	}


	@Override
	public Response getSignForQr(QrData qrData) throws Exception {
		QrData qrResponse = new QrData();
		GenerateSignature generateSignature = new GenerateSignature();
		generateSignature.setSubscriberUniqueId(qrData.getOrgId());
		generateSignature.setCertType(0);
		generateSignature.setHashData(true);
		generateSignature.setHash(qrData.getDataHash());
		String response = rapkiServiceIfaceImpl.generateSignatureOrganization(generateSignature);
		qrResponse.setSignedDataHash(response);

		if (qrData.getKeyHash() != null && !qrData.getKeyHash().isEmpty()) {
			GenerateSignature generateSignature2 = new GenerateSignature();
			generateSignature2.setSubscriberUniqueId(qrData.getOrgId());
			generateSignature2.setCertType(0);
			generateSignature2.setHashData(true);
			generateSignature2.setHash(qrData.getKeyHash());
			String response2 = rapkiServiceIfaceImpl.generateSignatureOrganization(generateSignature2);
			qrResponse.setSignedKeyHash(response2);
		}

		return new Response(true, "", qrResponse);

	}

	@Override
	public ApiResponses getSubscriberPhoto(String suid) throws Exception {

		SubscriberCompleteDetails assisted = subscriberCompleteDetailsRepositoy.findBysubscriberUid(suid);
		RAServiceAsserts.notNullorEmpty(assisted, ErrorCodes.E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND);
		String photo =null;
		if(ugPassCode) {

			photo = getBase64String(assisted.getPhoto());
		}else {

			photo = assisted.getPhoto();
		}
		return AppUtil.createApiResponse(true, "Photo", photo);
	}
}