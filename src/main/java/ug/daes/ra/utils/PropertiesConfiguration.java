/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The Class PropertiesConfiguration.
 */
@Component
@ConfigurationProperties(prefix = "ra.service")
public class PropertiesConfiguration {

	/** The status. */
	private String status;

	/** The pki. */
	private String pki;

	/** The issuecertificatecallbackurl. */
	private String issuecertificatecallbackurl;

	/** The notification. */
	private String notification;

	/**
	 * Gets the pki.
	 *
	 * @return the pki
	 */
	public String getPki() {
		return pki;
	}

	/**
	 * Sets the pki.
	 *
	 * @param pkiurl
	 *            the new pki
	 */
	public void setPki(String pkiurl) {
		this.pki = pkiurl;
	}

	/**
	 * Gets the issuecertificatecallbackurl.
	 *
	 * @return the issuecertificatecallbackurl
	 */
	public String getIssuecertificatecallbackurl() {
		return issuecertificatecallbackurl;
	}

	/**
	 * Sets the issuecertificatecallbackurl.
	 *
	 * @param issuecertificatecallbackurl
	 *            the new issuecertificatecallbackurl
	 */
	public void setIssuecertificatecallbackurl(String issuecertificatecallbackurl) {
		this.issuecertificatecallbackurl = issuecertificatecallbackurl;
	}

	/**
	 * Gets the notification.
	 *
	 * @return the notification
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * Sets the notification.
	 *
	 * @param notification
	 *            the new notification
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PropertiesConfiguration [status=" + status + ", pki=" + pki + ", issuecertificatecallbackurl="
				+ issuecertificatecallbackurl + ", notification=" + notification + "]";
	}

}
