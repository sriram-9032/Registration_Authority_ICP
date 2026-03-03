/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.service.iface.implementation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import jakarta.persistence.PersistenceException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.DAESService;
import ug.daes.Result;
import ug.daes.ra.asserts.RAServiceAsserts;
import ug.daes.ra.config.SecureUrlValidator;
import ug.daes.ra.config.SentryClientExceptions;
import ug.daes.ra.dto.*;
import ug.daes.ra.dto.EmailReqDto;
import ug.daes.ra.enums.CertificateStatus;
import ug.daes.ra.enums.CertificateType;
import ug.daes.ra.enums.LogMessageType;
import ug.daes.ra.enums.RevokeReason;
import ug.daes.ra.enums.ServiceName;
import ug.daes.ra.enums.TransactionType;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.model.*;
import ug.daes.ra.repository.iface.*;
import ug.daes.ra.request.entity.*;
import ug.daes.ra.request.entity.RequestEntity;
import ug.daes.ra.response.entity.APIResponse;
import ug.daes.ra.response.entity.CertificateData;
import ug.daes.ra.response.entity.ServiceResponse;
import ug.daes.ra.service.iface.RAServiceIface;
import ug.daes.ra.utils.AppUtil;
import ug.daes.ra.utils.Constant;
import ug.daes.ra.utils.NativeUtils;
import ug.daes.ra.utils.PropertiesConstants;
import ug.daes.ra.utils.KafkaSender;

/**
 * The Class RAServiceIfaceImpl.
 */
@Service
@EnableScheduling
public class RAServiceIfaceImpl implements RAServiceIface {

	private static final String CLASS = "RAServiceIfaceImpl";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(RAServiceIfaceImpl.class);

	boolean notificationSent = false;

	int emailSentCount = 0;
	private static SecureUrlValidator secureUrlValidator;
	private String emailBaseUrl;

	private final RestTemplate restTemplate;
	private final KafkaSender rabbitMQSender;
	private final SubscriberCertificatesRepository subscriberCertificatesRepository;
	private final OrganizationCertificatesRepository organizationCertificatesRepository;
	private final OrganizationDetailsRepository organizationDetailsRepository;
	private final SubscriberCertificatePinHistoryRepository subscriberCertificatePinHistoryRepository;
	private final SubscriberRADataRepository subscriberRADataRepository;
	private final SubscriberCertificateLifeCycleRepository subscriberCertificateLifeCycleRepository;
	private final SubscriberRepository subscriberRepository;
	private final SubscriberStatusRepository subscriberStatusRepository;
	private final SubscriberFcmTokenRepository subscriberFcmTokenRepository;
	private final SubscriberWrappedKeyRepository subscriberWrappedKeyRepository;
	private final MessageSource messageSource;
	private final SentryClientExceptions sentryClientExceptions;

	@Autowired
	public RAServiceIfaceImpl(RestTemplate restTemplate,
							  KafkaSender rabbitMQSender,
							  SubscriberCertificatesRepository subscriberCertificatesRepository,
							  OrganizationCertificatesRepository organizationCertificatesRepository,
							  OrganizationDetailsRepository organizationDetailsRepository,
							  SubscriberCertificatePinHistoryRepository subscriberCertificatePinHistoryRepository,
							  SubscriberRADataRepository subscriberRADataRepository,
							  SubscriberCertificateLifeCycleRepository subscriberCertificateLifeCycleRepository,
							  SubscriberRepository subscriberRepository,
							  SubscriberStatusRepository subscriberStatusRepository,
							  SubscriberFcmTokenRepository subscriberFcmTokenRepository,
							  SubscriberWrappedKeyRepository subscriberWrappedKeyRepository,
							  MessageSource messageSource,
							  SentryClientExceptions sentryClientExceptions) {
		this.restTemplate = restTemplate;
		this.rabbitMQSender = rabbitMQSender;
		this.subscriberCertificatesRepository = subscriberCertificatesRepository;
		this.organizationCertificatesRepository = organizationCertificatesRepository;
		this.organizationDetailsRepository = organizationDetailsRepository;
		this.subscriberCertificatePinHistoryRepository = subscriberCertificatePinHistoryRepository;
		this.subscriberRADataRepository = subscriberRADataRepository;
		this.subscriberCertificateLifeCycleRepository = subscriberCertificateLifeCycleRepository;
		this.subscriberRepository = subscriberRepository;
		this.subscriberStatusRepository = subscriberStatusRepository;
		this.subscriberFcmTokenRepository = subscriberFcmTokenRepository;
		this.subscriberWrappedKeyRepository = subscriberWrappedKeyRepository;
		this.messageSource = messageSource;
		this.sentryClientExceptions = sentryClientExceptions;
	}

