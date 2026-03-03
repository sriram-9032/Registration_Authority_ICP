/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.response.entity;

/**
 * The Class CertificateData.
 */
public class CertificateData {

	/** The status. */
	private boolean status;

	/** The auth cert status. */
	private String certStatus;

	/** The auth issue date. */
	private String issueDate;

	private String revokeDate;

	/** The auth expiry date. */
	private String expiryDate;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Gets the cert status.
	 *
	 * @return the cert status
	 */
	public String getCertStatus() {
		return certStatus;
	}

	/**
	 * Sets the cert status.
	 *
	 * @param certStatus
	 *            the new cert status
	 */
	public void setCertStatus(String certStatus) {
		this.certStatus = certStatus;
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

	public String getRevokeDate() {
		return revokeDate;
	}

	public void setRevokeDate(String revokeDate) {
		this.revokeDate = revokeDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"status\":" + status + "," + "\"certificateDetails\":" + "{" + "\"certStatus\" :\"" + certStatus
				+ "\"," + "\"issueDate\" : \"" + issueDate + "\"," + "\"revokeDate\" : \"" + revokeDate + "\","
				+ "\"expiryDate\" : \"" + expiryDate + "\"}" + "}";
	}

}
