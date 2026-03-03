/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

import java.io.Serializable;

/**
 * The Class CheckCertificateStatus.
 */
public class CheckCertificateStatus implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The serial number. */
	private String serialNumber;

	/** The certificate. */
	private String certificate;

	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber
	 *            the new serial number
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"serial_number\"" + ":" + "\"" + serialNumber + "\"," + "\"certificate\"" + ":" + "\""
				+ certificate + "\"" + "}";
	}

}
