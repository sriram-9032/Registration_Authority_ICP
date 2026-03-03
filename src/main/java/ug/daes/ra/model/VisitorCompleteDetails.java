//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ug.daes.ra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "visitor_complete_details")
@Entity
public class VisitorCompleteDetails {
	@Id
	@Column(name = "subscriber_uid")
	private String subscriberUid;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "subscriber_type")
	private String subscriberType;
	@Column(name = "date_of_birth")
	private String dob;
	@Column(name = "id_doc_type")
	private String idDocType;
	@Column(name = "id_doc_number")
	private String idDocNumber;
	@Column(name = "created_date")
	private String createdOn;
	@Column(name = "certificate_status")
	private String certificateStatus;
	@Column(name = "device_status")
	private String deviceStatus;
	@Column(name = "subscriber_status")
	private String subscriberStatus;
	@Column(name = "device_registration_time")
	private String deviceRegistrationTime;
	@Column(name = "cerificate_expiry_date")
	private String certificateExpiryDate;
	@Column(name = "certificate_issue_date")
	private String certificateIssueDate;
	@Column(name = "sign_pin_set_date")
	private String signPinSetDate;
	@Column(name = "auth_pin_set_date")
	private String authPinSetDate;
	@Column(name = "selfie_uri")
	private String selfieUri;
	@Column(name = "on_boarding_time")
	private String onBoardingTime;
	@Column(name = "on_boarding_method")
	private String onBoardingMethod;
	@Column(name = "level_of_assurance")
	private String levelOfAssurance;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "geo_location")
	private String geoLocation;
	@Column(name = "gender")
	private String gender;
	@Column(name = "email_id")
	private String eMail;
	@Column(name = "revocation_date")
	private String revocationDate;
	@Column(name = "revocation_reason")
	private String revocationReason;
	@Column(name = "selfie_thumbnail_uri")
	private String photo;
	@Column(name = "certificate_serial_number")
	private String certificateSerialNumber;
	@Column(name = "video_url")
	private String videoUrl;
	@Column(name = "video_type")
	private String videoType;
	@Column(name = "non_resident_card_status")
	private String nonResidentCardStatus;
	@Column(name = "non_resident_id")
	private String nonResidentId;
	@Column(name = "id_doc_image")
	private String idDocImage;
	@Column(name = "country_name")
	private String countryName;
	@Column(name = "passport_expiry_date")
	private String passportExpiryDate;
	@Column(name = "visa_number")
	private String visaNumber;
	@Column(name = "visa_issue_date")
	private String visaIssueDate;
	@Column(name = "visa_expiry_date")
	private String visaExpiryDate;
	@Column(name = "visa_type")
	private String visaType;
	@Column(name = "employer")
	private String employer;
	@Column(name = "is_blacklisted")
	private String blacklisted;
	@Column(name = "onboarding_document")
	private String onboardingDocumnet;
	@Column(name = "resident_id")
	private String residentId;
	
	@Column(name = "residence_id_document")
	private String residenceIdDocument;
	
	@Column(name = "resident_card_issued_on")
	private String residentCardIssuedOn;
	
	@Column(name = "resident_card_valid_upto")
	private String residentCardValidUpto;
	
	@Column(name = "resident_card_status")
	private String residentCardStatus;
	
	@Column(name = "visitor_card_number")
	private String visitorCardNumber;
	
	@Column(name = "visitor_card_pdf")
	private String visitorCardPdf;
	
	@Column(name = "visitor_card_status")
	private String visitorCardStatus;
	
	@Column(name = "visitor_card_issued_on")
	private String visitorCardIssuedOn;
	
	@Column(name = "visitor_card_valid_upto")
	private String visitorCardValidUpto;
	
	@Column(name = "tradelicense_document_card")
	private String tradelicenseDocumentCard;
	
	@Column(name = "establishment_card")
	private String establishmentCard;
	
	@Column(name = "blood_group")
	private String bloodGroup;
	
	@Column(name = "place_of_birth")
	private String placeOfBirth;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "father_name")
	private String fatherName;
	
	@Column(name = "mother_name")
	private String motherName;
	
	@Column(name = "occupation")
	private String occupation;
	
	@Column(name = "signed_visa_document")
	private String signedVisaDocument;
	
	@Column(name = "visa_status")
	private String visaStatus;
	
	@Column(name = "non_resident_card_valid_upto")
	private String nonResidentCardValidUpto;
	
	@Column(name = "non_resident_card_issued_on")
	private String nonResidentCardIssuedOn;
	
	@Column(name = "non_resident_id_documnet")
	private String nonResidentIdDocumnet;
	
	

	
	
	

	public String getResidenceIdDocument() {
		return residenceIdDocument;
	}

	public void setResidenceIdDocument(String residenceIdDocument) {
		this.residenceIdDocument = residenceIdDocument;
	}

	public String getResidentCardIssuedOn() {
		return residentCardIssuedOn;
	}

	public void setResidentCardIssuedOn(String residentCardIssuedOn) {
		this.residentCardIssuedOn = residentCardIssuedOn;
	}

	public String getResidentCardValidUpto() {
		return residentCardValidUpto;
	}

	public void setResidentCardValidUpto(String residentCardValidUpto) {
		this.residentCardValidUpto = residentCardValidUpto;
	}

	public String getResidentCardStatus() {
		return residentCardStatus;
	}

	public void setResidentCardStatus(String residentCardStatus) {
		this.residentCardStatus = residentCardStatus;
	}

	public String getVisitorCardNumber() {
		return visitorCardNumber;
	}

	public void setVisitorCardNumber(String visitorCardNumber) {
		this.visitorCardNumber = visitorCardNumber;
	}

	public String getVisitorCardPdf() {
		return visitorCardPdf;
	}

	public void setVisitorCardPdf(String visitorCardPdf) {
		this.visitorCardPdf = visitorCardPdf;
	}

	public String getVisitorCardStatus() {
		return visitorCardStatus;
	}

	public void setVisitorCardStatus(String visitorCardStatus) {
		this.visitorCardStatus = visitorCardStatus;
	}

	public String getVisitorCardIssuedOn() {
		return visitorCardIssuedOn;
	}

	public void setVisitorCardIssuedOn(String visitorCardIssuedOn) {
		this.visitorCardIssuedOn = visitorCardIssuedOn;
	}

	public String getVisitorCardValidUpto() {
		return visitorCardValidUpto;
	}

	public void setVisitorCardValidUpto(String visitorCardValidUpto) {
		this.visitorCardValidUpto = visitorCardValidUpto;
	}

	public String getTradelicenseDocumentCard() {
		return tradelicenseDocumentCard;
	}

	public void setTradelicenseDocumentCard(String tradelicenseDocumentCard) {
		this.tradelicenseDocumentCard = tradelicenseDocumentCard;
	}

	public VisitorCompleteDetails() {
	}

	public String getResidentId() {
		return this.residentId;
	}

	public void setResidentId(String residentId) {
		this.residentId = residentId;
	}

	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSubscriberType() {
		return this.subscriberType;
	}

	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}

	public String getDob() {
		return this.dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getIdDocType() {
		return this.idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNumber() {
		return this.idDocNumber;
	}

	public void setIdDocNumber(String idDocNumber) {
		this.idDocNumber = idDocNumber;
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCertificateStatus() {
		return this.certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getDeviceStatus() {
		return this.deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getSubscriberStatus() {
		return this.subscriberStatus;
	}

	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	public String getDeviceRegistrationTime() {
		return this.deviceRegistrationTime;
	}

	public void setDeviceRegistrationTime(String deviceRegistrationTime) {
		this.deviceRegistrationTime = deviceRegistrationTime;
	}

	public String getCertificateExpiryDate() {
		return this.certificateExpiryDate;
	}

	public void setCertificateExpiryDate(String certificateExpiryDate) {
		this.certificateExpiryDate = certificateExpiryDate;
	}

	public String getCertificateIssueDate() {
		return this.certificateIssueDate;
	}

	public void setCertificateIssueDate(String certificateIssueDate) {
		this.certificateIssueDate = certificateIssueDate;
	}

	public String getSignPinSetDate() {
		return this.signPinSetDate;
	}

	public void setSignPinSetDate(String signPinSetDate) {
		this.signPinSetDate = signPinSetDate;
	}

	public String getAuthPinSetDate() {
		return this.authPinSetDate;
	}

	public void setAuthPinSetDate(String authPinSetDate) {
		this.authPinSetDate = authPinSetDate;
	}

	public String getSelfieUri() {
		return this.selfieUri;
	}

	public void setSelfieUri(String selfieUri) {
		this.selfieUri = selfieUri;
	}

	public String getOnBoardingTime() {
		return this.onBoardingTime;
	}

	public void setOnBoardingTime(String onBoardingTime) {
		this.onBoardingTime = onBoardingTime;
	}

	public String getOnBoardingMethod() {
		return this.onBoardingMethod;
	}

	public void setOnBoardingMethod(String onBoardingMethod) {
		this.onBoardingMethod = onBoardingMethod;
	}

	public String getLevelOfAssurance() {
		return this.levelOfAssurance;
	}

	public void setLevelOfAssurance(String levelOfAssurance) {
		this.levelOfAssurance = levelOfAssurance;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGeoLocation() {
		return this.geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String geteMail() {
		return this.eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getRevocationDate() {
		return this.revocationDate;
	}

	public void setRevocationDate(String revocationDate) {
		this.revocationDate = revocationDate;
	}

	public String getRevocationReason() {
		return this.revocationReason;
	}

	public void setRevocationReason(String revocationReason) {
		this.revocationReason = revocationReason;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCertificateSerialNumber() {
		return this.certificateSerialNumber;
	}

	public void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoType() {
		return this.videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getNonResidentCardStatus() {
		return this.nonResidentCardStatus;
	}

	public void setNonResidentCardStatus(String nonResidentCardStatus) {
		this.nonResidentCardStatus = nonResidentCardStatus;
	}

	public String getNonResidentId() {
		return this.nonResidentId;
	}

	public void setNonResidentId(String nonResidentId) {
		this.nonResidentId = nonResidentId;
	}

	public String getIdDocImage() {
		return this.idDocImage;
	}

	public void setIdDocImage(String idDocImage) {
		this.idDocImage = idDocImage;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPassportExpiryDate() {
		return this.passportExpiryDate;
	}

	public void setPassportExpiryDate(String passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public String getVisaNumber() {
		return this.visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getVisaIssueDate() {
		return this.visaIssueDate;
	}

	public void setVisaIssueDate(String visaIssueDate) {
		this.visaIssueDate = visaIssueDate;
	}

	public String getVisaExpiryDate() {
		return this.visaExpiryDate;
	}

	public void setVisaExpiryDate(String visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}

	public String getVisaType() {
		return this.visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getEmployer() {
		return this.employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getBlacklisted() {
		return this.blacklisted;
	}

	public void setBlacklisted(String blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getOnboardingDocumnet() {
		return this.onboardingDocumnet;
	}

	public void setOnboardingDocumnet(String onboardingDocumnet) {
		this.onboardingDocumnet = onboardingDocumnet;
	}
	

	public String getEstablishmentCard() {
		return establishmentCard;
	}

	public void setEstablishmentCard(String establishmentCard) {
		this.establishmentCard = establishmentCard;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSignedVisaDocument() {
		return signedVisaDocument;
	}

	public void setSignedVisaDocument(String signedVisaDocument) {
		this.signedVisaDocument = signedVisaDocument;
	}

	public String getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	public String getNonResidentCardValidUpto() {
		return nonResidentCardValidUpto;
	}

	public void setNonResidentCardValidUpto(String nonResidentCardValidUpto) {
		this.nonResidentCardValidUpto = nonResidentCardValidUpto;
	}

	public String getNonResidentCardIssuedOn() {
		return nonResidentCardIssuedOn;
	}

	public void setNonResidentCardIssuedOn(String nonResidentCardIssuedOn) {
		this.nonResidentCardIssuedOn = nonResidentCardIssuedOn;
	}

	public String getNonResidentIdDocumnet() {
		return nonResidentIdDocumnet;
	}

	public void setNonResidentIdDocumnet(String nonResidentIdDocumnet) {
		this.nonResidentIdDocumnet = nonResidentIdDocumnet;
	}

	@Override
	public String toString() {
		return "VisitorCompleteDetails [subscriberUid=" + subscriberUid + ", fullName=" + fullName + ", subscriberType="
				+ subscriberType + ", dob=" + dob + ", idDocType=" + idDocType + ", idDocNumber=" + idDocNumber
				+ ", createdOn=" + createdOn + ", certificateStatus=" + certificateStatus + ", deviceStatus="
				+ deviceStatus + ", subscriberStatus=" + subscriberStatus + ", deviceRegistrationTime="
				+ deviceRegistrationTime + ", certificateExpiryDate=" + certificateExpiryDate
				+ ", certificateIssueDate=" + certificateIssueDate + ", signPinSetDate=" + signPinSetDate
				+ ", authPinSetDate=" + authPinSetDate + ", selfieUri=" + selfieUri + ", onBoardingTime="
				+ onBoardingTime + ", onBoardingMethod=" + onBoardingMethod + ", levelOfAssurance=" + levelOfAssurance
				+ ", mobileNumber=" + mobileNumber + ", geoLocation=" + geoLocation + ", gender=" + gender + ", eMail="
				+ eMail + ", revocationDate=" + revocationDate + ", revocationReason=" + revocationReason + ", photo="
				+ photo + ", certificateSerialNumber=" + certificateSerialNumber + ", videoUrl=" + videoUrl
				+ ", videoType=" + videoType + ", nonResidentCardStatus=" + nonResidentCardStatus + ", nonResidentId="
				+ nonResidentId + ", idDocImage=" + idDocImage + ", countryName=" + countryName
				+ ", passportExpiryDate=" + passportExpiryDate + ", visaNumber=" + visaNumber + ", visaIssueDate="
				+ visaIssueDate + ", visaExpiryDate=" + visaExpiryDate + ", visaType=" + visaType + ", employer="
				+ employer + ", blacklisted=" + blacklisted + ", onboardingDocumnet=" + onboardingDocumnet
				+ ", residentId=" + residentId + ", residenceIdDocument=" + residenceIdDocument
				+ ", residentCardIssuedOn=" + residentCardIssuedOn + ", residentCardValidUpto=" + residentCardValidUpto
				+ ", residentCardStatus=" + residentCardStatus + ", visitorCardNumber=" + visitorCardNumber
				+ ", visitorCardPdf=" + visitorCardPdf + ", visitorCardStatus=" + visitorCardStatus
				+ ", visitorCardIssuedOn=" + visitorCardIssuedOn + ", visitorCardValidUpto=" + visitorCardValidUpto
				+ ", tradelicenseDocumentCard=" + tradelicenseDocumentCard + ", establishmentCard=" + establishmentCard
				+ ", bloodGroup=" + bloodGroup + ", placeOfBirth=" + placeOfBirth + ", address=" + address
				+ ", fatherName=" + fatherName + ", motherName=" + motherName + ", occupation=" + occupation
				+ ", signedVisaDocument=" + signedVisaDocument + ", visaStatus=" + visaStatus
				+ ", nonResidentCardValidUpto=" + nonResidentCardValidUpto + ", nonResidentCardIssuedOn="
				+ nonResidentCardIssuedOn + ", nonResidentIdDocumnet=" + nonResidentIdDocumnet + "]";
	}
	

//	public String toString() {
//		return "VisitorCompleteDetails{subscriberUid='" + this.subscriberUid + '\'' + ", fullName='" + this.fullName
//				+ '\'' + ", subscriberType='" + this.subscriberType + '\'' + ", dob='" + this.dob + '\''
//				+ ", idDocType='" + this.idDocType + '\'' + ", idDocNumber='" + this.idDocNumber + '\''
//				+ ", createdOn='" + this.createdOn + '\'' + ", certificateStatus='" + this.certificateStatus + '\''
//				+ ", deviceStatus='" + this.deviceStatus + '\'' + ", subscriberStatus='" + this.subscriberStatus + '\''
//				+ ", deviceRegistrationTime='" + this.deviceRegistrationTime + '\'' + ", certificateExpiryDate='"
//				+ this.certificateExpiryDate + '\'' + ", certificateIssueDate='" + this.certificateIssueDate + '\''
//				+ ", signPinSetDate='" + this.signPinSetDate + '\'' + ", authPinSetDate='" + this.authPinSetDate + '\''
//				+ ", selfieUri='" + this.selfieUri + '\'' + ", onBoardingTime='" + this.onBoardingTime + '\''
//				+ ", onBoardingMethod='" + this.onBoardingMethod + '\'' + ", levelOfAssurance='" + this.levelOfAssurance
//				+ '\'' + ", mobileNumber='" + this.mobileNumber + '\'' + ", geoLocation='" + this.geoLocation + '\''
//				+ ", gender='" + this.gender + '\'' + ", eMail='" + this.eMail + '\'' + ", revocationDate='"
//				+ this.revocationDate + '\'' + ", revocationReason='" + this.revocationReason + '\'' + ", photo='"
//				+ this.photo + '\'' + ", certificateSerialNumber='" + this.certificateSerialNumber + '\''
//				+ ", videoUrl='" + this.videoUrl + '\'' + ", videoType='" + this.videoType + '\''
//				+ ", nonResidentCardStatus='" + this.nonResidentCardStatus + '\'' + ", nonResidentId='"
//				+ this.nonResidentId + '\'' + ", idDocImage='" + this.idDocImage + '\'' + ", countryName='"
//				+ this.countryName + '\'' + ", passportExpiryDate='" + this.passportExpiryDate + '\'' + ", visaNumber='"
//				+ this.visaNumber + '\'' + ", visaIssueDate='" + this.visaIssueDate + '\'' + ", visaExpiryDate='"
//				+ this.visaExpiryDate + '\'' + ", visaType='" + this.visaType + '\'' + ", employer='" + this.employer
//				+ '\'' + ", blacklisted='" + this.blacklisted + '\'' + ", onboardingDocumnet='"
//				+ this.onboardingDocumnet + '\'' + ", residentId='" + this.residentId + '\'' + '}';
//	}
}
