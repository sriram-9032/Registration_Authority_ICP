/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

import java.io.Serializable;

/**
 * The Class RevokeCertificateRequest.
 */
public class RevokeCertificateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The reason id. */
	private String reasonId;

	/** The serial number. */
	private String serialNumber;

	/**
	 * Gets the reason id.
	 *
	 * @return the reason id
	 */
	public String getReasonId() {
		return reasonId;
	}

	/**
	 * Sets the reason id.
	 *
	 * @param reasonId
	 *            the new reason id
	 */
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"serial_number\"" + ":" + "\"" + serialNumber + "\"," + "\"reason\"" + ":" + "\"" + reasonId
				+ "\"" + "}";
	}

}
