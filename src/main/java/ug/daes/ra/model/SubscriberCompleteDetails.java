/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class SubscriberCompleteDetails.
 */
@Entity
@Table(name = "subscriber_complete_details")
public class SubscriberCompleteDetails {

	/** The subscriber uid. */
	@Id
	@Column(name = "subscriber_uid")
	private String subscriberUid;

	/** The full name. */
	@Column(name = "full_name")
	private String fullName;

	/** The subscriber type. */
	@Column(name = "subscriber_type")
	private String subscriberType;

	/** The date of birth. */
	@Column(name = "date_of_birth")
	private String dateOfBirth;

	/** The id doc type. */
	@Column(name = "id_doc_type")
	private String idDocType;

	/** The id doc number. */
	@Column(name = "id_doc_number")
	private String idDocNumber;

	/** The last on boarding date. */
	@Column(name = "created_date")
	private String lastOnBoardingDate;

	/** The certificate status. */
	@Column(name = "certificate_status")
	private String certificateStatus;

	/** The subscriber status. */
	@Column(name = "subscriber_status")
	private String subscriberStatus;

	/** The device status. */
	@Column(name = "device_status")
	private String deviceStatus;

	/** The device registration time. */
	@Column(name = "device_registration_time")
	private String deviceRegistrationTime;

	/** The certificate issue date. */
	@Column(name = "certificate_issue_date")
	private String certificateIssueDate;

	/** The certificate expiry date. */
	@Column(name = "cerificate_expiry_date")
	private String certificateExpiryDate;

	/** The pin set date. */
	@Column(name = "sign_pin_set_date")
	private String signPinSetDate;

	/** The pin set date. */
	@Column(name = "auth_pin_set_date")
	private String authPinSetDate;

	/** The photo. */
	@Column(name = "selfie_uri")
	private String photo;

	/** The on boarding time. */
	@Column(name = "on_boarding_time")
	private String onBoardingTime;

	/** The on boarding method. */
	@Column(name = "on_boarding_method")
	private String onBoardingMethod;

	/** The level of assurance. */
	@Column(name = "level_of_assurance")
	private String levelOfAssurance;

	/** The mobile number. */
	@Column(name = "mobile_number")
	private String mobileNumber;

	/** The geo location. */
	@Column(name = "geo_location")
	private String geoLocation;

	/** The geo location. */
	@Column(name = "gender")
	private String gender;

	/** The e mail. */
	@Column(name = "email_id")
	private String eMail;

	/** The revocation date. */
	@Column(name = "revocation_date")
	private String revocationDate;

	/** The revocation reason. */
	@Column(name = "revocation_reason")
	private String revocationReason;