	/**
	 * Issue certificate.
	 *
	 * @param raRequestModel the ra request model
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception          the exception
	 * @return the string
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dt.ra.service.iface.RAServiceIface#issueCertificate(com.dt.ra.service
	 * .requestentity.IssueCertificateRequest)
	 */
	@Override
	public String issueCertificate(RARequestDTO raRequestModel) throws RAServiceException, Exception {
		try {
			sentryClientExceptions.captureTags(raRequestModel.getSubscriberUniqueId(),null,"issueCertificate","RAServiceIfaceImpl");
			logger.info("{} :: issueCertificate() :: request :: raRequestModel :: {}", CLASS, raRequestModel);
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(raRequestModel.getSubscriberUniqueId());
			logger.info("{} :: issueCertificate() :: request :: subscriberData :: uid :: {} :: data :: {}",
					CLASS, subscriber.getSubscriberUid(), subscriber);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setEndTime(null);
			logModelDTO.setIdentifier(subscriber.getSubscriberUid());
			logModelDTO.setServiceName(ServiceName.CERTIFICATE_GENERATED.toString());
			logModelDTO.setLogMessage(Constant.REQUEST);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(NativeUtils.getUUId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(raRequestModel.getGeoLocation());
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(null);
			logModelDTO.seteSealUsed(false);


			SubscriberStatusModel subscriberStatus = subscriberStatusRepository
					.findBysubscriberUid(raRequestModel.getSubscriberUniqueId());
			logger.info("{} :: issueCertificate() :: request :: statusdata :: uid :: {} :: data :: {}",
					CLASS, subscriber.getSubscriberUid(), subscriberStatus.getSubscriberStatus());
			RAServiceAsserts.notNullorEmpty(subscriberStatus, ErrorCodes.E_SUBSCRIBER_STATUS_DATA_NOT_FOUND);
			if (!subscriberStatus.getSubscriberStatus().equals(Constant.CERT_GENERATING))
				throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_NOT_ONBOARDED);
			SubscriberRaData subscriberRaData = subscriberRADataRepository
					.findBysubscriberUniqueId(raRequestModel.getSubscriberUniqueId());
			logger.info("{} :: issueCertificate() :: request :: raRequestData :: uid :: {} :: data :: {}",
					CLASS, subscriber.getSubscriberUid(), subscriberRaData);
			RAServiceAsserts.notNullorEmpty(subscriberRaData, ErrorCodes.E_SUBSCRIBER_RA_DATA_NOT_FOUND);
			List<SubscriberCertificates> subscriberCertificates = subscriberCertificatesRepository
					.findByCertificateStatusAndsubscriberUniqueId(CertificateStatus.ACTIVE.toString(),
							raRequestModel.getSubscriberUniqueId());
			if (subscriberCertificates.size() == 2)
				throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_CERTIFICATES_ARE_ACTIVE);
			if (subscriberCertificates.isEmpty()) {
				String keyId = NativeUtils.generatePKIKeyId();
				IssueCertificateRequest issueCertificateRequest = new IssueCertificateRequest();
				issueCertificateRequest.setSubscriberUniqueId(raRequestModel.getSubscriberUniqueId());
				issueCertificateRequest.setKeyID(keyId);


				issueCertificateRequest.setCommonName(subscriberRaData.getCommonName());
				issueCertificateRequest.setCountryName(subscriberRaData.getCountryName());

				PostRequest issueCertificatePostRequest = new PostRequest();
				issueCertificatePostRequest.setRequestBody(issueCertificateRequest.toString());
				issueCertificatePostRequest.setHashdata(issueCertificateRequest.toString().hashCode());
				issueCertificatePostRequest.setPkiKeyID(keyId);
				issueCertificatePostRequest.setCertificateType(CertificateType.SIGN.toString());
				issueCertificatePostRequest.setCallbackURI(PropertiesConstants.getIssueCertificateCallbackUrl());
				String response = processRequest(PropertiesConstants.getPkiUrl(), issueCertificatePostRequest, subscriber,
						logModelDTO);
				if (response.equals(Constant.SUCCESS)) {
					logger.info(CLASS + " :: issueCertificate :: Request for issuing Sign process Successfully");
					String keyId2 = NativeUtils.generatePKIKeyId();
					issueCertificateRequest.setKeyID(keyId2);
					issueCertificatePostRequest.setRequestBody(issueCertificateRequest.toString());
					issueCertificatePostRequest.setPkiKeyID(keyId2);
					issueCertificatePostRequest.setHashdata(issueCertificateRequest.toString().hashCode());
					issueCertificatePostRequest.setCertificateType(CertificateType.AUTH.toString());
					String response2 = processRequest(PropertiesConstants.getPkiUrl(), issueCertificatePostRequest,
							subscriber, logModelDTO);
					if (response2.equals(Constant.SUCCESS)) {
						logger.info(CLASS + " :: issueCertificate() :: Request for issuing Auth process Successfully.");
						return Constant.REQUEST_FOR_ISSUING_SIGN_AND_AUTH_PROCESS_SUCCESSFULLY;
					} else
						throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_ISSUE_AUTHENTICATION_CERTIFICATE_FAILED);
				} else
					throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_ISSUE_SIGNING_CERTIFICATE_FAILED);
			} else if (subscriberCertificates.size() == 1) {
				for (SubscriberCertificates certificates : subscriberCertificates) {
					String keyId = NativeUtils.generatePKIKeyId();
					IssueCertificateRequest issueCertificateRequest = new IssueCertificateRequest();
					issueCertificateRequest.setSubscriberUniqueId(raRequestModel.getSubscriberUniqueId());
					issueCertificateRequest.setKeyID(keyId);


					issueCertificateRequest.setCommonName(subscriberRaData.getCommonName());
					issueCertificateRequest.setCountryName(subscriberRaData.getCountryName());
					PostRequest issueCertificatePostRequest = new PostRequest();
					issueCertificatePostRequest.setRequestBody(issueCertificateRequest.toString());
					issueCertificatePostRequest.setHashdata(issueCertificateRequest.toString().hashCode());
					issueCertificatePostRequest.setPkiKeyID(keyId);
					if (certificates.getCertificateType().equals(CertificateType.AUTH.toString()))
						issueCertificatePostRequest.setCertificateType(CertificateType.SIGN.toString());
					else
						issueCertificatePostRequest.setCertificateType(CertificateType.AUTH.toString());
					issueCertificatePostRequest.setCallbackURI(PropertiesConstants.getIssueCertificateCallbackUrl());
					String response = processRequest(PropertiesConstants.getPkiUrl(), issueCertificatePostRequest,
							subscriber, logModelDTO);
					if (response.equals(Constant.SUCCESS)) {
						logger.info("{} :: issueCertificate() :: Request for issuing {} process successfully",
								CLASS,
								issueCertificatePostRequest.getCertificateType());
						return Constant.REQUEST_FOR_ISSUING_SIGN_AND_AUTH_PROCESS_SUCCESSFULLY;
					} else {
						if (certificates.getCertificateType().equals(CertificateType.AUTH.toString()))
							throw new RAServiceException(
									ErrorCodes.E_SUBSCRIBER_ISSUE_AUTHENTICATION_CERTIFICATE_FAILED);
						else
							throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_ISSUE_SIGNING_CERTIFICATE_FAILED);
					}
				}
			}
			throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_CERTIFICATES_ARE_ACTIVE);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			sentryClientExceptions.captureExceptions(e);
			logger.error(CLASS + " issueCertificate() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			sentryClientExceptions.captureExceptions(e);
			logger.error(CLASS + " issueCertificate() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			sentryClientExceptions.captureExceptions(e);
			logger.error(CLASS + " issueCertificate() ::  EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/**
	 * Process request.
	 *
	 * @param baseUrl                     the base url
	 * @param issueCertificatePostRequest the issue certificate post request
	 * @param subscriber                  the subscriber
	 * @param logModelDTO                 the log model DTO
	 * @return the string
	 * @throws Exception the exception
	 */
	private String processRequest(String baseUrl, PostRequest issueCertificatePostRequest, Subscriber subscriber,
								  LogModelDTO logModelDTO) throws Exception {
		try {
			logger.info(CLASS + " :: issuecertificate() :: processRequest().");
			logModelDTO.setTimestamp(null);
			logModelDTO.setCallStack(issueCertificatePostRequest.getRequestBody());
			logModelDTO.setChecksum(null);
			issueCertificatePostRequest.setRequestBody(logModelDTO.toString());
			issueCertificatePostRequest.setHashdata(logModelDTO.toString().hashCode());
			RequestEntity requestEntity = new RequestEntity();
			requestEntity.setPostRequest(issueCertificatePostRequest);
			requestEntity.setTransactionType(Constant.ISSUE_CERTIFICATE);
			logger.info("{} issueCertificate() :: processRequest() :: requestBody :: {}",
					CLASS,
					requestEntity.getPostRequest().getRequestBody());

			secureUrlValidator.validate(baseUrl);
			ResponseEntity<String> httpResponse = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
			logger.info("{} issueCertificate() :: processRequest() :: native response :: {}",
					CLASS,
					httpResponse.getBody());
			RAServiceAsserts.notNullorEmpty(httpResponse, ErrorCodes.E_RA_POST_REQUEST_FAILED);
			if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody())) {
				throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
			}
			if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody())) {
				throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
			}
			String totalTime = NativeUtils.getTotalTime(logModelDTO.getStartTime(), NativeUtils.getTimeStampString());
			logModelDTO.setLogMessage("Total Time Taken " + totalTime + " seconds");
			logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
			logModelDTO.setEndTime(NativeUtils.getTimeStampString());
			logModelDTO.setCallStack(null);
			LogModel logModel = NativeUtils.getLogModel(logModelDTO);
			rabbitMQSender.send(logModel);
			return Constant.SUCCESS;
		} catch (RAServiceException e) {
			logger.error("{} :: processRequest :: RAServiceException", CLASS, e);
			logger.error("Unexpected exception", e);
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Check certificate status.
	 *
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception          the exception
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.RAServiceIface#checkCertificateStatus(java.lang.
	 * String)
	 */
	@Override
	@Scheduled(cron = "0 0 0 * *  ?")
	public String checkCertificateStatus() throws RAServiceException, Exception {
		try {
			logger.info(CLASS + " :: checkCertificateStatus() :: request :: ");
			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			List<SubscriberCertificates> subscriberCertificates = subscriberCertificatesRepository
					.findByCertificateStatusExpired();

			logModelDTO.setServiceName(null);
			logModelDTO.setLogMessage(Constant.REQUEST);
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(NativeUtils.getUUId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(null);
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(null);
			logModelDTO.seteSealUsed(false);
			CheckCertificateStatus checkCertificateStatus = new CheckCertificateStatus();
			for (SubscriberCertificates subscriberCertificate : subscriberCertificates) {
				logModelDTO.setIdentifier(subscriberCertificate.getSubscriberUniqueId());
				checkCertificateStatus.setSerialNumber(subscriberCertificate.getCertificateSerialNumber());
				checkCertificateStatus.setCertificate(subscriberCertificate.getCertificateData());
				String requestbody = checkCertificateStatus.toString();
				logModelDTO.setCallStack(requestbody);
				logModelDTO.setCallStack(null);
				logModelDTO.setLogMessage(Constant.RESPONSE);
				subscriberCertificate.setCertificateStatus(CertificateStatus.EXPIRED.toString());
				subscriberCertificate.setUpdatedDate(NativeUtils.getTimeStamp());
				subscriberCertificatesRepository.save(subscriberCertificate);
				SubscriberCertificateLifeCycle subscriberCertificateLifeCycle = new SubscriberCertificateLifeCycle();
				subscriberCertificateLifeCycle
						.setCertificateSerialNumber(subscriberCertificate.getCertificateSerialNumber());
				subscriberCertificateLifeCycle.setCertificateStatus(CertificateStatus.EXPIRED.toString());
				subscriberCertificateLifeCycle.setSubscriberUniqueId(subscriberCertificate.getSubscriberUniqueId());
				subscriberCertificateLifeCycle.setCertificateType(subscriberCertificate.getCertificateType());
				subscriberCertificateLifeCycle.setCreationDate(NativeUtils.getTimeStamp());
				subscriberCertificateLifeCycleRepository.save(subscriberCertificateLifeCycle);
				SubscriberStatusModel subscriberStatus = subscriberStatusRepository
						.findBysubscriberUid(subscriberCertificate.getSubscriberUniqueId());
				subscriberStatus.setSubscriberStatus(Constant.CERT_EXPIRED);
				subscriberStatus.setUpdatedDate(NativeUtils.getTimeStamp());
				subscriberStatus.setSubscriberStatusDescription(Constant.CERTIFICATES_ARE_EXPIRED);
				subscriberStatusRepository.save(subscriberStatus);

				Subscriber subscriber = subscriberRepository
						.findBysubscriberUid(subscriberCertificate.getSubscriberUniqueId());
				String subscriberFcmToken = subscriberFcmTokenRepository
						.findBysubscriberUid(subscriberCertificate.getSubscriberUniqueId());

				NotificationContextDTO notificationContextDTO = new NotificationContextDTO();
				notificationContextDTO.setpREF_CERTIFICATE_STATUS(CertificateStatus.EXPIRED);

				NotificationDataDTO notificationDataDTO = new NotificationDataDTO();
				notificationDataDTO.setTitle(Constant.HI + subscriber.getFullName());
				notificationDataDTO.setBody(
						Constant.YOUR + subscriberCertificate.getCertificateType() + Constant.CERTIFIACTE_IS_EXPIRED);
				notificationDataDTO.setNotificationContext(notificationContextDTO);

				PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
				pushNotificationRequest.setTo(subscriberFcmToken);
				pushNotificationRequest.setPriority(Constant.HIGH);
				pushNotificationRequest.setData(notificationDataDTO.getExpiredCertNotificationData());



				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<>(pushNotificationRequest.getNotificationRquest(), headers);

				if (!notificationSent) {
					try {
						ResponseEntity<String> httpResponse1 = restTemplate
								.postForEntity(PropertiesConstants.getNotification(), entity, String.class);
						notificationSent = true;
					} catch (Exception e) {
						logger.error("{} :: FCM Notification failed", CLASS, e);
					}
				}




				logModelDTO.setLogMessage(Constant.RESPONSE);
				logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
				logModelDTO.setEndTime(NativeUtils.getTimeStampString());
				LogModel logModel = NativeUtils.getLogModel(logModelDTO);
				try {
					rabbitMQSender.send(logModel);
				} catch (Exception e) {
					logger.error("{} :: Service log failed", CLASS, e);
				}
			}
			logger.info(CLASS + " :: checkCertificateStatus() :: status :: completed.");

			return Constant.COMPLETED;
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " checkCertificateStatus() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " checkCertificateStatus() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " checkCertificateStatus() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}


//	public APIResponse sendEmail(String email) {
//		try {
//			EmailReqDto emailReqDto = new EmailReqDto();
//			emailReqDto.setUserSubscription(true);
//			emailReqDto.setEmailId(email);
//			String url = emailBaseUrl;
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			HttpEntity<Object> requestEntity = new HttpEntity<>(emailReqDto, headers);
//
//			logger.info("{} sendEmailToSpoc() requestEntity >> {}", CLASS, requestEntity);
//			ResponseEntity<ApiResponses> res = restTemplate.exchange(
//					url, HttpMethod.POST, requestEntity, ApiResponses.class);
//
//			logger.info("{} sendEmailToSpoc() res >> {}", CLASS, res);
//
//			HttpStatusCode statusCode = res.getStatusCode();
//			int statusValue = statusCode.value();
//
//			if (statusValue == HttpStatus.OK.value()) {
//				return new APIResponse(true, "Email sent successfully", res.getBody());
//			} else if (statusValue == HttpStatus.BAD_REQUEST.value()) {
//				return new APIResponse(false, "Bad Request", null);
//			} else if (statusValue == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//				return new APIResponse(false, "Internal server error", null);
//			} else {
//				return new APIResponse(false,
//						messageSource.getMessage(
//								"api.error.something.went.wrong.please.try.after.sometime",
//								null, Locale.ENGLISH),
//						null);
//			}
//		} catch (Exception e) {
//			logger.error("Unexpected exception", e);
//			return new APIResponse(false,
//					messageSource.getMessage(
//							"api.error.something.went.wrong.please.try.after.sometime",
//							null, Locale.ENGLISH),
//					null);
//		}
//	}
public APIResponse sendEmail(String email) {
	try {
		EmailReqDto emailReqDto = new EmailReqDto();
		emailReqDto.setUserSubscription(true);
		emailReqDto.setEmailId(email);
		String url = emailBaseUrl;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(emailReqDto, headers);

		logger.info("{} sendEmailToSpoc() requestEntity >> {}", CLASS, requestEntity);
		ResponseEntity<ApiResponses> res = restTemplate.exchange(
				url, HttpMethod.POST, requestEntity, ApiResponses.class);

		logger.info("{} sendEmailToSpoc() res >> {}", CLASS, res);

		HttpStatusCode statusCode = res.getStatusCode();
		int statusValue = statusCode.value();

		String responseBody = res.getBody() != null ? new ObjectMapper().writeValueAsString(res.getBody()) : null;

		if (statusValue == HttpStatus.OK.value()) {
			return new APIResponse(true, "Email sent successfully", responseBody);
		} else if (statusValue == HttpStatus.BAD_REQUEST.value()) {
			return new APIResponse(false, "Bad Request", null);
		} else if (statusValue == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return new APIResponse(false, "Internal server error", null);
		} else {
			return new APIResponse(false,
					messageSource.getMessage(
							"api.error.something.went.wrong.please.try.after.sometime",
							null, Locale.ENGLISH),
					null);
		}
	} catch (Exception e) {
		logger.error("Unexpected exception", e);
		return new APIResponse(false,
				messageSource.getMessage(
						"api.error.something.went.wrong.please.try.after.sometime",
						null, Locale.ENGLISH),
				null);
	}
}

	/**
	 * Revoke certificate.
	 *
	 * @param requestBody the request body
	 * @return the string
	 * @throws Exception the exception
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.RAServiceIface#revokeCertificate(com.dt.ra.
	 * service.requestentity.RevokeCertificateRequest)
	 */
	@Override
	public String revokeCertificate(RARequestDTO requestBody) throws Exception {
		try {

			logger.info("{} :: revokeCertificate() :: request :: {}", CLASS, requestBody);
			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setIdentifier(requestBody.getSubscriberUniqueId());
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(requestBody.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberStatusModel subscriberStatus = subscriberStatusRepository
					.findBysubscriberUid(requestBody.getSubscriberUniqueId());
			RAServiceAsserts.notNullorEmpty(subscriberStatus, ErrorCodes.E_SUBSCRIBER_STATUS_DATA_NOT_FOUND);
			List<SubscriberCertificates> subscriberCertificates = subscriberCertificatesRepository
					.findByCertificateStatusAndsubscriberUniqueId(CertificateStatus.ACTIVE.toString(),
							requestBody.getSubscriberUniqueId());
			if (subscriberCertificates.isEmpty()) {
				throw new RAServiceException(ErrorCodes.E_SUBSCRIBER_CERTIFICATES_ARE_REVOKED);
			}else {
				boolean result = false;
				String revokeRequest = null;
				for (SubscriberCertificates subscriberCertificate : subscriberCertificates) {
					switch (requestBody.getReasonId()) {
						case "1":
							subscriberCertificate.setRevocationReason(RevokeReason.KEY_COMPROMISED.toString());
							break;
						case "-2":
							subscriberCertificate.setRevocationReason(RevokeReason.NO_REASON.toString());
							break;
						case "3":
							subscriberCertificate.setRevocationReason(RevokeReason.AFFILIATION_CHANGED.toString());
							break;
						case "4":
							subscriberCertificate.setRevocationReason(RevokeReason.SUPERSEDED.toString());
							break;
						case "5":
							subscriberCertificate.setRevocationReason(RevokeReason.CESSATION_OF_OPERATION.toString());
							break;
						case "6":
							subscriberCertificate.setRevocationReason(RevokeReason.CERTIFICATE_HOLD.toString());
							break;
						case "9":
							subscriberCertificate.setRevocationReason(RevokeReason.PRIVILEGE_WITHDRAWN.toString());
							break;
						default:
							throw new RAServiceException(ErrorCodes.E_REVOKE_REASON_NOT_FOUND);
					}

					logModelDTO.setServiceName(ServiceName.CERTIFICATE_REVOKED.toString());
					logModelDTO.setLogMessage(Constant.REQUEST);
					logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
					logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
					logModelDTO.setTransactionSubType(null);
					logModelDTO.setCorrelationID(NativeUtils.getUUId());
					logModelDTO.setTransactionID(NativeUtils.getUUId());
					logModelDTO.setSubTransactionID(null);
					logModelDTO.setGeoLocation(requestBody.getGeoLocation());
					logModelDTO.setServiceProviderName(null);
					logModelDTO.setServiceProviderAppName(null);
					logModelDTO.setSignatureType(null);
					logModelDTO.seteSealUsed(false);
					logModelDTO.setEndTime(null);
					logModelDTO.setTimestamp(null);

					RevokeCertificateRequest revokeCertificateRequest = new RevokeCertificateRequest();
					revokeCertificateRequest.setReasonId(requestBody.getReasonId());
					revokeCertificateRequest.setSerialNumber(subscriberCertificate.getCertificateSerialNumber());
					revokeRequest = revokeCertificateRequest.toString();
					logModelDTO.setCallStack(revokeRequest);
					logger.info("{} :: revokeCertificate() :: native request :: {}", CLASS, revokeRequest);
					String baseUrl = PropertiesConstants.getPkiUrl();
					PostRequest request = new PostRequest();
					request.setRequestBody(logModelDTO.toString());
					request.setHashdata(logModelDTO.toString().hashCode());
					RequestEntity requestEntity = new RequestEntity();
					requestEntity.setPostRequest(request);
					requestEntity.setTransactionType(Constant.REVOKE_CERTIFICATE);
					ResponseEntity<String> httpResponse = restTemplate.postForEntity(baseUrl, requestEntity,
							String.class);
					if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody())) {
						throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
					}
					if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody())) {
						throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
					}
					logger.info("{} :: revokeCertificate() :: native response :: {}", CLASS, httpResponse.getBody());
					ServiceResponse serviceResponse = objectMapper.readValue(httpResponse.getBody(),
							ServiceResponse.class);
					logModelDTO.setCallStack(null);
					logModelDTO.setLogMessage(Constant.RESPONSE);
					if (serviceResponse.getStatus().equals(Constant.FAIL)) {
						logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
						logModelDTO.setEndTime(NativeUtils.getTimeStampString());
						LogModel logModel = NativeUtils.getLogModel(logModelDTO);
						rabbitMQSender.send(logModel);
						ErrorCodes.setResponse(serviceResponse);
						throw new RAServiceException(serviceResponse.getError_message());
					} else {
						subscriberCertificate.setCertificateStatus(CertificateStatus.REVOKED.toString());
						subscriberCertificate.setUpdatedDate(NativeUtils.getTimeStamp());
						subscriberCertificatesRepository.save(subscriberCertificate);

						SubscriberCertificateLifeCycle subscriberCertificateLifeCycle= new SubscriberCertificateLifeCycle();

						subscriberCertificateLifeCycle
								.setCertificateSerialNumber(subscriberCertificate.getCertificateSerialNumber());
						subscriberCertificateLifeCycle.setCertificateStatus(CertificateStatus.REVOKED.toString());
						subscriberCertificateLifeCycle.setRevokedReason(subscriberCertificate.getRevocationReason());
						subscriberCertificateLifeCycle
								.setSubscriberUniqueId(subscriberCertificate.getSubscriberUniqueId());
						subscriberCertificateLifeCycle.setCertificateType(subscriberCertificate.getCertificateType());
						subscriberCertificateLifeCycle.setCreationDate(NativeUtils.getTimeStamp());
						subscriberCertificateLifeCycleRepository.save(subscriberCertificateLifeCycle);
						result = true;
					}
				}
				if (result) {
					subscriberStatus.setSubscriberStatus(Constant.CERT_REVOKED);
					subscriberStatus.setUpdatedDate(NativeUtils.getTimeStamp());
					subscriberStatus.setSubscriberStatusDescription(Constant.CERTIFICATES_ARE_REVOKED_SUCCESSFULLY);
					subscriberStatusRepository.save(subscriberStatus);

					String subscriberFcmToken = subscriberFcmTokenRepository
							.findBysubscriberUid(requestBody.getSubscriberUniqueId());
					logger.info("{} :: revokeCertificate() :: fcmToken :: {}", CLASS, subscriberFcmToken);
					NotificationContextDTO notificationContextDTO = new NotificationContextDTO();
					notificationContextDTO.setpREF_CERTIFICATE_STATUS(CertificateStatus.REVOKED);

					NotificationDataDTO notificationDataDTO = new NotificationDataDTO();
					notificationDataDTO.setTitle(Constant.HI + subscriber.getFullName());
					notificationDataDTO.setBody(Constant.YOUR_CERTIFICATES_ARE_REVOKED_SUCCESSFULLY);
					notificationDataDTO.setNotificationContext(notificationContextDTO);

					PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
					pushNotificationRequest.setTo(subscriberFcmToken);
					pushNotificationRequest.setPriority(Constant.HIGH);
					pushNotificationRequest.setData(notificationDataDTO.getRevokeCertNotificationData());


					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<String> entity = new HttpEntity<>(pushNotificationRequest.getNotificationRquest() , headers);
					ResponseEntity<String> httpResponse = restTemplate.postForEntity(PropertiesConstants.getNotification(),
							entity, String.class);
					logger.info("{} :: revokeCertificate() :: notification response :: {}", CLASS, httpResponse.getBody());
					logModelDTO.setLogMessage(Constant.RESPONSE);
					logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
					logModelDTO.setEndTime(NativeUtils.getTimeStampString());
					logModelDTO.setTimestamp(null);
					logModelDTO.setCallStack(null);
					LogModel logModel = NativeUtils.getLogModel(logModelDTO);
					rabbitMQSender.send(logModel);
					return Constant.SUCCESS;
				} else
					throw new RAServiceException(ErrorCodes.E_CERTIFICATE_REVOCATION_FAILED);
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " revokeCertificate() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " revokeCertificate() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " revokeCertificate() ::  EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}


	@Override
	public String issueCertificateCallBack(Map<String, String> response) throws Exception {
		try {
			logger.info("{} :: issueCertificateCallBack() :: response :: {}", CLASS, response);

			ServiceResponse serviceResponse = objectMapper.readValue(
					response.get(Constant.CALLBACK_RESPONSE), ServiceResponse.class);
			String status = serviceResponse.getStatus();
			String suid = response.get(Constant.CALLBACK_SUID);

			if (Constant.CALLBACK_SUCCESS.equals(status)) {
				processSuccessCallback(response, serviceResponse, suid);
			} else {
				processFailureCallback(response, serviceResponse, suid);
			}

			return status;
		} catch (PersistenceException e) {
			logger.error("{} issueCertificateCallback() :: DATABASE EXCEPTION {}", CLASS, e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("{} issueCertificateCallback() :: RAServiceException {}", CLASS, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("{} issueCertificateCallback() :: EXCEPTION {}", CLASS, e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime", null, Locale.ENGLISH));
		}
	}

	private void processSuccessCallback(Map<String, String> response, ServiceResponse serviceResponse, String suid) throws Exception {
		saveCertificateAndMetadata(response, serviceResponse, suid);

		List<SubscriberCertificates> activeCerts = subscriberCertificatesRepository
				.findByCertificateStatusAndsubscriberUniqueId(CertificateStatus.ACTIVE.toString(), suid);

		if (activeCerts.size() == 2) {
			updateSubscriberStatus(suid, Constant.PIN_SET_REQUIRED, Constant.CERTIFICATES_ISSUED_SUCCESSFULLY);
			sendCertificateNotification(suid, CertificateStatus.ACTIVE);
			auditTransaction(suid, ServiceName.CERTIFICATE_PAIR_ISSUED.toString(), LogMessageType.SUCCESS);
		}
	}

	private void processFailureCallback(Map<String, String> response, ServiceResponse serviceResponse, String suid) throws Exception {
		saveLifecycleStatus(response, serviceResponse.getStatus(), suid);
		auditTransaction(suid, ServiceName.CERTIFICATE_PAIR_ISSUED.toString(), LogMessageType.ERROR);

		List<SubscriberCertificateLifeCycle> failures = subscriberCertificateLifeCycleRepository
				.findBySubscriberUniqueIdAndCertificateStatus(suid, serviceResponse.getStatus());

		if (!failures.isEmpty() && failures.size() % 2 == 0) {
			sendCertificateNotification(suid, CertificateStatus.FAILED);
		}
	}

	private void sendCertificateNotification(String suid, CertificateStatus status) {
		try {
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(suid);
			String token = subscriberFcmTokenRepository.findBysubscriberUid(suid);

			NotificationDataDTO data = prepareNotificationData(subscriber.getFullName(), status);
			PushNotificationRequest request = new PushNotificationRequest();
			request.setTo(token);
			request.setPriority(Constant.HIGH);


			request.setData(status == CertificateStatus.ACTIVE ? data.getIssueCertNotificationData() : data.getFailedCertNotificationData());

			HttpEntity<String> entity = new HttpEntity<>(request.getNotificationRquest(), createJsonHeaders());


			ResponseEntity<String> result = restTemplate.postForEntity(PropertiesConstants.getNotification(),
					entity, String.class);

			logNotificationAudit(suid, result, status);
		} catch (Exception e) {
			logger.error("{} :: Notification failed for suid: {}", CLASS, suid, e);
		}
	}

	private NotificationDataDTO prepareNotificationData(String fullName, CertificateStatus status) {
		NotificationDataDTO notificationDataDTO = new NotificationDataDTO();
		notificationDataDTO.setTitle(Constant.HI + fullName);

		NotificationContextDTO context = new NotificationContextDTO();
		context.setpREF_CERTIFICATE_STATUS(status);
		notificationDataDTO.setNotificationContext(context);

		if (status == CertificateStatus.ACTIVE) {
			notificationDataDTO.setBody(Constant.CERTIFICATES_ARE_ISSUED_SUCCESSFULLY_PLEASE_SET_THE_PIN_FOR_FURTHER_USAGES);
		} else {
			notificationDataDTO.setBody(Constant.CERTIFICATES_ISSUANCE_FAILED_PLEASE_TRY_AFTER_SOME_TIME);
		}
		return notificationDataDTO;
	}

	private void logNotificationAudit(String suid, ResponseEntity<String> response, CertificateStatus status) {
		LogModelDTO log = new LogModelDTO();
		log.setIdentifier(suid);
		log.setStartTime(NativeUtils.getTimeStampString());
		log.setEndTime(NativeUtils.getTimeStampString());
		log.setTransactionType(TransactionType.BUSINESS.toString());
		log.setCorrelationID(NativeUtils.getUUId());
		log.setTransactionID(NativeUtils.getUUId());
		log.setServiceName(ServiceName.OTHER.toString());

		String resultLabel = (response != null && response.getStatusCode() == HttpStatus.OK) ? "successfully" : "failed";
		log.setLogMessage("RESPONSE ->> SUID :: " + suid + " | Notification send " + resultLabel + " for Certificates " + status);
		log.setLogMessageType((response != null && response.getStatusCode() == HttpStatus.OK) ? LogMessageType.SUCCESS.toString() : LogMessageType.FAILURE.toString());

		try {
			rabbitMQSender.send(NativeUtils.getLogModel(log));
		} catch (Exception e) {
			logger.error("Audit logging failed", e);
		}
	}

	private void auditTransaction(String identifier, String serviceName, LogMessageType type) {
		LogModelDTO log = new LogModelDTO();
		log.setIdentifier(identifier);
		log.setStartTime(NativeUtils.getTimeStampString());
		log.setEndTime(NativeUtils.getTimeStampString());
		log.setTransactionType(TransactionType.BUSINESS.toString());
		log.setCorrelationID(NativeUtils.getUUId());
		log.setTransactionID(NativeUtils.getUUId());
		log.setServiceName(serviceName);
		log.setLogMessageType(type.toString());
		try {
			rabbitMQSender.send(NativeUtils.getLogModel(log));
		} catch (Exception e) {
			logger.error("Transaction audit failed", e);
		}
	}

	private void saveWrappedKey(String sn, String key) {
		SubscriberWrappedKey wrapped = new SubscriberWrappedKey();
		wrapped.setCertificateSerialNumber(sn);
		wrapped.setWrappedKey(key);
		subscriberWrappedKeyRepository.save(wrapped);
	}

	private void updateSubscriberStatus(String suid, String status, String description) throws Exception {
		SubscriberStatusModel subStatus = subscriberStatusRepository.findBysubscriberUid(suid);
		if (subStatus != null) {
			subStatus.setSubscriberStatus(status);
			subStatus.setUpdatedDate(NativeUtils.getTimeStamp());
			subStatus.setSubscriberStatusDescription(description);
			subscriberStatusRepository.save(subStatus);
		}
	}

	private String determineCertType(String type) throws RAServiceException {
		if ("AUTH".equals(type)) return CertificateType.AUTH.toString();
		if ("SIGN".equals(type)) return CertificateType.SIGN.toString();
		throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
	}

	private void saveLifecycleStatus(Map<String, String> response, String status, String suid) throws Exception {
		SubscriberCertificateLifeCycle lifeCycle = new SubscriberCertificateLifeCycle();
		lifeCycle.setCertificateStatus(status);
		lifeCycle.setCreationDate(NativeUtils.getTimeStamp());
		lifeCycle.setSubscriberUniqueId(suid);
		lifeCycle.setCertificateType(determineCertType(response.get(Constant.CALLBACK_CERT_TYPE)));
		subscriberCertificateLifeCycleRepository.save(lifeCycle);
	}

	private SubscriberCertificates saveCertificateAndMetadata(Map<String, String> response, ServiceResponse res, String suid) throws Exception {
		SubscriberCertificates cert = new SubscriberCertificates();
		cert.setPkiKeyId(response.get(Constant.PKI_KEY_ID));
		cert.setCertificateData(res.getCertificate());
		cert.setCertificateStatus(CertificateStatus.ACTIVE.toString());
		cert.setCertificateType(determineCertType(response.get(Constant.CALLBACK_CERT_TYPE)));
		cert.setCertificateSerialNumber(res.getCertificate_serial_number());
		cert.setCertificateStartDate(NativeUtils.getTimeStamp(res.getIssueDate()));
		cert.setCertificateEndDate(NativeUtils.getTimeStamp(res.getExpiryDate()));
		cert.setSubscriberUniqueId(suid);
		cert.setCreationDate(NativeUtils.getTimeStamp());
		subscriberCertificatesRepository.save(cert);

		saveWrappedKey(res.getCertificate_serial_number(), res.getWrappedKey());
		saveLifecycleStatus(response, CertificateStatus.ACTIVE.toString(), suid);
		return cert;
	}

	private HttpHeaders createJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/**
	 * Gets the revoke reasons.
	 *
	 * @return the revoke reasons
	 */
	public String getRevokeReasons() {
		String str = """
        [
          {"index": "1",  "reason": "KEY_COMPROMISED"},
          {"index": "-2", "reason": "NO_REASON_CODE"},
          {"index": "3",  "reason": "AFFILIATION_CHANGED"},
          {"index": "4",  "reason": "SUPERSEDED"},
          {"index": "5",  "reason": "CESSATION_OF_OPERATION"},
          {"index": "6",  "reason": "CERTIFICATE_HOLD"},
          {"index": "9",  "reason": "PRIVILEGE_WITHDRAWN"}
        ]
        """;
		return str;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.RAServiceIface#
	 * getCertificateDetailsBySubscriberUniqueId(java.lang.String)
	 */
	@Override
	public String getCertificateDetailsBySubscriberUniqueId(String subscriberUniqueId)
			throws RAServiceException, Exception {
		try {
			logger.info("{} :: getCertificateDetailsBySubscriberUniqueId() :: id :: {}",
					CLASS, subscriberUniqueId);
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberCertificates subscriberCertificates = subscriberCertificatesRepository
					.findTopBySubscriberUniqueIdOrderByCreationDateDesc(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriberCertificates, ErrorCodes.E_CERTIFICATES_NOT_ISSUED);
			CertificateData certificateData = new CertificateData();
			if (subscriberCertificates.getCertificateStatus() != null) {
				certificateData.setCertStatus(subscriberCertificates.getCertificateStatus());
			}
			String[] expDate = subscriberCertificates.getCertificateEndDate().toString().split(" ");
			certificateData.setExpiryDate(expDate[0]);
			String[] issueDate = subscriberCertificates.getCertificateStartDate().toString().split(" ");
			certificateData.setIssueDate(issueDate[0]);
			if (subscriberCertificates.getUpdatedDate() != null) {
				String[] revokeDate = subscriberCertificates.getUpdatedDate().toString().split(" ");
				certificateData.setRevokeDate(revokeDate[0]);
			}
			certificateData.setStatus(true);
			logger.info(CLASS + " :: getCertificateDetailsBySubscriberUniqueId() :: response :: success.");
			return certificateData.toString();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDetailsBySubscriberUniqueId() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDetailsBySubscriberUniqueId() :: IN RAServiceException {}",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDetailsBySubscriberUniqueId() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/**
	 * Gets the certificate life cycle logs by subscriber id.
	 *
	 * @param subscriberUniqueId the subscriber unique id
	 * @return the certificate life cycle logs by subscriber id
	 * @throws RAServiceException the RA service exception
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see com.dt.ra.service.iface.RAServiceIface#
	 * getCertificateLifeCycleLogsBySubscriberId(com.dt.ra.service.model.
	 * RAPKISubscriberdata)
	 */
	@Override
	public String getCertificateLifeCycleLogsBySubscriberUniqueId(String subscriberUniqueId)
			throws RAServiceException, Exception {
		try {
			logger.info("{} :: getCertificateLifeCycleLogsBySubscriberId() :: id :: {}",
					CLASS, subscriberUniqueId);
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			List<SubscriberCertificateLifeCycle> lifeCyclesLogs = subscriberCertificateLifeCycleRepository
					.findBysubscriberUniqueId(subscriberUniqueId);
			RAServiceAsserts.notNullorEmpty(lifeCyclesLogs.size(), Constant.CERTIFICATE_LOGS_NOT_FOUND);
			logger.info(CLASS + " :: getCertificateLifecycleLogs():: Success.");
			return lifeCyclesLogs.toString();
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateLifeCycleLogsBySubscriberUniqueId() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateLifeCycleLogsBySubscriberUniqueId() :: IN RAServiceException {}",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateLifeCycleLogsBySubscriberUniqueId() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.dtt.ra.service.iface.RAServiceIface#verifyCertficatesPins(com.dtt.ra.
	 * request.entity.VerifyCertificatesPins)
	 */
	@Override
	public String verifyCertficatesPins(VerifyCertificatesPins certificatesPins) throws RAServiceException, Exception {
		logger.info("{} :: verifyCertficatesPins() :: req :: certificatesPins :: {}",
				CLASS, certificatesPins);
		try {
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(certificatesPins.getSubscriberUId());
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			List<SubscriberCertificates> subscriberCertificates = subscriberCertificatesRepository
					.findByCertificateStatusAndsubscriberUniqueId(
							CertificateStatus.ACTIVE.toString(),
							certificatesPins.getSubscriberUId()
					);
			if (subscriberCertificates.isEmpty()) {
				throw new RAServiceException(ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			}
			else {
				boolean result = false;
				for (SubscriberCertificates certificate : subscriberCertificates) {
					SubscriberCertificatePinHistory subscriberCertificatePinHistory = subscriberCertificatePinHistoryRepository
							.findBysubscriberUniqueId(certificate.getSubscriberUniqueId());
					logModelDTO.setIdentifier(certificatesPins.getSubscriberUId());
					logModelDTO.setServiceName(null);
					logModelDTO.setLogMessage(Constant.REQUEST);
					logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
					logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
					logModelDTO.setTransactionSubType(null);
					logModelDTO.setCorrelationID(NativeUtils.getUUId());
					logModelDTO.setTransactionID(NativeUtils.getUUId());
					logModelDTO.setSubTransactionID(null);
					logModelDTO.setGeoLocation(null);
					logModelDTO.setServiceProviderName(null);
					logModelDTO.setServiceProviderAppName(null);
					logModelDTO.setSignatureType(null);
					logModelDTO.seteSealUsed(false);

					if (certificate.getCertificateType().equals(CertificateType.SIGN.toString())) {
						String signingPin = subscriberCertificatePinHistory.getSigningCertificatePinList();
						List<String> pinList = Arrays.asList(signingPin.split(", "));
						certificatesPins.setCurrentSigningPassword(pinList.get(pinList.size() - 1));
						certificatesPins.setSigningPassword(certificatesPins.getSigningPin());
					}
					if (certificate.getCertificateType().equals(CertificateType.AUTH.toString())) {
						String authenticationPin = subscriberCertificatePinHistory
								.getAuthenticationCertificatePinList();
						List<String> pinList = Arrays.asList(authenticationPin.split(", "));
						certificatesPins.setCurrentSigningPassword(pinList.get(pinList.size() - 1));
						certificatesPins.setSigningPassword(certificatesPins.getAuthPin());
					}
					SubscriberWrappedKey subscriberWrappedKey = subscriberWrappedKeyRepository
							.findBycertificateSerialNumber(certificate.getCertificateSerialNumber());
					certificatesPins.setWrappedKey(subscriberWrappedKey.getWrappedKey());
					logModelDTO.setCallStack(certificatesPins.toString());

					String baseUrl = PropertiesConstants.getPkiUrl();
					PostRequest request = new PostRequest();
					request.setRequestBody(logModelDTO.toString());
					request.setHashdata(logModelDTO.toString().hashCode());
					RequestEntity requestEntity = new RequestEntity();
					requestEntity.setPostRequest(request);
					requestEntity.setTransactionType(Constant.VERIFY_PIN);
					ResponseEntity<String> httpResponse = restTemplate.postForEntity(baseUrl, requestEntity,
							String.class);
					if (Constant.TRANSACTION_TYPE_NOT_FOUND.equals(httpResponse.getBody())) {
						throw new RAServiceException(ErrorCodes.E_TRANSACTION_TYPE_NOT_FOUND);
					}
					if (Constant.REQUEST_IS_NOT_VALID.equals(httpResponse.getBody())) {
						throw new RAServiceException(ErrorCodes.E_REQUEST_DATA_IS_NOT_VALID);
					}
					ServiceResponse serviceResponse = objectMapper.readValue(httpResponse.getBody(),
							ServiceResponse.class);
					logger.info("{} :: verifyCertficatesPins() :: native response :: {}", CLASS, serviceResponse.getStatus());
					logModelDTO.setCallStack(null);
					logModelDTO.setLogMessage(Constant.RESPONSE);
					if (serviceResponse.getStatus().equals(Constant.FAIL)) {
						ErrorCodes.setResponse(serviceResponse);
						logger.info("{} :: verifyCertficatesPins :: error :: {}", CLASS, serviceResponse.getError_message());
						logModelDTO.setLogMessageType(LogMessageType.ERROR.toString());
						logModelDTO.setEndTime(NativeUtils.getTimeStampString());
						LogModel logModel = NativeUtils.getLogModel(logModelDTO);
						rabbitMQSender.send(logModel);
						throw new RAServiceException(serviceResponse.getError_message());
					} else {
						logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
						logModelDTO.setEndTime(NativeUtils.getTimeStampString());
						Result checksumResult = DAESService.addChecksumToTransaction(logModelDTO.toString());
						logger.info("{} :: verifyCertficatesPins() :: checksumResult :: {}",
								CLASS,
								checksumResult.getResponse() != null ? new String(checksumResult.getResponse()) : "null");
						LogModel logModel = objectMapper.readValue(new String(checksumResult.getResponse()),
								LogModel.class);

						rabbitMQSender.send(logModel);
						result = true;
					}
				}
				if (result)
					return Constant.PIN_VERIFICATION_SUCCESS;
				else
					return Constant.PIN_VERIFICATION_FAILED;
			}
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " verifyCertficatesPins() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " verifyCertficatesPins() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " verifyCertficatesPins() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public String getCertificateDataByCertificateType(String subscriberUid, String certType)
			throws RAServiceException, Exception {
		try {
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUid);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberCertificates subscriberCertificate = subscriberCertificatesRepository
					.findByCertificateStatusAndsubscriberUniqueIdAndCertificateType(CertificateStatus.ACTIVE.toString(),
							subscriberUid, certType);
			RAServiceAsserts.notNullorEmpty(subscriberCertificate, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			if (subscriberCertificate.getCertificateType().equals(certType))
				return subscriberCertificate.getCertificateData();
			else
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateType() :: IN DATABASE EXCEPTION {}", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateType() :: IN RAServiceException {}", e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateType() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	public String getOrgCertDetails(String orgId, String certType) throws Exception {
		try {
			String certData = organizationCertificatesRepository.getOrgCertData(orgId);
			return certData;
		} catch (Exception e) {
			throw new Exception(
					messageSource.getMessage("api.error.please.try.after.some.time.server.down", null, Locale.ENGLISH));
		}
	}


	@Override
	public String getOrganizationCertificateDataByCertificateType(String orgId, String certType)
			throws RAServiceException, Exception {
		try {
			logger.info("{} :: getOrganizationCertificateDataByCertificateType(), orgId >> {} certType >> {}",
					CLASS, orgId, certType);			OrganizationDetails organizationDetails = organizationDetailsRepository.findByOrganizationUid(orgId);
			RAServiceAsserts.notNullorEmpty(organizationDetails, ErrorCodes.E_ORGANIZATION_DATA_NOT_FOUND);
			OrganizationCertificates organizationCertificates = organizationCertificatesRepository
					.findByCertificateStatusAndOrganizationUniqueId(CertificateStatus.ACTIVE.toString(),
							organizationDetails.getOrganizationUid());
			RAServiceAsserts.notNullorEmpty(organizationCertificates, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			if (organizationCertificates.getCertificateType().equals(certType))
				return organizationCertificates.getCertificateData();
			else
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateType() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateType() :: RAServiceException {} ",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateType() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public ApiResponses getCertificateDataByCertificateTypeForAgent(String subscriberUid, String certType)
			throws RAServiceException, Exception {
		try {
			Subscriber subscriber = subscriberRepository.findBysubscriberUid(subscriberUid);
			RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_SUBSCRIBER_DATA_NOT_FOUND);
			SubscriberCertificates subscriberCertificate = subscriberCertificatesRepository
					.findByCertificateStatusAndsubscriberUniqueIdAndCertificateType(CertificateStatus.ACTIVE.toString(),
							subscriberUid, certType);
			String certificateSerialNumber = subscriberCertificate.getCertificateSerialNumber();

			SubscriberWrappedKey subscriberWrappedKey = subscriberWrappedKeyRepository
					.findBycertificateSerialNumber(certificateSerialNumber);

			SubscriberCertForAgentDto subscriberCertForAgentDto = new SubscriberCertForAgentDto();
			subscriberCertForAgentDto.setSubscriberCertificate(subscriberCertificate.getCertificateData());
			subscriberCertForAgentDto.setWrappedKey(subscriberWrappedKey.getWrappedKey());

			RAServiceAsserts.notNullorEmpty(subscriberCertificate, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			if (subscriberCertificate.getCertificateType().equals(certType))
				return AppUtil.createApiResponse(true,
						messageSource.getMessage("api.response.subcriber.certificate.details", null, Locale.ENGLISH),
						subscriberCertForAgentDto);
			else
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateTypeForAgent() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateTypeForAgent() :: IN RAServiceException {}",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getCertificateDataByCertificateTypeForAgent() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Override
	public ApiResponses getOrganizationCertificateDataByCertificateTypeForAgent(String orgId, String certType)
			throws Exception {
		try {
			logger.info("{} :: getOrganizationCertificateDataByCertificateTypeForAgent(), orgId >> {} certType >> {}",
					CLASS, orgId, certType);
			OrganizationDetails organizationDetails = organizationDetailsRepository.findByOrganizationUid(orgId);
			RAServiceAsserts.notNullorEmpty(organizationDetails, ErrorCodes.E_ORGANIZATION_DATA_NOT_FOUND);
			OrganizationCertificates organizationCertificates = organizationCertificatesRepository
					.findByCertificateStatusAndOrganizationUniqueId(CertificateStatus.ACTIVE.toString(),
							organizationDetails.getOrganizationUid());
			RAServiceAsserts.notNullorEmpty(organizationCertificates, ErrorCodes.E_ACTIVE_CERTIFICATE_NOT_FOUND);
			SubscriberCertForAgentDto subscriberCertForAgentDto = new SubscriberCertForAgentDto();
			subscriberCertForAgentDto.setOrganizationCertificate(organizationCertificates.getCertificateData());
			subscriberCertForAgentDto.setOrgWrappedKey(organizationCertificates.getWrappedKey());
			if (organizationCertificates.getCertificateType().equals(certType))
				return AppUtil.createApiResponse(true,
						messageSource.getMessage("api.response.organization.certificate", null, Locale.ENGLISH),
						subscriberCertForAgentDto);
			else
				throw new RAServiceException(ErrorCodes.E_CERTIFICATE_TYPE_NOT_FOUND);
		} catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				 | PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateTypeForAgent() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (RAServiceException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateTypeForAgent() :: IN RAServiceException {}",
					e.getMessage());
			throw new RAServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " getOrganizationCertificateDataByCertificateTypeForAgent() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

	@Transactional(
			rollbackFor = {Exception.class}
	)
	@Override
	public ApiResponses expireSubscriberCert(ExpireSubscriberCertRequestDTO expireSubscriberCertRequestDTO) throws Exception {

		try {

			Subscriber subscriber = null;
			if (expireSubscriberCertRequestDTO.getEmail() != null && !expireSubscriberCertRequestDTO.getEmail().isEmpty()) {
				subscriber = (Subscriber) this.subscriberRepository.getbyEmailId(expireSubscriberCertRequestDTO.getEmail());
				RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_EMAIL_NOT_FOUND);}

			if (expireSubscriberCertRequestDTO.getMobileNumber() != null && !expireSubscriberCertRequestDTO.getMobileNumber().isEmpty()) {
				subscriber = subscriberRepository.findLatestByMobileNo(expireSubscriberCertRequestDTO.getMobileNumber());


				RAServiceAsserts.notNullorEmpty(subscriber, ErrorCodes.E_MOBILE_NUMBER_NOT_FOUND);}



			LogModelDTO logModelDTO = new LogModelDTO();
			logModelDTO.setStartTime(NativeUtils.getTimeStampString());
			logModelDTO.setServiceName(null);
			logModelDTO.setLogMessage("REQUEST");
			logModelDTO.setLogMessageType(LogMessageType.INFO.toString());
			logModelDTO.setTransactionType(TransactionType.BUSINESS.toString());
			logModelDTO.setTransactionSubType(null);
			logModelDTO.setCorrelationID(NativeUtils.getUUId());
			logModelDTO.setTransactionID(NativeUtils.getUUId());
			logModelDTO.setSubTransactionID(null);
			logModelDTO.setGeoLocation(null);
			logModelDTO.setServiceProviderName(null);
			logModelDTO.setServiceProviderAppName(null);
			logModelDTO.setSignatureType(null);
			logModelDTO.seteSealUsed(false);

			List<SubscriberCertificates> subscriberCertificates =
					subscriberCertificatesRepository.findBySubscriberUniqueIdToExpireCert(subscriber.getSubscriberUid());

			RAServiceAsserts.notNullorEmpty(subscriberCertificates, ErrorCodes.E_CERTIFICATES_NOT_ISSUED);

			for (SubscriberCertificates cert : subscriberCertificates) {
				if (cert.getCertificateStatus().equals("EXPIRED")) {
					return AppUtil.createApiResponse(true, "Certificates are already expired", (Object)null);
				}

				logger.info("It came here updating status of certificate here");
				cert.setCertificateStatus("EXPIRED");
				Date currentExpiry = cert.getCertificateEndDate();
				if (currentExpiry != null) {
					LocalDateTime localExpiry = currentExpiry.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					LocalDateTime updatedExpiry = localExpiry.minusYears(2);
					Date newExpiryDate = Date.from(updatedExpiry.atZone(ZoneId.systemDefault()).toInstant());
					cert.setCertificateEndDate(newExpiryDate);
				}

				subscriberCertificatesRepository.save(cert);

				// Update lifecycle
				SubscriberCertificateLifeCycle lifeCycle = new SubscriberCertificateLifeCycle();
				lifeCycle.setCertificateSerialNumber(cert.getCertificateSerialNumber());
				lifeCycle.setCertificateStatus(CertificateStatus.EXPIRED.toString());
				lifeCycle.setSubscriberUniqueId(cert.getSubscriberUniqueId());
				lifeCycle.setCertificateType(cert.getCertificateType());
				lifeCycle.setCreationDate(NativeUtils.getTimeStamp());
				subscriberCertificateLifeCycleRepository.save(lifeCycle);


				SubscriberStatusModel subscriberStatus =
						subscriberStatusRepository.findBysubscriberUid(cert.getSubscriberUniqueId());
				String subscriberFcmToken =
						subscriberFcmTokenRepository.findBysubscriberUid(cert.getSubscriberUniqueId());

				if (subscriberStatus != null) {
					subscriberStatus.setSubscriberStatus(Constant.CERT_EXPIRED);
					subscriberStatus.setUpdatedDate(NativeUtils.getTimeStamp());
					subscriberStatus.setSubscriberStatusDescription("Certificates are Expired.");
					subscriberStatusRepository.save(subscriberStatus);
				}

				NotificationContextDTO context = new NotificationContextDTO();
				context.setpREF_CERTIFICATE_STATUS(CertificateStatus.EXPIRED);

				NotificationDataDTO notificationData = new NotificationDataDTO();
				notificationData.setTitle("Hi " + subscriber.getFullName());
				notificationData.setBody("Your " + cert.getCertificateType() + " Certificate is expired.");
				notificationData.setNotificationContext(context);

				PushNotificationRequest notificationRequest = new PushNotificationRequest();
				notificationRequest.setTo(subscriberFcmToken);
				notificationRequest.setPriority("high");
				notificationRequest.setData(notificationData.getExpiredCertNotificationData());



				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<>(notificationRequest.getNotificationRquest(), headers);

				if (!notificationSent) {
					try {
						ResponseEntity<String> response = restTemplate.postForEntity(
								PropertiesConstants.getNotification(), entity, String.class);
						notificationSent = true;
					} catch (Exception ex) {
						logger.warn("FCM Notification failed.", ex);
					}
				}

				// Logging to RabbitMQ
				logModelDTO.setLogMessage("RESPONSE");
				logModelDTO.setLogMessageType(LogMessageType.SUCCESS.toString());
				logModelDTO.setEndTime(NativeUtils.getTimeStampString());

				try {
					LogModel logModel = NativeUtils.getLogModel(logModelDTO);
					rabbitMQSender.send(logModel);
				} catch (Exception ex) {
					logger.warn("Service log failed", ex);
				}
			}
			return AppUtil.createApiResponse(true,"Certificate Expired suceesfully",null);

		}catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " expireSubscriberCert() :: IN DATABASE EXCEPTION {}",
					e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			logger.error(CLASS + " expireSubscriberCert() :: exception {} ", e.getMessage());
			throw new Exception(messageSource.getMessage("api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH));
		}
	}

//	@Override
//	public ApiResponse fetchVisitorDetailsBySearchType(int searchType, String searchValue) {
//		try{
//			VisitorCompleteDetails visitorCompleteDetails= new VisitorCompleteDetails();
//			switch (searchType) {
//				case 1:
//					visitorCompleteDetails = visitorCompleteDetailsRepository.fetchVisitorByIdocNumber(searchValue,"1");
//					if(visitorCompleteDetails==null){
//						return AppUtil.createApiResponse(false,"National Id Not Found",null);
//					}
//
//					break;
//				case 2:
//					visitorCompleteDetails = visitorCompleteDetailsRepository.fetchVisitorByIdocNumber(searchValue,"3");
//					if(visitorCompleteDetails==null){
//						return AppUtil.createApiResponse(false,"Passport Not Found",null);
//					}
//
//					break;
//				case 3:
//					visitorCompleteDetails = visitorCompleteDetailsRepository.fetchVisitorByEmail(searchValue);
//					if(visitorCompleteDetails==null){
//						return AppUtil.createApiResponse(false,"Email Id Not Found",null);
//					}
//
//					break;
//				case 4:
//					visitorCompleteDetails=visitorCompleteDetailsRepository.fetchVisitorByMobileNumber(searchValue);
//					if(visitorCompleteDetails==null){
//						return AppUtil.createApiResponse(false,"Mobile Number Not Found",null);
//					}
//					break;
//				default:
//					return AppUtil.createApiResponse(false,"Please Select Valid Identifier",null);
//			}
//
//
//			if(visitorCompleteDetails.getSubscriberType().equals("Visitor")){
//				System.out.println("DATATDA "+visitorCompleteDetails.getDob());
//				;
//				String passportNumber=visitorCompleteDetails.getIdDocNumber();
//				int count= subscriberTravelHistoryRepo.noOfEntries("CLEARED","ENTRY",passportNumber);
//				SubscriberTravelHistory subscriberTravelHistoryEntry=subscriberTravelHistoryRepo.findLastTravelDate("CLEARED","ENTRY",passportNumber);
//				SubscriberTravelHistory subscriberTravelHistoryExit=subscriberTravelHistoryRepo.findLastTravelDate("CLEARED","EXIT",passportNumber);
//				SubscriberTravelHistory subscriberTravelHistoryLatest=subscriberTravelHistoryRepo.findLatestCleared(passportNumber);
//				VisitorCompleteDetailsDto visitorCompleteDetailsDto=new VisitorCompleteDetailsDto();
//				if(visitorCompleteDetails.getAuthPinSetDate()!=null ){
//					visitorCompleteDetailsDto.setAuthPinSetDate(visitorCompleteDetails.getAuthPinSetDate().substring(0,10));
//				}
//				if(visitorCompleteDetails.getCertificateExpiryDate()!=null ){
//					visitorCompleteDetailsDto.setCertificateExpiryDate(visitorCompleteDetails.getCertificateExpiryDate().substring(0,10));
//				}
//				if(visitorCompleteDetails.getCertificateIssueDate()!=null ){
//					visitorCompleteDetailsDto.setCertificateIssueDate(visitorCompleteDetails.getCertificateIssueDate().substring(0,10));
//				}
//
//
//				visitorCompleteDetailsDto.setDob(visitorCompleteDetails.getDob().substring(0,10));
//				System.out.println(visitorCompleteDetailsDto.getDob());
//				if(visitorCompleteDetails.getCreatedOn()!=null ){
//					visitorCompleteDetailsDto.setCreatedOn(visitorCompleteDetails.getCreatedOn().substring(0,10));
//				}
//				visitorCompleteDetailsDto.setCertificateSerialNumber(visitorCompleteDetails.getCertificateSerialNumber());
//				visitorCompleteDetailsDto.setCertificateStatus(visitorCompleteDetails.getCertificateStatus());
//				visitorCompleteDetailsDto.setCountryName(visitorCompleteDetails.getCountryName());
//				if(visitorCompleteDetails.getDeviceRegistrationTime()!=null ){
//					visitorCompleteDetailsDto.setDeviceRegistrationTime(visitorCompleteDetails.getDeviceRegistrationTime().substring(0,10));
//				}
//				visitorCompleteDetailsDto.setDeviceStatus(visitorCompleteDetails.getDeviceStatus());
//				visitorCompleteDetailsDto.seteMail(visitorCompleteDetails.geteMail());
//				visitorCompleteDetailsDto.setEmployer(visitorCompleteDetails.getEmployer());
//				visitorCompleteDetailsDto.setFullName(visitorCompleteDetails.getFullName());
//				visitorCompleteDetailsDto.setGender(visitorCompleteDetails.getGender());
//				visitorCompleteDetailsDto.setGeoLocation(visitorCompleteDetails.getGeoLocation());
//				visitorCompleteDetailsDto.setIdDocImage(visitorCompleteDetails.getIdDocImage());
//				visitorCompleteDetailsDto.setIdDocNumber(visitorCompleteDetails.getIdDocNumber());
//				visitorCompleteDetailsDto.setPassportNumber(visitorCompleteDetails.getIdDocNumber());
//				visitorCompleteDetailsDto.setIdDocType(visitorCompleteDetails.getIdDocType());
//
//				visitorCompleteDetailsDto.setNoOfEntries(count);
//				visitorCompleteDetailsDto.setNonResidentCardStatus(visitorCompleteDetails.getNonResidentCardStatus());
//				visitorCompleteDetailsDto.setNonResidentId(visitorCompleteDetails.getNonResidentId());
//				visitorCompleteDetailsDto.setLevelOfAssurance(visitorCompleteDetails.getLevelOfAssurance());
//				visitorCompleteDetailsDto.setMobileNumber(visitorCompleteDetails.getMobileNumber());
//				visitorCompleteDetailsDto.setOnBoardingMethod(visitorCompleteDetails.getOnBoardingMethod());
//				if(visitorCompleteDetails.getOnBoardingTime()!=null ){
//					visitorCompleteDetailsDto.setOnBoardingTime(visitorCompleteDetails.getOnBoardingTime().substring(0,10));
//				}
//				if(visitorCompleteDetails.getPassportExpiryDate()!=null ){
//					visitorCompleteDetailsDto.setPassportExpiryDate(visitorCompleteDetails.getPassportExpiryDate().substring(0,10));
//				}
//
//				visitorCompleteDetailsDto.setPhoto(visitorCompleteDetails.getPhoto());
//				if(visitorCompleteDetails.getRevocationDate()!=null ){
//					visitorCompleteDetailsDto.setRevocationDate(visitorCompleteDetails.getRevocationDate().substring(0,10));
//				}
//
//				visitorCompleteDetailsDto.setRevocationReason(visitorCompleteDetails.getRevocationReason());
//				visitorCompleteDetailsDto.setSelfieUri(visitorCompleteDetails.getSelfieUri());
//
//				if(visitorCompleteDetails.getSignPinSetDate()!=null ){
//					visitorCompleteDetailsDto.setSignPinSetDate(visitorCompleteDetails.getSignPinSetDate().substring(0,10));
//				}
//				visitorCompleteDetailsDto.setSubscriberStatus(visitorCompleteDetails.getSubscriberStatus());
//				visitorCompleteDetailsDto.setSubscriberType(visitorCompleteDetails.getSubscriberType());
//				visitorCompleteDetailsDto.setSubscriberUid(visitorCompleteDetails.getSubscriberUid());
//				visitorCompleteDetailsDto.setVideoType(visitorCompleteDetails.getVideoType());
//				visitorCompleteDetailsDto.setVideoUrl(visitorCompleteDetails.getVideoUrl());
//				if(visitorCompleteDetails.getVisaExpiryDate()!=null ){
//					visitorCompleteDetailsDto.setVisaExpiryDate(visitorCompleteDetails.getVisaExpiryDate().substring(0,10));
//				}
//
//				visitorCompleteDetailsDto.setVisaNumber(visitorCompleteDetails.getVisaNumber());
//				visitorCompleteDetailsDto.setBlackListed(visitorCompleteDetails.getBlacklisted());
//				visitorCompleteDetailsDto.setVisaType(visitorCompleteDetails.getVisaType());
//				visitorCompleteDetailsDto.setOnboardingDocument(visitorCompleteDetails.getOnboardingDocumnet());
//				if(subscriberTravelHistoryLatest!=null) {
//					visitorCompleteDetailsDto.setImmigrationStatus(subscriberTravelHistoryLatest.getImmigrationType());
//				}
//				if(subscriberTravelHistoryEntry!=null) {
//					visitorCompleteDetailsDto.setLastEntryDate(subscriberTravelHistoryEntry.getTravelDate().substring(0,10));
//				}
//				if(subscriberTravelHistoryExit!=null){
//					visitorCompleteDetailsDto.setLastExitDate(subscriberTravelHistoryExit.getTravelDate().substring(0,10));
//
//
//				}
//				if(visitorCompleteDetails.getVisaIssueDate()!=null){
//					visitorCompleteDetailsDto.setVisaIssueDate(visitorCompleteDetails.getVisaIssueDate().substring(0,10));
//				}
//				visitorCompleteDetailsDto.setResidentId(visitorCompleteDetails.getResidentId());
//
//							ObjectMapper objectMapper= new ObjectMapper();
//			String subscriberDetails = objectMapper.writeValueAsString(visitorCompleteDetailsDto);
//			return AppUtil.createApiResponse(true,"Subscriber Details Fetched Successfully",subscriberDetails);
//			}
//			VisitorCompleteDetailsDto visitorCompleteDetailsDto=new VisitorCompleteDetailsDto();
//			if(visitorCompleteDetails.getAuthPinSetDate()!=null ){
//				visitorCompleteDetailsDto.setAuthPinSetDate(visitorCompleteDetails.getAuthPinSetDate().substring(0,10));
//			}
//			if(visitorCompleteDetails.getCertificateExpiryDate()!=null ){
//				visitorCompleteDetailsDto.setCertificateExpiryDate(visitorCompleteDetails.getCertificateExpiryDate().substring(0,10));
//			}
//			if(visitorCompleteDetails.getCertificateIssueDate()!=null ){
//				visitorCompleteDetailsDto.setCertificateIssueDate(visitorCompleteDetails.getCertificateIssueDate().substring(0,10));
//			}
//			if(visitorCompleteDetails.getCertificateIssueDate()!=null ){
//				visitorCompleteDetailsDto.setCertificateIssueDate(visitorCompleteDetails.getCertificateIssueDate().substring(0,10));
//			}
//			System.out.println("DATATDA "+visitorCompleteDetails.getDob());
//			visitorCompleteDetailsDto.setDob(visitorCompleteDetails.getDob().substring(0,10));
//
//			if(visitorCompleteDetails.getCreatedOn()!=null ){
//				visitorCompleteDetailsDto.setCreatedOn(visitorCompleteDetails.getCreatedOn().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setCertificateSerialNumber(visitorCompleteDetails.getCertificateSerialNumber());
//			visitorCompleteDetailsDto.setCertificateStatus(visitorCompleteDetails.getCertificateStatus());
//			visitorCompleteDetailsDto.setCountryName(visitorCompleteDetails.getCountryName());
//			if(visitorCompleteDetails.getDeviceRegistrationTime()!=null ){
//				visitorCompleteDetailsDto.setDeviceRegistrationTime(visitorCompleteDetails.getDeviceRegistrationTime().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setDeviceStatus(visitorCompleteDetails.getDeviceStatus());
//			visitorCompleteDetailsDto.seteMail(visitorCompleteDetails.geteMail());
//			visitorCompleteDetailsDto.setEmployer(visitorCompleteDetails.getEmployer());
//			visitorCompleteDetailsDto.setFullName(visitorCompleteDetails.getFullName());
//			visitorCompleteDetailsDto.setGender(visitorCompleteDetails.getGender());
//			visitorCompleteDetailsDto.setGeoLocation(visitorCompleteDetails.getGeoLocation());
//			visitorCompleteDetailsDto.setIdDocImage(visitorCompleteDetails.getIdDocImage());
//			visitorCompleteDetailsDto.setIdDocNumber(visitorCompleteDetails.getIdDocNumber());
//			if(visitorCompleteDetails.getIdDocType().equals("3")) {
//				visitorCompleteDetailsDto.setPassportNumber(visitorCompleteDetails.getIdDocNumber());
//			}
//			visitorCompleteDetailsDto.setIdDocType(visitorCompleteDetails.getIdDocType());
//			visitorCompleteDetailsDto.setNonResidentCardStatus(visitorCompleteDetails.getNonResidentCardStatus());
//			visitorCompleteDetailsDto.setNonResidentId(visitorCompleteDetails.getNonResidentId());
//			visitorCompleteDetailsDto.setLevelOfAssurance(visitorCompleteDetails.getLevelOfAssurance());
//			visitorCompleteDetailsDto.setMobileNumber(visitorCompleteDetails.getMobileNumber());
//			visitorCompleteDetailsDto.setOnBoardingMethod(visitorCompleteDetails.getOnBoardingMethod());
//
//			if(visitorCompleteDetails.getOnBoardingTime()!=null ){
//				visitorCompleteDetailsDto.setOnBoardingTime(visitorCompleteDetails.getOnBoardingTime().substring(0,10));
//			}
//			if(visitorCompleteDetails.getPassportExpiryDate()!=null ){
//				visitorCompleteDetailsDto.setPassportExpiryDate(visitorCompleteDetails.getPassportExpiryDate().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setPhoto(visitorCompleteDetails.getPhoto());
//			if(visitorCompleteDetails.getRevocationDate()!=null ){
//				visitorCompleteDetailsDto.setRevocationDate(visitorCompleteDetails.getRevocationDate().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setRevocationReason(visitorCompleteDetails.getRevocationReason());
//			visitorCompleteDetailsDto.setSelfieUri(visitorCompleteDetails.getSelfieUri());
//			if(visitorCompleteDetails.getSignPinSetDate()!=null ){
//				visitorCompleteDetailsDto.setSignPinSetDate(visitorCompleteDetails.getSignPinSetDate().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setSubscriberStatus(visitorCompleteDetails.getSubscriberStatus());
//			visitorCompleteDetailsDto.setSubscriberType(visitorCompleteDetails.getSubscriberType());
//			visitorCompleteDetailsDto.setSubscriberUid(visitorCompleteDetails.getSubscriberUid());
//			visitorCompleteDetailsDto.setVideoType(visitorCompleteDetails.getVideoType());
//			visitorCompleteDetailsDto.setVideoUrl(visitorCompleteDetails.getVideoUrl());
//			if(visitorCompleteDetails.getVisaExpiryDate()!=null ){
//				visitorCompleteDetailsDto.setVisaExpiryDate(visitorCompleteDetails.getVisaExpiryDate().substring(0,10));
//			}
//			if(visitorCompleteDetails.getVisaIssueDate()!=null){
//				visitorCompleteDetailsDto.setVisaIssueDate(visitorCompleteDetails.getVisaIssueDate().substring(0,10));
//			}
//			visitorCompleteDetailsDto.setVisaNumber(visitorCompleteDetails.getVisaNumber());
//			visitorCompleteDetailsDto.setBlackListed(visitorCompleteDetails.getBlacklisted());
//			visitorCompleteDetailsDto.setOnboardingDocument(visitorCompleteDetails.getOnboardingDocumnet());
//			visitorCompleteDetailsDto.setVisaType(visitorCompleteDetails.getVisaType());
//			ObjectMapper objectMapper= new ObjectMapper();
//			String subscriberDetails = objectMapper.writeValueAsString(visitorCompleteDetailsDto);
//			visitorCompleteDetailsDto.setResidentId(visitorCompleteDetails.getResidentId());
//			return AppUtil.createApiResponse(true,"Subscriber Details Fetched Successfully",subscriberDetails);
//
//		}catch (JDBCConnectionException | ConstraintViolationException | DataException | LockAcquisitionException
//				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
//			logger.error("Unexpected exception", e);
//
//			return AppUtil.createApiResponse(false, "Something went wrong please try after sometime", null);
//		} catch (Exception e) {
//
//			logger.error("Unexpected exception", e);
//			return AppUtil.createApiResponse(false, "Something went wrong please try after sometime", null);
//		}
//	}



}
