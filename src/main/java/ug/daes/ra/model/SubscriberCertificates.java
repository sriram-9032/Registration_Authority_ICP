/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class SubscriberCertificateData.
 */
@Entity
@Table(name = "subscriber_certificates")
public class SubscriberCertificates implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The certificate serial number. */
	@Id
	@Column(name = "certificate_serial_number", nullable = false, unique = true)
	private String certificateSerialNumber;

	/** The subscriber unique id. */
	@Column(name = "subscriber_uid", nullable = false)
	private String subscriberUniqueId;

	/** The certificate type. */
	@Column(name = "certificate_type", nullable = false)
	private String certificateType;

	/** The pki key id. */
	@Column(name = "pki_key_id", nullable = false, unique = true)
	private String pkiKeyId;

	/** The certificate data. */
	@Column(name = "certificate_data", nullable = false, unique = true)
	private String certificateData;

	/** The certificate serial number. */

	/** The start date. */
	@Column(name = "certificate_issue_date", nullable = false)
	private Date certificateStartDate;

	/** The end date. */
	@Column(name = "cerificate_expiry_date", nullable = false)
	private Date certificateEndDate;

	/** The certificate status. */
	@Column(name = "certificate_status", nullable = false)
	// @Enumerated(EnumType.STRING)
	private String certificateStatus;

	/** The revocation reason. */
	@Column(name = "remarks")
	// @Enumerated(EnumType.STRING)
	private String revocationReason;

	/** The creation date. */
	@Column(name = "created_date", nullable = false)
	private Date creationDate;

	/** The updated date. */
	@Column(name = "updated_date")
	private Date updatedDate;

	/**
	 * Gets the certificate start date.
	 *
	 * @return the certificate start date
	 */
	public Date getCertificateStartDate() {
		return certificateStartDate;
	}

	/**
	 * Sets the certificate start date.
	 *
	 * @param certificateStartDate
	 *            the new certificate start date
	 */
	public void setCertificateStartDate(Date certificateStartDate) {
		this.certificateStartDate = certificateStartDate;
	}

	/**
	 * Gets the certificate end date.
	 *
	 * @return the certificate end date
	 */
	public Date getCertificateEndDate() {
		return certificateEndDate;
	}

	/**
	 * Sets the certificate end date.
	 *
	 * @param certificateEndDate
	 *            the new certificate end date
	 */
	public void setCertificateEndDate(Date certificateEndDate) {
		this.certificateEndDate = certificateEndDate;
	}

	/**
	 * Gets the certificate type.
	 *
	 * @return the certificate type
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * Sets the certificate type.
	 *
	 * @param certificateType
	 *            the new certificate type
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * Gets the pki key id.
	 *
	 * @return the pki key id
	 */
	public String getPkiKeyId() {
		return pkiKeyId;
	}

	/**
	 * Sets the pki key id.
	 *
	 * @param pkiKeyId
	 *            the new pki key id
	 */
	public void setPkiKeyId(String pkiKeyId) {
		this.pkiKeyId = pkiKeyId;
	}

	/**
	 * Gets the certificate data.
	 *
	 * @return the certificate data
	 */
	public String getCertificateData() {
		return certificateData;
	}

	/**
	 * Sets the certificate data.
	 *
	 * @param certificateData
	 *            the new certificate data
	 */
	public void setCertificateData(String certificateData) {
		this.certificateData = certificateData;
	}

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
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate
	 *            the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	 * Gets the updated date.
	 *
	 * @return the updated date
	 */
	public Date getUpdatedDate() {
		return updatedDate;
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
