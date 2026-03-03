/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;

/**
 * The persistent class for the subscribers database table.
 * 
 */
@Entity
@Table(name = "subscribers")
public class Subscriber implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	/** The date of birth. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	/** The email id. */
	@Column(name = "email_id")
	private String emailId;

	/** The full name. */
	@Column(name = "full_name")
	private String fullName;

	/** The id doc number. */
	@Column(name = "id_doc_number")
	private String idDocNumber;

	/** The id doc type. */
	@Column(name = "id_doc_type")
	private String idDocType;

	/** The mobile number. */
	@Column(name = "mobile_number")
	private String mobileNumber;

	/** The subscriber uid. */
	@Id
	@Column(name = "subscriber_uid")
	private String subscriberUid;

	/** The updated date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name="national_id")
	private String nationalId;

	/**
	 * Instantiates a new subscriber.
	 */
	public Subscriber() {
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
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth
	 *            the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return this.emailId;
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId
	 *            the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName
	 *            the new full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the id doc number.
	 *
	 * @return the id doc number
	 */
	public String getIdDocNumber() {
		return this.idDocNumber;
	}

	/**
	 * Sets the id doc number.
	 *
	 * @param idDocNumber
	 *            the new id doc number
	 */
	public void setIdDocNumber(String idDocNumber) {
		this.idDocNumber = idDocNumber;
	}

	/**
	 * Gets the id doc type.
	 *
	 * @return the id doc type
	 */
	public String getIdDocType() {
		return this.idDocType;
	}

	/**
	 * Sets the id doc type.
	 *
	 * @param idDocType
	 *            the new id doc type
	 */
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	/**
	 * Gets the mobile number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return this.mobileNumber;
	}

	/**
	 * Sets the mobile number.
	 *
	 * @param mobileNumber
	 *            the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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