	/**
	 * Gets the subscriber uid.
	 *
	 * @return the subscriber uid
	 */
	public String getSubscriberUid() {
		return subscriberUid;
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
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
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
	 * Gets the subscriber type.
	 *
	 * @return the subscriber type
	 */
	public String getSubscriberType() {
		return subscriberType;
	}

	/**
	 * Sets the subscriber type.
	 *
	 * @param subscriberType
	 *            the new subscriber type
	 */
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth
	 *            the new date of birth
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the id doc type.
	 *
	 * @return the id doc type
	 */
	public String getIdDocType() {
		return idDocType;
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
	 * Gets the id doc number.
	 *
	 * @return the id doc number
	 */
	public String getIdDocNumber() {
		return idDocNumber;
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
	 * Gets the last on boarding date.
	 *
	 * @return the last on boarding date
	 */
	public String getLastOnBoardingDate() {
		return lastOnBoardingDate;
	}

	/**
	 * Sets the last on boarding date.
	 *
	 * @param lastOnBoardingDate
	 *            the new last on boarding date
	 */
	public void setLastOnBoardingDate(String lastOnBoardingDate) {
		this.lastOnBoardingDate = lastOnBoardingDate;
	}

	/**
	 * Gets the certificate status.
	 *
	 * @return the certificate status
	 */
	public String getCertificateStatus() {
		return certificateStatus;
	}

	/**
	 * Sets the certificate status.
	 *
	 * @param certificateStatus
	 *            the new certificate status
	 */
	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	/**
	 * Gets the subscriber status.
	 *
	 * @return the subscriber status
	 */
	public String getSubscriberStatus() {
		return subscriberStatus;
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
	 * Gets the device status.
	 *
	 * @return the device status
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * Sets the device status.
	 *
	 * @param deviceStatus
	 *            the new device status
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * Gets the device registration time.
	 *
	 * @return the device registration time
	 */
	public String getDeviceRegistrationTime() {
		return deviceRegistrationTime;
	}

	/**
	 * Sets the device registration time.
	 *
	 * @param deviceRegistrationTime
	 *            the new device registration time
	 */
	public void setDeviceRegistrationTime(String deviceRegistrationTime) {
		this.deviceRegistrationTime = deviceRegistrationTime;
	}

	/**
	 * Gets the certificate issue date.
	 *
	 * @return the certificate issue date
	 */
	public String getCertificateIssueDate() {
		return certificateIssueDate;
	}

	/**
	 * Sets the certificate issue date.
	 *
	 * @param certificateIssueDate
	 *            the new certificate issue date
	 */
	public void setCertificateIssueDate(String certificateIssueDate) {
		this.certificateIssueDate = certificateIssueDate;
	}

	/**
	 * Gets the certificate expiry date.
	 *
	 * @return the certificate expiry date
	 */
	public String getCertificateExpiryDate() {
		return certificateExpiryDate;
	}

	/**
	 * Sets the certificate expiry date.
	 *
	 * @param certificateExpiryDate
	 *            the new certificate expiry date
	 */
	public void setCertificateExpiryDate(String certificateExpiryDate) {
		this.certificateExpiryDate = certificateExpiryDate;
	}

	/**
	 * Gets the sign pin set date.
	 *
	 * @return the sign pin set date
	 */
	public String getSignPinSetDate() {
		return signPinSetDate;
	}

	/**
	 * Sets the sign pin set date.
	 *
	 * @param signPinSetDate
	 *            the new sign pin set date
	 */
	public void setSignPinSetDate(String signPinSetDate) {
		this.signPinSetDate = signPinSetDate;
	}

	/**
	 * Gets the auth pin set date.
	 *
	 * @return the auth pin set date
	 */
	public String getAuthPinSetDate() {
		return authPinSetDate;
	}

	/**
	 * Sets the auth pin set date.
	 *
	 * @param authPinSetDate
	 *            the new auth pin set date
	 */
	public void setAuthPinSetDate(String authPinSetDate) {
		this.authPinSetDate = authPinSetDate;
	}

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo
	 *            the new photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * Gets the on boarding time.
	 *
	 * @return the on boarding time
	 */
	public String getOnBoardingTime() {
		return onBoardingTime;
	}

	/**
	 * Sets the on boarding time.
	 *
	 * @param onBoardingTime
	 *            the new on boarding time
	 */
	public void setOnBoardingTime(String onBoardingTime) {
		this.onBoardingTime = onBoardingTime;
	}

	/**
	 * Gets the on boarding method.
	 *
	 * @return the on boarding method
	 */
	public String getOnBoardingMethod() {
		return onBoardingMethod;
	}

	/**
	 * Sets the on boarding method.
	 *
	 * @param onBoardingMethod
	 *            the new on boarding method
	 */
	public void setOnBoardingMethod(String onBoardingMethod) {
		this.onBoardingMethod = onBoardingMethod;
	}

	/**
	 * Gets the level of assurance.
	 *
	 * @return the level of assurance
	 */
	public String getLevelOfAssurance() {
		return levelOfAssurance;
	}

	/**
	 * Sets the level of assurance.
	 *
	 * @param levelOfAssurance
	 *            the new level of assurance
	 */
	public void setLevelOfAssurance(String levelOfAssurance) {
		this.levelOfAssurance = levelOfAssurance;
	}

	/**
	 * Gets the mobile number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
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
	 * Gets the e mail.
	 *
	 * @return the e mail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * Sets the e mail.
	 *
	 * @param eMail
	 *            the new e mail
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * Gets the revocation date.
	 *
	 * @return the revocation date
	 */
	public String getRevocationDate() {
		return revocationDate;
	}

	/**
	 * Sets the revocation date.
	 *
	 * @param revocationDate
	 *            the new revocation date
	 */
	public void setRevocationDate(String revocationDate) {
		this.revocationDate = revocationDate;
	}

	/**
	 * Gets the revocation reason.
	 *
	 * @return the revocation reason
	 */
	public String getRevocationReason() {
		return revocationReason;
	}

	/**
	 * Sets the revocation reason.
	 *
	 * @param revocationReason
	 *            the new revocation reason
	 */
	public void setRevocationReason(String revocationReason) {
		this.revocationReason = revocationReason;
	}

	/**
	 * Gets the geo location.
	 *
	 * @return the geo location
	 */
	public String getGeoLocation() {
		return geoLocation;
	}

	/**
	 * Sets the geo location.
	 *
	 * @param geoLocation
	 *            the new geo location
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubscriberCompleteDetails [subscriberUid=" + subscriberUid + ", fullName=" + fullName
				+ ", subscriberType=" + subscriberType + ", dateOfBirth=" + dateOfBirth + ", idDocType=" + idDocType
				+ ", idDocNumber=" + idDocNumber + ", lastOnBoardingDate=" + lastOnBoardingDate + ", certificateStatus="
				+ certificateStatus + ", subscriberStatus=" + subscriberStatus + ", deviceStatus=" + deviceStatus
				+ ", deviceRegistrationTime=" + deviceRegistrationTime + ", certificateIssueDate="
				+ certificateIssueDate + ", certificateExpiryDate=" + certificateExpiryDate + ", signPinSetDate="
				+ signPinSetDate + ", authPinSetDate=" + authPinSetDate + ", photo=" + photo + ", onBoardingTime="
				+ onBoardingTime + ", onBoardingMethod=" + onBoardingMethod + ", levelOfAssurance=" + levelOfAssurance
				+ ", mobileNumber=" + mobileNumber + ", geoLocation=" + geoLocation + ", gender=" + gender + ", eMail="
				+ eMail + ", revocationDate=" + revocationDate + ", revocationReason=" + revocationReason + "]";
	}

}
