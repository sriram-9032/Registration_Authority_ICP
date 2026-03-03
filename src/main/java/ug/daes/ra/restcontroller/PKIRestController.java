/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.restcontroller;

import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ug.daes.ra.asserts.RAServiceAsserts;
import ug.daes.ra.config.SentryClientExceptions;
import ug.daes.ra.dto.RARequestDTO;
import ug.daes.ra.request.entity.SetPinModelDto;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.request.entity.AuthenticatePKIModel;
import ug.daes.ra.request.entity.GenerateSignature;
import ug.daes.ra.request.entity.SetPinModel;
import ug.daes.ra.request.entity.VerifyCertificatesPins;
import ug.daes.ra.response.entity.APIResponse;
import ug.daes.ra.service.iface.implementation.RAPKIServiceIfaceImpl;
import ug.daes.ra.service.iface.implementation.RAServiceIfaceImpl;
import ug.daes.ra.utils.Constant;
import ug.daes.ra.utils.PropertiesConstants;

@RestController
@RequestMapping(value = "/api/")
public class PKIRestController {

	static Logger logger = LoggerFactory.getLogger(PKIRestController.class);
	private static final String CLASS = "RACertificatesRestController";





	private final SentryClientExceptions sentryClientExceptions;
	private final RAServiceIfaceImpl raServiceIfaceImpl;
	private final RAPKIServiceIfaceImpl rapkiServiceIfaceImpl;
	private final RestTemplate restTemplate;
	private final MessageSource messageSource;

	public PKIRestController(
			SentryClientExceptions sentryClientExceptions,
			RAServiceIfaceImpl raServiceIfaceImpl,
			RAPKIServiceIfaceImpl rapkiServiceIfaceImpl,
			RestTemplate restTemplate,
			MessageSource messageSource) {

		this.sentryClientExceptions = sentryClientExceptions;
		this.raServiceIfaceImpl = raServiceIfaceImpl;
		this.rapkiServiceIfaceImpl = rapkiServiceIfaceImpl;
		this.restTemplate = restTemplate;
		this.messageSource = messageSource;
	}

	@GetMapping(value = "get/service/status", produces = "application/json")
	public APIResponse getServiceStatus() {

		try {

			ResponseEntity<String> response =
					restTemplate.getForEntity(PropertiesConstants.getStatus(), String.class);

			boolean isRunning = Boolean.parseBoolean(
					Optional.ofNullable(response.getBody()).orElse("false")
			);

			if (isRunning) {

				logger.info("{} :: getServiceStatus > ra.service.is.running", CLASS);

				return new APIResponse(
						true,
						messageSource.getMessage(
								"api.response.ra.service.is.running",
								null,
								Locale.ENGLISH
						),
						null
				);
			}

			throw new IllegalStateException(ErrorCodes.E_RA_SERVER_NOT_RUNNING);

		} catch (Exception e) {

			logger.error("{} :: getServiceStatus() :: error", CLASS, e);

			return new APIResponse(
					false,
					ErrorCodes.getErrorMessage(
							ErrorCodes.getErrorCode(e.getMessage())
					),
					null
			);
		}
	}


