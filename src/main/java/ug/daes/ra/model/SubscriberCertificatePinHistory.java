/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class SubscriberCertificatePinHistory.
 */
@Entity
@Table(name = "subscriber_certificate_pin_history")
public class SubscriberCertificatePinHistory implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The subscriber certificate pin history id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriber_certificate_pin_history_id")
	private int subscriberCertificatePinHistoryId;

	/** The subscriber unique id. */
	@Column(name = "subscriber_uid", unique = true, nullable = false)
	private String subscriberUniqueId;

	/** The authentication certificate pin list. */
	@Column(name = "auth_pin_list")
	private String authenticationCertificatePinList;

	/** The signing certificate pin list. */
	@Column(name = "sign_pin_list")
	private String signingCertificatePinList;

	/** The pin setting date. */
	@Column(name = "sign_pin_set_date")
	private Date signPinSetDate;

	/** The created date. */
	@Column(name = "auth_pin_set_date")
	private Date authPinSetDate;

	/**
	 * Gets the subscriber certificate pin history id.
	 *
	 * @return the subscriber certificate pin history id
	 */
	public int getSubscriberCertificatePinHistoryId() {
		return subscriberCertificatePinHistoryId;
	}

	/**
	 * Sets the subscriber certificate pin history id.
	 *
	 * @param subscriberCertificatePinHistoryId
	 *            the new subscriber certificate pin history id
	 */
	public void setSubscriberCertificatePinHistoryId(int subscriberCertificatePinHistoryId) {
		this.subscriberCertificatePinHistoryId = subscriberCertificatePinHistoryId;
	}

	/**
	 * Gets the authentication certificate pin list.
	 *
	 * @return the authentication certificate pin list
	 */
	public String getAuthenticationCertificatePinList() {
		return authenticationCertificatePinList;
	}

	/**
	 * Sets the authentication certificate pin list.
	 *
	 * @param authenticationCertificatePinList
	 *            the new authentication certificate pin list
	 */
	public void setAuthenticationCertificatePinList(String authenticationCertificatePinList) {
		this.authenticationCertificatePinList = authenticationCertificatePinList;
	}

	/**
	 * Gets the signing certificate pin list.
	 *
	 * @return the signing certificate pin list
	 */
	public String getSigningCertificatePinList() {
		return signingCertificatePinList;
	}

	/**
	 * Sets the signing certificate pin list.
	 *
	 * @param signingCertificatePinList
	 *            the new signing certificate pin list
	 */
	public void setSigningCertificatePinList(String signingCertificatePinList) {
		this.signingCertificatePinList = signingCertificatePinList;
	}

	/**
	 * Gets the sign pin set date.
	 *
	 * @return the sign pin set date
	 */
	public Date getSignPinSetDate() {
		return signPinSetDate;
	}

	/**
	 * Sets the sign pin set date.
	 *
	 * @param signPinSetDate
	 *            the new sign pin set date
	 */
	public void setSignPinSetDate(Date signPinSetDate) {
		this.signPinSetDate = signPinSetDate;
	}

	/**
	 * Gets the auth pin set date.
	 *
	 * @return the auth pin set date
	 */
	public Date getAuthPinSetDate() {
		return authPinSetDate;
	}

	/**
	 * Sets the auth pin set date.
	 *
	 * @param authPinSetDate
	 *            the new auth pin set date
	 */
	public void setAuthPinSetDate(Date authPinSetDate) {
		this.authPinSetDate = authPinSetDate;
	}

	/**
	 * Gets the subscriber unique id.
	 *
	 * @return the subscriber unique id
	 */
	public String getSubscriberUniqueId() {
		return subscriberUniqueId;
	}

	/**
	 * Sets the subscriber unique id.
	 *
	 * @param subscriberUniqueId
	 *            the new subscriber unique id
	 */
	public void setSubscriberUniqueId(String subscriberUniqueId) {
		this.subscriberUniqueId = subscriberUniqueId;
	}

}
