/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;

/**
 * The Class SubscriberStatus.
 */
@Entity
@Table(name = "subscriber_status")
@NamedQuery(name = "SubscriberStatus.findAll", query = "SELECT s FROM SubscriberStatus s")
public class SubscriberStatusModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	/** The otp verified status. */
	@Column(name = "otp_verified_status")
	private String otpVerifiedStatus;

	/** The subscriber status. */
	@Column(name = "subscriber_status")
	private String subscriberStatus;

	/** The subscriber status description. */
	@Column(name = "subscriber_status_description")
	private String subscriberStatusDescription;

	/** The subscriber status id. */
	@Column(name = "subscriber_status_id")
	private int subscriberStatusId;

	/** The subscriber uid. */
	@Id
	@Column(name = "subscriber_uid")
	private String subscriberUid;

	/** The updated date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	/**
	 * Instantiates a new subscriber status.
	 */
	public SubscriberStatusModel() {
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the otp verified status.
	 *
	 * @return the otp verified status
	 */
	public String getOtpVerifiedStatus() {
		return this.otpVerifiedStatus;
	}

	/**
	 * Sets the otp verified status.
	 *
	 * @param otpVerifiedStatus
	 *            the new otp verified status
	 */
	public void setOtpVerifiedStatus(String otpVerifiedStatus) {
		this.otpVerifiedStatus = otpVerifiedStatus;
	}

	/**
	 * Gets the subscriber status.
	 *
	 * @return the subscriber status
	 */
	public String getSubscriberStatus() {
		return this.subscriberStatus;
	}

	/**
	 * Sets the subscriber status.
	 *
	 * @param subscriberStatus
	 *            the new subscriber status
	 */
	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	/**
	 * Gets the subscriber status description.
	 *
	 * @return the subscriber status description
	 */
	public String getSubscriberStatusDescription() {
		return this.subscriberStatusDescription;
	}

	/**
	 * Sets the subscriber status description.
	 *
	 * @param subscriberStatusDescription
	 *            the new subscriber status description
	 */
	public void setSubscriberStatusDescription(String subscriberStatusDescription) {
		this.subscriberStatusDescription = subscriberStatusDescription;
	}

	/**
	 * Gets the subscriber status id.
	 *
	 * @return the subscriber status id
	 */
	public int getSubscriberStatusId() {
		return this.subscriberStatusId;
	}

	/**
	 * Sets the subscriber status id.
	 *
	 * @param subscriberStatusId
	 *            the new subscriber status id
	 */
	public void setSubscriberStatusId(int subscriberStatusId) {
		this.subscriberStatusId = subscriberStatusId;
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

	/**
	 * Gets the updated date.
	 *
	 * @return the updated date
	 */
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	/**
	 * Sets the updated date.
	 *
	 * @param updatedDate
	 *            the new updated date
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}