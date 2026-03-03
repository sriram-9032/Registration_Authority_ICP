/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class SubscriberWrappedKey.
 */
@Entity
@Table(name = "Subscriber_wrapped_key")
public class SubscriberWrappedKey implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The certificate serial number. */
	@Id
	@Column(name = "certificate_serial_number", nullable = false, unique = true)
	private String certificateSerialNumber;

	/** The wrapped key. */
	@Column(name = "wrapped_key", nullable = false, unique = true)
	private String wrappedKey;

	/**
	 * Gets the certificate serial number.
	 *
	 * @return the certificate serial number
	 */
	public String getCertificateSerialNumber() {
		return certificateSerialNumber;
	}

	/**
	 * Sets the certificate serial number.
	 *
	 * @param certificateSerialNumber
	 *            the new certificate serial number
	 */
	public void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
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

}