	@PostMapping(value = "post/service/certificate/request", consumes = "application/json", produces = "application/json")
	public String certificateRequest(@RequestBody RARequestDTO requestBody) throws UnknownHostException {
		try {
			logger.info("{} :: requestCertificate() :: request :: {}", CLASS, requestBody);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus())
				throw new Exception(apiResponse.getMessage());
			RAServiceAsserts.notNullorEmpty(requestBody, ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getSubscriberUniqueId(), ErrorCodes.E_INVALID_REQUEST);
			String response = raServiceIfaceImpl.issueCertificate(requestBody);
			logger.info("{} :: requestCertificate() :: response :: {}", CLASS, response);
			return new APIResponse(true,messageSource.getMessage("api.response.issuing.signing.and.authentication.certificates.are.process.successfully",
							null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			sentryClientExceptions.captureTags(requestBody.getSubscriberUniqueId(),null,"generateSignature","PKIRESTController");
			sentryClientExceptions.captureExceptions(e);
			logger.error("{} :: requestCertificate() :: error :: ", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}


	@PostMapping(value = "post/service/certificate/request/callback")
	public String requestCertificateCallBack(@RequestBody Map<String, String> callbackResponse) throws UnknownHostException {
		try {
			logger.info("{} :: requestCertificateCallBack() :: response :: {}", CLASS, callbackResponse);
			RAServiceAsserts.notNullorEmpty(callbackResponse, ErrorCodes.E_INVALID_REQUEST);
			String response = raServiceIfaceImpl.issueCertificateCallBack(callbackResponse);
			logger.info("{} :: requestCertificateCallBack() :: response :: {}", CLASS, response);
			return new APIResponse(true, messageSource
					.getMessage("api.response.issue.certificate.callback.process.successfully", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			sentryClientExceptions.captureTags(callbackResponse.get(Constant.CALLBACK_SUID),null,"requestCertificateCallBack","PKIRestController");
			sentryClientExceptions.captureExceptions(e);
			logger.error("{} :: requestCertificateCallBack() :: error :: ", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	/**
	 * Revoke certificate.
	 *
	 * @param requestBody the request body
	 * @return the string
	 */
	@PostMapping(value = "post/service/certificate/revoke", produces = "application/json")
	public String revokeCertificate(@RequestBody RARequestDTO requestBody,HttpServletRequest httpServletRequest) {
		try {
			logger.info("{} :: revokeCertificate() :: request.", CLASS);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus())
				throw new Exception(apiResponse.getMessage());
			RAServiceAsserts.notNullorEmpty(requestBody, ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getSubscriberUniqueId(), ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getReasonId(), ErrorCodes.E_INVALID_REQUEST);
			String response = raServiceIfaceImpl.revokeCertificate(requestBody);
			logger.info("{} :: revokeCertificate :: response :: {}",
					CLASS, response);
			return new APIResponse(true, messageSource.getMessage("api.response.certificates.are.revoked.successfully",
					null, Locale.ENGLISH), "\"" + response + "\"").toString();
		} catch (Exception e) {
			logger.error("{} :: revokeCertificate() :: error", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	/**
	 * Sets the pin.
	 *
	 * @param setPinModel the set pin model
	 * @return the string
	 */
	@PostMapping(value = "post/service/certificate/set-pin", consumes = "application/json")
	public String setPin(@RequestBody SetPinModel setPinModel,HttpServletRequest httpServletRequest) {
		try {

			logger.info("{} :: setPin() :: request :: {}",
					CLASS, setPinModel);
			RAServiceAsserts.notNullorEmpty(setPinModel, ErrorCodes.E_INVALID_REQUEST);
			String response = rapkiServiceIfaceImpl.setPin(setPinModel);
			logger.info("{} :: setPin() :: response :: {}",
					CLASS, response);
			return new APIResponse(true,
					messageSource.getMessage("api.response.pin.operation.successful", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			logger.error("{} :: SetPin() :: error", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	/**
	 * Generate signature.
	 *
	 * @param generateSignatureRequest the generate signature request
	 * @return the string
	 */
	//@Async
//	@PostMapping(value = "post/service/certificate/generate-signature", consumes = "application/json")
//	public CompletableFuture<String> generateSignature(@RequestBody GenerateSignature generateSignatureRequest) {
//		try {
//			logger.info(CLASS + " :: generateSignature :: request. :: " + generateSignatureRequest);
//			APIResponse apiResponse = getServiceStatus();
//			if (!apiResponse.getStatus())
//				throw new Exception(apiResponse.getMessage());
//			logger.info(CLASS + " :: generateSignature() :: generateSignatureRequest :: " + generateSignatureRequest);
//			RAServiceAsserts.notNullorEmpty(generateSignatureRequest, ErrorCodes.E_INVALID_REQUEST);
//			//String response = rapkiServiceIfaceImpl.generateSignature(generateSignatureRequest);
//			//logger.info(CLASS + " :: generateSignature() :: response :: " + response);
//			return rapkiServiceIfaceImpl.generateSignature(generateSignatureRequest)
//					.thenApply(response -> new APIResponse(true,
//					messageSource.getMessage("api.response.generate.signature.response", null, Locale.ENGLISH),
//					"\"" + response + "\"").toString());
//		} catch (Exception e) {
//			logger.info(CLASS + " :: generateSignature() :: error :: " + e.getMessage());
//			return CompletableFuture.completedFuture(new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
//					.toString());
//		}
//	}
	/**
	 * Sets Auth and Sign the pin.
	 *
	 * @param setPinModel the set pin model
	 * @return the string
	 */
	@PostMapping(value = "post/certificate/set-pins", consumes = "application/json")
	public String setPins(@RequestBody SetPinModelDto setPinModel, HttpServletRequest httpServletRequest) {
		try {
			logger.info("{} :: setAuthSignPin() :: request :: {}",
					CLASS, setPinModel);
			RAServiceAsserts.notNullorEmpty(setPinModel, ErrorCodes.E_INVALID_REQUEST);
			String response = rapkiServiceIfaceImpl.setPins(setPinModel);
			return new APIResponse(true,
					messageSource.getMessage("api.response.pin.operation.successful", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			logger.error("{} :: setAuthSignPin() :: error", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}
	@PostMapping(value = "post/service/certificate/generate-signature", consumes = "application/json")
	public String generateSignature(@RequestBody GenerateSignature generateSignatureRequest) throws UnknownHostException {
		try {
			logger.info("{} :: generateSignature :: request :: {}",
					CLASS, generateSignatureRequest);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus())
				throw new Exception(apiResponse.getMessage());
			logger.info("{} :: generateSignature() :: generateSignatureRequest :: {}",
					CLASS, generateSignatureRequest);
			RAServiceAsserts.notNullorEmpty(generateSignatureRequest, ErrorCodes.E_INVALID_REQUEST);
			String response = rapkiServiceIfaceImpl.generateSignature(generateSignatureRequest);
			return new APIResponse(true, "Generate Signature Response.", "\"" + response + "\"").toString();
		} catch (Exception e) {
			sentryClientExceptions.captureTags(generateSignatureRequest.getSubscriberUniqueId(),null,"generateSignature","PKIRESTController");
			sentryClientExceptions.captureExceptions(e);
			logger.error("{} :: generateSignature() :: error", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();

		}
	}

	@PostMapping(value = { "post/service/certificate/generate-signature-org" }, consumes = { "application/json" })
	public String generateSignatureOrganization(@RequestBody GenerateSignature generateSignatureRequest) throws UnknownHostException {
		try {
			logger.info("{} :: generateSignatureOrganization :: request :: {}",
					CLASS, generateSignatureRequest);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus()) {
				throw new Exception(apiResponse.getMessage());
			}
			RAServiceAsserts.notNullorEmpty(generateSignatureRequest, ErrorCodes.E_INVALID_REQUEST);
			String response = rapkiServiceIfaceImpl.generateSignatureOrganization(generateSignatureRequest);
			logger.info("generateSignatureOrganization() :: response :: {}", response);
			return new APIResponse(true,
					messageSource.getMessage("api.response.generate.signature.response", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			sentryClientExceptions.captureTags(generateSignatureRequest.getSubscriberUniqueId(),null,"generateSignature","PKIRESTController");
			sentryClientExceptions.captureExceptions(e);
			logger.error("generateSignatureOrganization() :: error :: {}", e.getMessage(), e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	/**
	 * Authenticate PKI.
	 *
	 * @param authenticatePKIModel the authenticate PKI model
	 * @return the string
	 */
	@PostMapping(value = "post/service/certificate/authenticate-pki", consumes = "application/json")
	public String authenticatePKI(@RequestBody AuthenticatePKIModel authenticatePKIModel) {
		try {
			logger.info("{} :: authenticatePKI() :: authenticatePKIRequest :: {}",
					CLASS, authenticatePKIModel);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus())
				throw new Exception(apiResponse.getMessage());
			RAServiceAsserts.notNullorEmpty(authenticatePKIModel, ErrorCodes.E_INVALID_REQUEST);
			String response = rapkiServiceIfaceImpl.authenticatePKI(authenticatePKIModel);

			logger.info("{} :: authenticatePKI() :: response :: {}", CLASS, response);
			return new APIResponse(true,
					messageSource.getMessage("api.response.authenticate.pki.response", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			logger.error("{} :: authenticatePKI() :: error", CLASS, e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	/**
	 * Verify certficate pin.
	 *
	 * @param requestBody the request body
	 * @return the string
	 */
	@PostMapping(value = "post/service/certificate/verify-certificate-pin", produces = "application/json")
	public String verifyCertficatePin(@RequestBody VerifyCertificatesPins requestBody,HttpServletRequest httpServletRequest) {
		try {
			logger.info("{} :: verifyCertificatePin() :: request :: {}", CLASS, requestBody);
			APIResponse apiResponse = getServiceStatus();
			if (!apiResponse.getStatus())
				throw new Exception(apiResponse.getMessage());
			RAServiceAsserts.notNullorEmpty(requestBody, ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getSubscriberUId(), ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getSigningPin(), ErrorCodes.E_INVALID_REQUEST);
			RAServiceAsserts.notNullorEmpty(requestBody.getAuthPin(), ErrorCodes.E_INVALID_REQUEST);
			String response = raServiceIfaceImpl.verifyCertficatesPins(requestBody);
			logger.info("{} :: verifyCertificatePin() :: response :: {}", CLASS, response);
			return new APIResponse(true,
					messageSource.getMessage("api.response.pin.verification.successful", null, Locale.ENGLISH),
					"\"" + response + "\"").toString();
		} catch (Exception e) {
			logger.error("{} :: verifyCertficatePin() :: error :: {}", CLASS, e.getMessage(), e);
			return new APIResponse(false, ErrorCodes.getErrorMessage(ErrorCodes.getErrorCode(e.getMessage())), null)
					.toString();
		}
	}

	@GetMapping(value = "get/servcie/certificate/status")
	public String checkCertificateStatus() throws RAServiceException, Exception {

		String status = raServiceIfaceImpl.checkCertificateStatus();
		return status;
	}
}
