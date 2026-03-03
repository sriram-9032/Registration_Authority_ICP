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
@Table(name="subscriber_devices_history")
@NamedQuery(name="SubscriberDeviceHistory.findAll", query="SELECT s FROM SubscriberDeviceHistory s")
public class SubscriberDeviceHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name="created_date")
	private String createdDate;

	@Column(name="device_status")
	private String deviceStatus;

	@Column(name="device_uid")
	private String deviceUid;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="subscriber_device_history_id")
	private int subscriberDeviceHistoryId;

	@Column(name="subscriber_uid")
	private String subscriberUid;

	@Column(name="updated_date")
	private String updatedDate;

	public SubscriberDeviceHistory() {
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeviceStatus() {
		return this.deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceUid() {
		return this.deviceUid;
	}

	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

	public String getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getSubscriberDeviceHistoryId() {
		return subscriberDeviceHistoryId;
	}

	public void setSubscriberDeviceHistoryId(int subscriberDeviceHistoryId) {
		this.subscriberDeviceHistoryId = subscriberDeviceHistoryId;
	}

	@Override
	public String toString() {
		return "SubscriberDeviceHistory [createdDate=" + createdDate + ", deviceStatus=" + deviceStatus + ", deviceUid="
				+ deviceUid + ", subscriberDeviceHistoryId=" + subscriberDeviceHistoryId + ", subscriberUid="
				+ subscriberUid + ", updatedDate=" + updatedDate + "]";
	}
}
