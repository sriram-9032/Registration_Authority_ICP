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
 * The Class Subscribercertificatemanagementdata.
 */
@Entity
@Table(name="subscriber_certificate_life_cycle")
public class SubscriberCertificateLifeCycle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The certificate management id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="subscriber_certificate_life_cycle_id")
	private int SubscriberCertificateLifeCycleId;

	/** The subscriber digital id. */
	@Column(name="subscriber_uid",nullable = false)
	private String subscriberUniqueId;

	/** The certificate serial number. */
	@Column(name="certificate_serial_number",nullable = false)
	private String certificateSerialNumber;

	/** The certificate status. */
	@Column(name="certificate_status",nullable = false)
	private String certificateStatus;

	/** The certificate type. */
	@Column(name="certificate_type", nullable = false)
	private String certificateType;

	/** The revoked reason. */
	@Column(name="revocation_reason")
	private String revokedReason;

	/** The creation date. */
	@Column(name="created_date",nullable = false)
	private Date creationDate;

	/**
	 * Gets the subscriber certificate life cycle id.
	 *
	 * @return the subscriber certificate life cycle id
	 */
	public int getSubscriberCertificateLifeCycleId() {
		return SubscriberCertificateLifeCycleId;
	}

	/**
	 * Sets the subscriber certificate life cycle id.
	 *
	 * @param subscriberCertificateLifeCycleId
	 *            the new subscriber certificate life cycle id
	 */
	public void setSubscriberCertificateLifeCycleId(int subscriberCertificateLifeCycleId) {
		SubscriberCertificateLifeCycleId = subscriberCertificateLifeCycleId;
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
	 * Gets the revoked reason.
	 *
	 * @return the revoked reason
	 */
	public String getRevokedReason() {
		return revokedReason;
	}

	/**
	 * Sets the revoked reason.
	 *
	 * @param revokedReason
	 *            the new revoked reason
	 */
	public void setRevokedReason(String revokedReason) {
		this.revokedReason = revokedReason;
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

//	@Override
//	public String toString() {
//		return "SubscriberCertificateLifeCycle [SubscriberCertificateLifeCycleId=" + SubscriberCertificateLifeCycleId
//				+ ", subscriberUniqueId=" + subscriberUniqueId + ", certificateSerialNumber=" + certificateSerialNumber
//				+ ", certificateStatus=" + certificateStatus + ", certificateType=" + certificateType
//				+ ", revokedReason=" + revokedReason + ", creationDate=" + creationDate + "]";
//	}
	
	/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
	public String toString() {
		return "{"
				+"\" SubscriberCertificateLifeCycleId \" : \"" +SubscriberCertificateLifeCycleId + "\","
				+"\" SubscriberUniqueId \" : \"" +subscriberUniqueId + "\"," 
				+"\" CertificateSerialNumber \" : \"" +certificateSerialNumber + "\","
				+"\" CertificateStatus \" : \"" +certificateStatus + "\","
				+"\" CertificateType \" : \"" +certificateType + "\","
				+"\" RevokedReason \" : \"" +revokedReason + "\","
				+"\" CreationDate \" : \"" +creationDate + "\""
				+ "}";
	}

}
