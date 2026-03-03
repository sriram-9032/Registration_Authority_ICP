
package ug.daes.ra.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ug.daes.ra.response.entity.ServiceResponse;

/**
 * The Class ErrorCodes.
 */
public class ErrorCodes {

	/** The response.
	 * Note: Kept as non-final because it has a setter, but renamed to follow static naming if needed.
	 */
	private static ServiceResponse response = null;

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public static ServiceResponse getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	public static void setResponse(ServiceResponse response) {
		ErrorCodes.response = response;
	}

	private static final Map<String, String> MESSAGE_MAPPING;
	private static final Map<String, String> CODE_MAPPING;

	public static final String E_OB_01 = "104561";
	public static final String E_OB_02 = "104562";
	public static final String E_OB_03 = "104563";
	public static final String E_OB_04 = "104564";
	public static final String E_RA_11 = "104565";
	public static final String E_RA_12 = "104566";
	public static final String E_RA_13 = "104567";
	public static final String E_RA_14 = "104568";
	public static final String E_RA_15 = "104569";
	public static final String E_RA_16 = "104570";
	public static final String E_RA_17 = "104571";
	public static final String E_RA_18 = "104572";
	public static final String E_RA_19 = "104573";
	public static final String E_RA_20 = "104574";
	public static final String E_RA_21 = "104575";
	public static final String E_RA_22 = "104576";
	public static final String E_RA_23 = "104577";
	public static final String E_RA_24 = "104578";
	public static final String E_RA_25 = "104579";
	public static final String E_RA_26 = "104580";
	public static final String E_RA_27 = "104581";
	public static final String E_RA_28 = "104582";
	public static final String E_RA_29 = "104583";
	public static final String E_RA_30 = "104584";
	public static final String E_RA_31 = "104585";
	public static final String E_RA_32 = "104586";
	public static final String E_RA_33 = "104587";
	public static final String E_RA_34 = "104588";
	public static final String E_RA_35 = "104589";
	public static final String E_RA_36 = "104590";
	public static final String E_RA_37 = "104591";
	public static final String E_RA_38 = "104592";
	public static final String E_RA_39 = "104593";
	public static final String E_RA_100 = "104594";
	public static final String E_RA_101 = "104595";
	public static final String E_RA_102 = "104596";
	public static final String E_RA_103 = "104597";
	public static final String E_RA_200 = "104598";
	public static final String E_RA_500 = "104599";
	public static final String E_RA_501 = "104600";
	public static final String E_SUBSCRIBER_DATA_NOT_FOUND = "Subscriber data not found";
	public static final String E_ORGANIZATION_DATA_NOT_FOUND = "Organization data not found";
	public static final String E_SUBSCRIBER_STATUS_DATA_NOT_FOUND = "Subscriber status data not found";
	public static final String E_SUBSCRIBER_DEVICE_DATA_NOT_FOUND = "Subscriber device data not found";
	public static final String E_SUBSCRIBER_NOT_ONBOARDED = "Subscriber not onboarded";
	public static final String E_SUBSCRIBER_RA_DATA_NOT_FOUND = "Subscriber RA data not found";
	public static final String E_SUBSCRIBER_CERTIFICATES_ARE_ACTIVE = "Subscriber certificates are active";
	public static final String E_SUBSCRIBER_CERTIFICATES_ARE_REVOKED = "Subscriber certificates are revoke";
	public static final String E_SUBSCRIBER_CERTIFICATES_ARE_EXPIRED = "Subscriber certificates are expired";
	public static final String E_SUBSCRIBER_ISSUE_SIGNING_CERTIFICATE_FAILED = "Issuing signing certificate failed";
	public static final String E_SUBSCRIBER_ISSUE_AUTHENTICATION_CERTIFICATE_FAILED = "Issuing authentication certificate failed";
	public static final String E_TRANSACTION_TYPE_NOT_FOUND = "Transaction type not found";
	public static final String E_REQUEST_DATA_IS_NOT_VALID = "Request data is not valid";
	public static final String E_CERTIFICATES_NOT_ISSUED = "Certificates are not issued";
	public static final String E_RA_SERVER_NOT_RUNNING = "RA server not running";
	public static final String E_TRANSACTION_HANDLER_NOT_RUNNING = "Transaction handler not running";
	public static final String E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND = "Subscriber complete details not found";
	public static final String E_ACTIVE_CERTIFICATE_NOT_FOUND = "Active certificate not found";
	public static final String E_PIN_MATCHED_WITH_OLD_PIN = "Pin matched with old pin";
	public static final String E_CERTIFICATE_TYPE_NOT_FOUND = "Certificate type not found";
	public static final String E_LOG_INTEGRITY_FAILED = "Log integrity failed";
	public static final String E_RA_POST_REQUEST_FAILED = "RA post request failed";
	public static final String E_SOMETHING_WENT_WRONG = "Something went wrong";
	public static final String E_NATIVE_REQUEST_FAILED = "Native request failed";
	public static final String E_INVALID_REQUEST = "Invalid request";
	public static final String E_SIGNING_CERTIFICATE_PIN_NOT_SET = "Signing certificate pin not set";
	public static final String E_AUTHENTICATION_CERTIFICATE_PIN_NOT_SET = "Authenticate certificate pin not set";
	public static final String E_REVOKE_REASON_NOT_FOUND = "Revoke reason not found";
	public static final String E_CERTIFICATE_REVOCATION_FAILED = "Certificate revocation failed";
	public static final String E_NIN_NOT_FOUND = "NIN not found";
	public static final String E_PASSPORT_NOT_FOUND = "Passport not found";
	public static final String E_EMAIL_NOT_FOUND = "Email not found";
	public static final String E_MOBILE_NUMBER_NOT_FOUND = "Mobile Number not found";
	public static final String E_SUBSCRIBER_NOT_ACTIVE = "Subscriber not active";
	public static final String E_PIN_NOT_MATCHED_WITH_OLD_PIN = "Pin not matched with old pin";
	public static final String E_SIGNING_PIN_NOT_MATCHED = "Signing pin not matched";
	public static final String E_AUTH_PIN_NOT_MATCHED = "Current authentication pin not matched";
	public static final String E_NEW_SIGNING_PIN_MATCHED_WITH_OLD_SIGNING_PIN = "New signing pin matched with old signing pin";
	public static final String E_NEW_SIGNING_PIN_MATCHED_WITH_CURRENT_AUTHENTICATION_PIN = "New signing pin matched with current authentication pin";
	public static final String E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_OLD_AUTHENTICATION_PIN = "New authentication pin matched with old authentication pin";
	public static final String E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_CURRENT_SIGNING_PIN = "New authentication pin matched with current signing pin";

