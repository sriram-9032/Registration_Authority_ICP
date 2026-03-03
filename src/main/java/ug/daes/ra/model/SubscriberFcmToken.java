/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class SubscriberFcmToken.
 */
@Entity
@Table(name = "subscriber_fcm_token")
public class SubscriberFcmToken implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created date. */
	@Column(name = "created_date")
	private String createdDate;

	/** The fcm token. */
	@Column(name = "fcm_token")
	private String fcmToken;

	/** The subscriber fcm token id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriber_fcm_token_id")
	private int subscriberFcmTokenId;

	/** The subscriber uid. */
	@Column(name = "subscriber_uid")
	private String subscriberUid;

	/**
	 * Instantiates a new subscriber fcm token.
	 */
	public SubscriberFcmToken() {
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the fcm token.
	 *
	 * @return the fcm token
	 */
	public String getFcmToken() {
		return this.fcmToken;
	}

	/**
	 * Sets the fcm token.
	 *
	 * @param fcmToken
	 *            the new fcm token
	 */
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	/**
	 * Gets the subscriber fcm token id.
	 *
	 * @return the subscriber fcm token id
	 */
	public int getSubscriberFcmTokenId() {
		return this.subscriberFcmTokenId;
	}

	/**
	 * Sets the subscriber fcm token id.
	 *
	 * @param subscriberFcmTokenId
	 *            the new subscriber fcm token id
	 */
	public void setSubscriberFcmTokenId(int subscriberFcmTokenId) {
		this.subscriberFcmTokenId = subscriberFcmTokenId;
	}

	/**
	 * Gets the subscriber uid.
	 *
	 * @return the subscriber uid
	 */
	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	/**
	 * Sets the subscriber uid.
	 *
	 * @param subscriberUid
	 *            the new subscriber uid
	 */
	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

}