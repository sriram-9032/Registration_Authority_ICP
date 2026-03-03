package ug.daes.ra.request.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "trusted_stakeholders")
public class TrustedStakeholder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "spoc_ugpass_email")
	private String spocUgpassEmail;
	@Column(name = "reference_id")
	private String referenceId;
	@Column(name = "organization_uid")
	private String organizationUid;
	@Column(name = "referred_by")
	private String referredBy;
	@Column(name = "status")
	private boolean status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "onboarding_time")
	private Date onboardingTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;
	
	@Column(name = "stakeholder_type")
	private String stakeholderType;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpocUgpassEmail() {
		return spocUgpassEmail;
	}

	public void setSpocUgpassEmail(String spocUgpassEmail) {
		this.spocUgpassEmail = spocUgpassEmail;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getOrganizationUid() {
		return organizationUid;
	}

	public void setOrganizationUid(String organizationUid) {
		this.organizationUid = organizationUid;
	}
	

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getOnboardingTime() {
		return onboardingTime;
	}

	public void setOnboardingTime(Date onboardingTime) {
		this.onboardingTime = onboardingTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getStakeholderType() {
		return stakeholderType;
	}

	public void setStakeholderType(String stakeholderType) {
		this.stakeholderType = stakeholderType;
	}

	@Override
	public String toString() {
		return "TrustedStakeholder [id=" + id + ", name=" + name + ", spocUgpassEmail=" + spocUgpassEmail
				+ ", referenceId=" + referenceId + ", organizationUid=" + organizationUid + ", referredBy=" + referredBy
				+ ", status=" + status + ", onboardingTime=" + onboardingTime + ", creationTime=" + creationTime
				+ ", stakeholderType=" + stakeholderType + "]";
	}

//	@Override
//	public String toString() {
//		return "TrustedStakeholder [id=" + id + ", name=" + name + ", spocUgpassEmail=" + spocUgpassEmail
//				+ ", referenceId=" + referenceId + ", organizationUid=" + organizationUid + ", status=" + status
//				+ ", onboardingTime=" + onboardingTime + ", creationTime=" + creationTime + ", stakeholderType="
//				+ stakeholderType + "]";
//	}
}