	static {
		Map<String, String> mMapping = new HashMap<>();
		mMapping.put(E_SUBSCRIBER_DATA_NOT_FOUND, E_OB_01);
		mMapping.put(E_SUBSCRIBER_STATUS_DATA_NOT_FOUND, E_OB_02);
		mMapping.put(E_SUBSCRIBER_NOT_ONBOARDED, E_OB_03);
		mMapping.put(E_SUBSCRIBER_DEVICE_DATA_NOT_FOUND, E_OB_04);

		mMapping.put(E_SUBSCRIBER_RA_DATA_NOT_FOUND, E_RA_11);
		mMapping.put(E_SUBSCRIBER_CERTIFICATES_ARE_ACTIVE, E_RA_12);
		mMapping.put(E_SUBSCRIBER_CERTIFICATES_ARE_REVOKED, E_RA_13);
		mMapping.put(E_SUBSCRIBER_CERTIFICATES_ARE_EXPIRED, E_RA_14);
		mMapping.put(E_SUBSCRIBER_ISSUE_SIGNING_CERTIFICATE_FAILED, E_RA_15);
		mMapping.put(E_SUBSCRIBER_ISSUE_AUTHENTICATION_CERTIFICATE_FAILED, E_RA_16);
		mMapping.put(E_TRANSACTION_TYPE_NOT_FOUND, E_RA_17);
		mMapping.put(E_REQUEST_DATA_IS_NOT_VALID, E_RA_18);
		mMapping.put(E_CERTIFICATES_NOT_ISSUED, E_RA_19);
		mMapping.put(E_ACTIVE_CERTIFICATE_NOT_FOUND, E_RA_20);
		mMapping.put(E_PIN_MATCHED_WITH_OLD_PIN, E_RA_21);
		mMapping.put(E_CERTIFICATE_TYPE_NOT_FOUND, E_RA_22);
		mMapping.put(E_LOG_INTEGRITY_FAILED, E_RA_23);
		mMapping.put(E_SIGNING_CERTIFICATE_PIN_NOT_SET, E_RA_24);
		mMapping.put(E_REVOKE_REASON_NOT_FOUND, E_RA_25);
		mMapping.put(E_CERTIFICATE_REVOCATION_FAILED, E_RA_26);
		mMapping.put(E_NIN_NOT_FOUND, E_RA_27);
		mMapping.put(E_PASSPORT_NOT_FOUND, E_RA_28);
		mMapping.put(E_EMAIL_NOT_FOUND, E_RA_29);
		mMapping.put(E_MOBILE_NUMBER_NOT_FOUND, E_RA_30);
		mMapping.put(E_SUBSCRIBER_NOT_ACTIVE, E_RA_31);
		mMapping.put(E_AUTHENTICATION_CERTIFICATE_PIN_NOT_SET, E_RA_32);
		mMapping.put(E_PIN_NOT_MATCHED_WITH_OLD_PIN, E_RA_33);
		mMapping.put(E_SIGNING_PIN_NOT_MATCHED, E_RA_34);
		mMapping.put(E_AUTH_PIN_NOT_MATCHED, E_RA_35);
		mMapping.put(E_NEW_SIGNING_PIN_MATCHED_WITH_OLD_SIGNING_PIN, E_RA_36);
		mMapping.put(E_NEW_SIGNING_PIN_MATCHED_WITH_CURRENT_AUTHENTICATION_PIN, E_RA_37);
		mMapping.put(E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_OLD_AUTHENTICATION_PIN, E_RA_38);
		mMapping.put(E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_CURRENT_SIGNING_PIN, E_RA_39);
		mMapping.put(E_RA_SERVER_NOT_RUNNING, E_RA_100);
		mMapping.put(E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND, E_RA_101);
		mMapping.put(E_INVALID_REQUEST, E_RA_102);
		mMapping.put(E_TRANSACTION_HANDLER_NOT_RUNNING, E_RA_103);
		mMapping.put(E_RA_POST_REQUEST_FAILED, E_RA_200);
		mMapping.put(E_NATIVE_REQUEST_FAILED, E_RA_500);
		mMapping.put(E_SOMETHING_WENT_WRONG, E_RA_501);
		MESSAGE_MAPPING = Collections.unmodifiableMap(mMapping);

		Map<String, String> cMapping = new HashMap<>();
		cMapping.put(E_OB_01, E_SUBSCRIBER_DATA_NOT_FOUND);
		cMapping.put(E_OB_02, E_SUBSCRIBER_STATUS_DATA_NOT_FOUND);
		cMapping.put(E_OB_03, E_SUBSCRIBER_NOT_ONBOARDED);
		cMapping.put(E_OB_04, E_SUBSCRIBER_DEVICE_DATA_NOT_FOUND);

		cMapping.put(E_RA_11, E_SUBSCRIBER_RA_DATA_NOT_FOUND);
		cMapping.put(E_RA_12, E_SUBSCRIBER_CERTIFICATES_ARE_ACTIVE);
		cMapping.put(E_RA_13, E_SUBSCRIBER_CERTIFICATES_ARE_REVOKED);
		cMapping.put(E_RA_14, E_SUBSCRIBER_CERTIFICATES_ARE_EXPIRED);
		cMapping.put(E_RA_15, E_SUBSCRIBER_ISSUE_SIGNING_CERTIFICATE_FAILED);
		cMapping.put(E_RA_16, E_SUBSCRIBER_ISSUE_AUTHENTICATION_CERTIFICATE_FAILED);
		cMapping.put(E_RA_17, E_TRANSACTION_TYPE_NOT_FOUND);
		cMapping.put(E_RA_18, E_REQUEST_DATA_IS_NOT_VALID);
		cMapping.put(E_RA_19, E_CERTIFICATES_NOT_ISSUED);
		cMapping.put(E_RA_20, E_ACTIVE_CERTIFICATE_NOT_FOUND);
		cMapping.put(E_RA_21, E_PIN_MATCHED_WITH_OLD_PIN);
		cMapping.put(E_RA_22, E_CERTIFICATE_TYPE_NOT_FOUND);
		cMapping.put(E_RA_23, E_LOG_INTEGRITY_FAILED);
		cMapping.put(E_RA_24, E_SIGNING_CERTIFICATE_PIN_NOT_SET);
		cMapping.put(E_RA_25, E_REVOKE_REASON_NOT_FOUND);
		cMapping.put(E_RA_26, E_CERTIFICATE_REVOCATION_FAILED);
		cMapping.put(E_RA_27, E_NIN_NOT_FOUND);
		cMapping.put(E_RA_28, E_PASSPORT_NOT_FOUND);
		cMapping.put(E_RA_29, E_EMAIL_NOT_FOUND);
		cMapping.put(E_RA_30, E_MOBILE_NUMBER_NOT_FOUND);
		cMapping.put(E_RA_31, E_SUBSCRIBER_NOT_ACTIVE);
		cMapping.put(E_RA_32, E_AUTHENTICATION_CERTIFICATE_PIN_NOT_SET);
		cMapping.put(E_RA_33, E_PIN_NOT_MATCHED_WITH_OLD_PIN);
		cMapping.put(E_RA_34, E_SIGNING_PIN_NOT_MATCHED);
		cMapping.put(E_RA_35, E_AUTH_PIN_NOT_MATCHED);
		cMapping.put(E_RA_36, E_NEW_SIGNING_PIN_MATCHED_WITH_OLD_SIGNING_PIN);
		cMapping.put(E_RA_37, E_NEW_SIGNING_PIN_MATCHED_WITH_CURRENT_AUTHENTICATION_PIN);
		cMapping.put(E_RA_38, E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_OLD_AUTHENTICATION_PIN);
		cMapping.put(E_RA_39, E_NEW_AUTHENTICATION_PIN_MATCHED_WITH_CURRENT_SIGNING_PIN);
		cMapping.put(E_RA_100, E_RA_SERVER_NOT_RUNNING);
		cMapping.put(E_RA_101, E_RA_SUBSCRIBER_COMPLETE_DETAILS_NOT_FOUND);
		cMapping.put(E_RA_102, E_INVALID_REQUEST);
		cMapping.put(E_RA_103, E_TRANSACTION_HANDLER_NOT_RUNNING);

		cMapping.put(E_RA_200, E_RA_POST_REQUEST_FAILED);
		cMapping.put(E_RA_500, E_NATIVE_REQUEST_FAILED);
		cMapping.put(E_RA_501, E_SOMETHING_WENT_WRONG);
		CODE_MAPPING = Collections.unmodifiableMap(cMapping);
	}

	/**
	 * Gets the error code.
	 *
	 * @param message
	 *            the message
	 * @return the error code
	 */
	public static String getErrorCode(String message) {
		String errorCode = MESSAGE_MAPPING.get(message);
		if (errorCode != null)
			return errorCode;
		else
			return response != null ? response.getError_code() : null;
	}

	/**
	 * Gets the error message.
	 *
	 * @param errorCode
	 *            the error code
	 * @return the error message
	 */
	public static String getErrorMessage(String errorCode) {
		String errorMessage = CODE_MAPPING.get(errorCode);
		if (errorMessage != null)
			return errorMessage;
		else
			return response != null ? response.getError_message() : null;
	}
}