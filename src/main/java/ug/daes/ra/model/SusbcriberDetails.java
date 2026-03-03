package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "susbcriber_details")
@NamedQuery(name = "SusbcriberDetails.findAll", query = "SELECT s FROM SusbcriberDetails s")
public class SusbcriberDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "subscriber_uid")
	private String subscriberUid;
	
	/** The full name. */
	@Column(name = "full_name")
	private String fullName;
	
	/** The date of birth. */
	@Column(name = "date_of_birth")
	private String dateOfBirth;
	
	/** The id doc type. */
	@Column(name = "id_doc_type")
	private String idDocType;
	
	/** The id doc number. */
	@Column(name = "id_doc_number")
	private String idDocNumber;
	
	/** The mobile number. */
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	/** The e mail. */
	@Column(name = "email_id")
	private String eMail;
	
	/** The subscriber status. */
	@Column(name = "os_name")
	private String osName;
	
	/** The subscriber status. */
	@Column(name = "os_version")
	private String osVersion;
	
	/** The subscriber status. */
	@Column(name = "app_version")
	private String appVersion;
	
	/** The subscriber status. */
	@Column(name = "device_info")
	private String deviceInfo;
	
	/** The subscriber status. */
	@Column(name = "subscriber_status")
	private String subscriberStatus;
	
	/** The subscriber status. */
	@Column(name = "device_status")
	private String deviceStatus;

	public String getSubscriberUid() {
		return subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNumber() {
		return idDocNumber;
	}

	public void setIdDocNumber(String idDocNumber) {
		this.idDocNumber = idDocNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	@Override
	public String toString() {
		return "SusbcriberDetails [subscriberUid=" + subscriberUid + ", fullName=" + fullName + ", dateOfBirth="
				+ dateOfBirth + ", idDocType=" + idDocType + ", idDocNumber=" + idDocNumber + ", mobileNumber="
				+ mobileNumber + ", eMail=" + eMail + ", osName=" + osName + ", osVersion=" + osVersion
				+ ", appVersion=" + appVersion + ", deviceInfo=" + deviceInfo + ", subscriberStatus=" + subscriberStatus
				+ ", deviceStatus=" + deviceStatus + "]";
	}

}
