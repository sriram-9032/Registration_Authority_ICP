package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="organization_details")
@NamedQuery(name="OrganizationDetails.findAll", query="SELECT o FROM OrganizationDetails o")
public class OrganizationDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "organization_details_id")
	private int organizationId;
	
	@Column(name="organization_uid")
	private String organizationUid;
	
	@Column(name= "org_name")
	private String organizationName;
	
	@Column(name = "organization_email")
	private String organizationEmail;
	
	@Column(name = "e_seal_image")
	private String eSealImage ;
	
	@Column(name = "authorized_letter_for_signatories")
	private String authorizedLetterForSignatories ;
	
	@Column(name="unique_regd_no")
	private String uniqueRegdNo;
	
	@Column(name="tax_no")
	private String taxNo;
	
	@Column(name="corporate_office_address")
	private String corporateOfficeAddress;
	
	@Column(name="incorporation_file")
	private String incorporation;
	
	@Column(name="tax_file")
	private String tax;
	
	@Column(name ="organization_segments")
	private int segment;

	@Column(name ="other_legal_document")
	private String otherLegalDocument;
	
	@Column(name = "other_eseal_document")
	private String otherESealDocument;
	
	@Column(name ="signed_pdf")
	private String signedPdf;
	
	@Column(name ="created_by")
	private String createdBy;
	
	@Column(name ="updated_by")
	private String updatedBy;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "updated_on")
	private String updatedOn;
	
	@Column(name ="organization_status")
	private String Status;
	
	@Column(name = "enable_post_paid_option")
	private boolean enablePostPaidOption;
	
	@Column(name = "spoc_ugpass_email")
	private String spocUgpassEmail;

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationUid() {
		return organizationUid;
	}

	public void setOrganizationUid(String organizationUid) {
		this.organizationUid = organizationUid;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationEmail() {
		return organizationEmail;
	}

	public void setOrganizationEmail(String organizationEmail) {
		this.organizationEmail = organizationEmail;
	}

	public String geteSealImage() {
		return eSealImage;
	}

	public void seteSealImage(String eSealImage) {
		this.eSealImage = eSealImage;
	}

	public String getAuthorizedLetterForSignatories() {
		return authorizedLetterForSignatories;
	}

	public void setAuthorizedLetterForSignatories(String authorizedLetterForSignatories) {
		this.authorizedLetterForSignatories = authorizedLetterForSignatories;
	}

	public String getUniqueRegdNo() {
		return uniqueRegdNo;
	}

	public void setUniqueRegdNo(String uniqueRegdNo) {
		this.uniqueRegdNo = uniqueRegdNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getCorporateOfficeAddress() {
		return corporateOfficeAddress;
	}

	public void setCorporateOfficeAddress(String corporateOfficeAddress) {
		this.corporateOfficeAddress = corporateOfficeAddress;
	}

	public String getIncorporation() {
		return incorporation;
	}

	public void setIncorporation(String incorporation) {
		this.incorporation = incorporation;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public int getSegment() {
		return segment;
	}

	public void setSegment(int segment) {
		this.segment = segment;
	}

	public String getOtherLegalDocument() {
		return otherLegalDocument;
	}

	public void setOtherLegalDocument(String otherLegalDocument) {
		this.otherLegalDocument = otherLegalDocument;
	}

	public String getOtherESealDocument() {
		return otherESealDocument;
	}

	public void setOtherESealDocument(String otherESealDocument) {
		this.otherESealDocument = otherESealDocument;
	}

	public String getSignedPdf() {
		return signedPdf;
	}

	public void setSignedPdf(String signedPdf) {
		this.signedPdf = signedPdf;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public boolean isEnablePostPaidOption() {
		return enablePostPaidOption;
	}

	public void setEnablePostPaidOption(boolean enablePostPaidOption) {
		this.enablePostPaidOption = enablePostPaidOption;
	}

	public String getSpocUgpassEmail() {
		return spocUgpassEmail;
	}

	public void setSpocUgpassEmail(String spocUgpassEmail) {
		this.spocUgpassEmail = spocUgpassEmail;
	}

	@Override
	public String toString() {
		return "OrganizationDetails [organizationId=" + organizationId + ", organizationUid=" + organizationUid
				+ ", organizationName=" + organizationName + ", organizationEmail=" + organizationEmail
				+ ", eSealImage=" + eSealImage + ", authorizedLetterForSignatories=" + authorizedLetterForSignatories
				+ ", uniqueRegdNo=" + uniqueRegdNo + ", taxNo=" + taxNo + ", corporateOfficeAddress="
				+ corporateOfficeAddress + ", incorporation=" + incorporation + ", tax=" + tax + ", segment=" + segment
				+ ", otherLegalDocument=" + otherLegalDocument + ", otherESealDocument=" + otherESealDocument
				+ ", signedPdf=" + signedPdf + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", Status=" + Status + ", enablePostPaidOption="
				+ enablePostPaidOption + ", spocUgpassEmail=" + spocUgpassEmail + "]";
	}
	
}
