package ug.daes.ra.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="organization_certificates")
public class OrganizationCertificates implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "certificate_serial_number", nullable = false, unique = true)
	private String certificateSerialNumber;
	
	@Column(name = "organization_uid", nullable = false)
	private String organizationUid;
	
	@Column(name = "pki_key_id", nullable = false, unique = true)
	private String pkiKeyId;
	
	@Column(name = "certificate_data", nullable = false, unique = true)
	private String certificateData;
	
	@Column(name = "wrapped_key", nullable = false, unique = true)
	private String wrappedKey;
	
	@Column(name = "certificate_issue_date", nullable = false)
	private Date certificateStartDate;
	
	@Column(name = "cerificate_expiry_date", nullable = false)
	private Date certificateEndDate;
	
	@Column(name = "certificate_status", nullable = false)
	private String certificateStatus;
	
	@Column(name = "remarks")
	private String revocationReason;
	
	@Column(name = "created_date", nullable = false)
	private Date creationDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "certificate_type", nullable = false)
	private String certificateType;

	public Date getCertificateStartDate() {
		return certificateStartDate;
	}

	public void setCertificateStartDate(Date certificateStartDate) {
		this.certificateStartDate = certificateStartDate;
	}

	public Date getCertificateEndDate() {
		return certificateEndDate;
	}

	public void setCertificateEndDate(Date certificateEndDate) {
		this.certificateEndDate = certificateEndDate;
	}

	public String getPkiKeyId() {
		return pkiKeyId;
	}

	public void setPkiKeyId(String pkiKeyId) {
		this.pkiKeyId = pkiKeyId;
	}

	public String getCertificateData() {
		return certificateData;
	}

	public void setCertificateData(String certificateData) {
		this.certificateData = certificateData;
	}

	public String getCertificateSerialNumber() {
		return certificateSerialNumber;
	}

	public void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getOrganizationUid() {
		return organizationUid;
	}

	public void setOrganizationUid(String organizationUid) {
		this.organizationUid = organizationUid;
	}

	public String getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getWrappedKey() {
		return wrappedKey;
	}

	public void setWrappedKey(String wrappedKey) {
		this.wrappedKey = wrappedKey;
	}

	public String getRevocationReason() {
		return revocationReason;
	}

	public void setRevocationReason(String revocationReason) {
		this.revocationReason = revocationReason;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String toString() {
		return "OrganizationCertificates [certificateSerialNumber=" + certificateSerialNumber + ", organizationUid="
				+ organizationUid + ", pkiKeyId=" + pkiKeyId + ", certificateData=" + certificateData + ", wrappedKey="
				+ wrappedKey + ", certificateStartDate=" + certificateStartDate + ", certificateEndDate="
				+ certificateEndDate + ", certificateStatus=" + certificateStatus + ", revocationReason="
				+ revocationReason + ", creationDate=" + creationDate + ", updatedDate=" + updatedDate
				+ ", certificateType=" + certificateType + "]";
	}
}
