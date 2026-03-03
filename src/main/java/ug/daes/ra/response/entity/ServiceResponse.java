/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.response.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ServiceResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponse {

	/** The status. */
	private String status;

	/** The signature. */
	private String certificate;

	/** The wrapped key. */
	private String wrappedKey;

	/** The certificate serial number. */
	private String certificate_serial_number;

	/** The error code. */
	private String error_code;

	/** The error message. */
	private String error_message;

	/** The certificate status. */
	private String certificate_status;

	/** The revocation reason. */
	private String revocation_reason;

	/** The issue date. */
	private String issueDate;

	/** The expiry date. */
	private String expiryDate;

	/** The signature. */
	private String signature;

	private boolean faceMatchResult;

	private double faceMatchScore;

	private String faceData;

	public double getFaceMatchScore() {
		return faceMatchScore;
	}

	public void setFaceMatchScore(double faceMatchScore) {
		this.faceMatchScore = faceMatchScore;
	}

	public boolean isFaceMatchResult() {
		return faceMatchResult;
	}

	public void setFaceMatchResult(boolean faceMatchResult) {
		this.faceMatchResult = faceMatchResult;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the certificate.
	 *
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * Sets the certificate.
	 *
	 * @param certificate
	 *            the new certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * Gets the certificate serial number.
	 *
	 * @return the certificate serial number
	 */
	public String getCertificate_serial_number() {
		return certificate_serial_number;
	}

	/**
	 * Sets the certificate serial number.
	 *
	 * @param certificate_serial_number
	 *            the new certificate serial number
	 */
	public void setCertificate_serial_number(String certificate_serial_number) {
		this.certificate_serial_number = certificate_serial_number;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getError_code() {
		return error_code;
	}

	/**
	 * Sets the error code.
	 *
	 * @param error_code
	 *            the new error code
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getError_message() {
		return error_message;
	}

	/**
	 * Sets the error message.
	 *
	 * @param error_message
	 *            the new error message
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	/**
	 * Gets the certificate status.
	 *
	 * @return the certificate status
	 */
	public String getCertificate_status() {
		return certificate_status;
	}

	/**
	 * Sets the certificate status.
	 *
	 * @param certificate_status
	 *            the new certificate status
	 */
	public void setCertificate_status(String certificate_status) {
		this.certificate_status = certificate_status;
	}

	/**
	 * Gets the revocation reason.
	 *
	 * @return the revocation reason
	 */
	public String getRevocation_reason() {
		return revocation_reason;
	}

	/**
	 * Sets the revocation reason.
	 *
	 * @param revocation_reason
	 *            the new revocation reason
	 */
	public void setRevocation_reason(String revocation_reason) {
		this.revocation_reason = revocation_reason;
	}

	/**
	 * Gets the issue date.
	 *
	 * @return the issue date
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * Sets the issue date.
	 *
	 * @param issueDate
	 *            the new issue date
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate
	 *            the new expiry date
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Gets the signature.
	 *
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Sets the signature.
	 *
	 * @param signature
	 *            the new signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * Gets the wrapped key.
	 *
	 * @return the wrapped key
	 */
	public String getWrappedKey() {
		return wrappedKey;
	}

	/**
	 * Sets the wrapped key.
	 *
	 * @param wrappedKey
	 *            the new wrapped key
	 */
	public void setWrappedKey(String wrappedKey) {
		this.wrappedKey = wrappedKey;
	}

	public String getFaceData() {
		return faceData;
	}

	public void setFaceData(String faceData) {
		this.faceData = faceData;
	}
//	@Override
//	public String toString() {
//		return "ServiceResponse [status=" + status + ", certificate=" + certificate + ", wrappedKey=" + wrappedKey
//				+ ", certificate_serial_number=" + certificate_serial_number + ", error_code=" + error_code
//				+ ", error_message=" + error_message + ", certificate_status=" + certificate_status
//				+ ", revocation_reason=" + revocation_reason + ", issueDate=" + issueDate + ", expiryDate=" + expiryDate
//				+ ", signature=" + signature + "]";
//	}


	@Override
	public String toString() {
		return "ServiceResponse{" +
				"status='" + status + '\'' +
				", certificate='" + certificate + '\'' +
				", wrappedKey='" + wrappedKey + '\'' +
				", certificate_serial_number='" + certificate_serial_number + '\'' +
				", error_code='" + error_code + '\'' +
				", error_message='" + error_message + '\'' +
				", certificate_status='" + certificate_status + '\'' +
				", revocation_reason='" + revocation_reason + '\'' +
				", issueDate='" + issueDate + '\'' +
				", expiryDate='" + expiryDate + '\'' +
				", signature='" + signature + '\'' +
				", faceMatchResult=" + faceMatchResult +
				", faceMatchScore=" + faceMatchScore +
				", faceData='" + faceData + '\'' +
				'}';
	}
}
