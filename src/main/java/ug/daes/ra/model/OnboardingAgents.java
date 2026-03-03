package ug.daes.ra.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "onboarding_agents")
public class OnboardingAgents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="agent_ugpass_suid")
    private String suid;

    @Column(name = "agent_name")
    private String name;
    @Column(name = "agent_ugpass_email")
    private String email;

    @Column(name = "agent_ugpass_mobile_number")
    private String mobileNumber;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "created_On")
    private Date createdOn;


    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "status")
    private String status;

    @Column(name="device_id")
    private String deviceId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "OnboardingAgents{" +
                "id=" + id +
                ", suid='" + suid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", status='" + status + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
