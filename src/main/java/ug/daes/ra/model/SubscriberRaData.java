/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ug.daes.ra.enums.CertificateType;

/**
 * The Class RAPKISubscriberdata.
 */
@Entity
@Table(name = "subscriber_ra_data")
public class SubscriberRaData implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The subscriber digital id. */
	@Id
	@Column(name = "subscriber_uid", unique = true, nullable = false, length = 36)
	private String subscriberUniqueId;

	/** The certificate type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "certificate_type", nullable = false, length = 16)
	private CertificateType certificateType;

	/** The common name. */
	@Column(name = "common_name", nullable = false, length = 36)
	private String commonName;

	/** The country name. */
	@Column(name = "country_name", nullable = false, length = 8)
	private String countryName;

	/** The pki username. */
	@Column(name = "pki_user_name", nullable = false, length = 255)
	private String pkiUsername;

	/** The pki username hash. */
	@Column(name = "pki_user_name_hash", nullable = false, length = 255)
	private String pkiUsernameHash;

	/** The pki password. */
	@Column(name = "pki_password", nullable = false, length = 255)
	private String pkiPassword;

	/** The pki password hash. */
	@Column(name = "pki_password_hash", nullable = false, length = 255)
	private String pkiPasswordHash;

	/** The created date. */
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	/** The updated date. */
	@Column(name = "updated_date", nullable = false)
	private Date updatedDate;

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
	 * Gets the certificate type.
	 *
	 * @return the certificate type
	 */
	public CertificateType getCertificateType() {
		return certificateType;
	}

	/**
	 * Sets the certificate type.
	 *
	 * @param certificateType
	 *            the new certificate type
	 */
	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * Gets the common name.
	 *
	 * @return the common name
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * Sets the common name.
	 *
	 * @param commonName
	 *            the new common name
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName
	 *            the new country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Gets the pki username.
	 *
	 * @return the pki username
	 */
	public String getPkiUsername() {
		return pkiUsername;
	}

	/**
	 * Sets the pki username.
	 *
	 * @param pkiUsername
	 *            the new pki username
	 */
	public void setPkiUsername(String pkiUsername) {
		this.pkiUsername = pkiUsername;
	}

	/**
	 * Gets the pki username hash.
	 *
	 * @return the pki username hash
	 */
	public String getPkiUsernameHash() {
		return pkiUsernameHash;
	}

	/**
	 * Sets the pki username hash.
	 *
	 * @param pkiUsernameHash
	 *            the new pki username hash
	 */
	public void setPkiUsernameHash(String pkiUsernameHash) {
		this.pkiUsernameHash = pkiUsernameHash;
	}

	/**
	 * Gets the pki password.
	 *
	 * @return the pki password
	 */
	public String getPkiPassword() {
		return pkiPassword;
	}

	/**
	 * Sets the pki password.
	 *
	 * @param pkiPassword
	 *            the new pki password
	 */
	public void setPkiPassword(String pkiPassword) {
		this.pkiPassword = pkiPassword;
	}

	/**
	 * Gets the pki password hash.
	 *
	 * @return the pki password hash
	 */
	public String getPkiPasswordHash() {
		return pkiPasswordHash;
	}

	/**
	 * Sets the pki password hash.
	 *
	 * @param pkiPasswordHash
	 *            the new pki password hash
	 */
	public void setPkiPasswordHash(String pkiPasswordHash) {
		this.pkiPasswordHash = pkiPasswordHash;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
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

	@Override
	public String toString() {
		return "SubscriberRaData [subscriberUniqueId=" + subscriberUniqueId + ", certificateType=" + certificateType
				+ ", commonName=" + commonName + ", countryName=" + countryName + ", pkiUsername=" + pkiUsername
				+ ", pkiUsernameHash=" + pkiUsernameHash + ", pkiPassword=" + pkiPassword + ", pkiPasswordHash="
				+ pkiPasswordHash + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

	
}