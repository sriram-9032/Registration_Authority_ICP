/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the subscriber_devices database table.
 * 
 */
@Entity
@Table(name = "subscriber_devices")
@NamedQuery(name = "SubscriberDevice.findAll", query = "SELECT s FROM SubscriberDevice s")
public class SubscriberDevice implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created date. */
	@Column(name = "created_date")
	private String createdDate;

	/** The device status. */
	@Column(name = "device_status")
	private String deviceStatus;

	/** The device uid. */
	@Column(name = "device_uid")
	private String deviceUid;

	/** The subscriber device id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriber_device_id")
	private int subscriberDeviceId;

	/** The subscriber uid. */
	@Column(name = "subscriber_uid")
	private String subscriberUid;

	/** The updated date. */
	@Column(name = "updated_date")
	private String updatedDate;

	/**
	 * Instantiates a new subscriber device.
	 */
	public SubscriberDevice() {
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the device status.
	 *
	 * @return the device status
	 */
	public String getDeviceStatus() {
		return this.deviceStatus;
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
	 * Gets the device uid.
	 *
	 * @return the device uid
	 */
	public String getDeviceUid() {
		return this.deviceUid;
	}

	/**
	 * Sets the device uid.
	 *
	 * @param deviceUid
	 *            the new device uid
	 */
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	/**
	 * Gets the subscriber device id.
	 *
	 * @return the subscriber device id
	 */
	public int getSubscriberDeviceId() {
		return this.subscriberDeviceId;
	}

	/**
	 * Sets the subscriber device id.
	 *
	 * @param subscriberDeviceId
	 *            the new subscriber device id
	 */
	public void setSubscriberDeviceId(int subscriberDeviceId) {
		this.subscriberDeviceId = subscriberDeviceId;
	}

	/**
	 * Gets the subscriber uid.
	 *
	 * @return the subscriber uid
	 */
	public String getSubscriberUid() {
		return this.subscriberUid;
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
	 * Gets the updated date.
	 *
	 * @return the updated date
	 */
	public String getUpdatedDate() {
		return this.updatedDate;
	}

	/**
	 * Sets the updated date.
	 *
	 * @param updatedDate
	 *            the new updated date
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "SubscriberDevice [createdDate=" + createdDate + ", deviceStatus=" + deviceStatus + ", deviceUid="
				+ deviceUid + ", subscriberDeviceId=" + subscriberDeviceId + ", subscriberUid=" + subscriberUid
				+ ", updatedDate=" + updatedDate + "]";
	}
	
	

}