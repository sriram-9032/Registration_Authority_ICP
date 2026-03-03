/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.dto;

import java.io.Serializable;

import ug.daes.ra.enums.CertificateStatus;
import ug.daes.ra.enums.OnboardingApprovalStatus;

/**
 * The Class NotificationContextDTO.
 */
public class NotificationContextDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The p RE F ONBOARDIN G STATUS. */
	private boolean pREF_ONBOARDING_STATUS;

	/** The p RE F ONBOARDIN G APPROVA L STATUS. */
	private OnboardingApprovalStatus pREF_ONBOARDING_APPROVAL_STATUS;

	/** The p RE F CERTIFICAT E STATUS. */
	private CertificateStatus pREF_CERTIFICATE_STATUS;

	/** The p RE F CERTIFICAT E REVOK E STATUS. */
	private boolean pREF_CERTIFICATE_REVOKE_STATUS;

	/** The p ROMOTIONA L NOTIFICATION. */
	private String pROMOTIONAL_NOTIFICATION;

	/**
	 * Instantiates a new notification context DTO.
	 */
	public NotificationContextDTO() {
	}

	/**
	 * Checks if is p RE F ONBOARDIN G STATUS.
	 *
	 * @return true, if is p RE F ONBOARDIN G STATUS
	 */
	public boolean ispREF_ONBOARDING_STATUS() {
		return pREF_ONBOARDING_STATUS;
	}

	/**
	 * Sets the p RE F ONBOARDIN G STATUS.
	 *
	 * @param pREF_ONBOARDING_STATUS
	 *            the new p RE F ONBOARDIN G STATUS
	 */
	public void setpREF_ONBOARDING_STATUS(boolean pREF_ONBOARDING_STATUS) {
		this.pREF_ONBOARDING_STATUS = pREF_ONBOARDING_STATUS;
	}

	/**
	 * Gets the p RE F CERTIFICAT E STATUS.
	 *
	 * @return the p RE F CERTIFICAT E STATUS
	 */
	public CertificateStatus getpREF_CERTIFICATE_STATUS() {
		return pREF_CERTIFICATE_STATUS;
	}

	/**
	 * Sets the p RE F CERTIFICAT E STATUS.
	 *
	 * @param pREF_CERTIFICATE_STATUS
	 *            the new p RE F CERTIFICAT E STATUS
	 */
	public void setpREF_CERTIFICATE_STATUS(CertificateStatus pREF_CERTIFICATE_STATUS) {
		this.pREF_CERTIFICATE_STATUS = pREF_CERTIFICATE_STATUS;
	}

	/**
	 * Gets the p RE F ONBOARDIN G APPROVA L STATUS.
	 *
	 * @return the p RE F ONBOARDIN G APPROVA L STATUS
	 */
	public OnboardingApprovalStatus getpREF_ONBOARDING_APPROVAL_STATUS() {
		return pREF_ONBOARDING_APPROVAL_STATUS;
	}

	/**
	 * Sets the p RE F ONBOARDIN G APPROVA L STATUS.
	 *
	 * @param pREF_ONBOARDING_APPROVAL_STATUS
	 *            the new p RE F ONBOARDIN G APPROVA L STATUS
	 */
	public void setpREF_ONBOARDING_APPROVAL_STATUS(OnboardingApprovalStatus pREF_ONBOARDING_APPROVAL_STATUS) {
		this.pREF_ONBOARDING_APPROVAL_STATUS = pREF_ONBOARDING_APPROVAL_STATUS;
	}

	/**
	 * Checks if is p RE F CERTIFICAT E REVOK E STATUS.
	 *
	 * @return true, if is p RE F CERTIFICAT E REVOK E STATUS
	 */
	public boolean ispREF_CERTIFICATE_REVOKE_STATUS() {
		return pREF_CERTIFICATE_REVOKE_STATUS;
	}

	/**
	 * Sets the p RE F CERTIFICAT E REVOK E STATUS.
	 *
	 * @param pREF_CERTIFICATE_REVOKE_STATUS
	 *            the new p RE F CERTIFICAT E REVOK E STATUS
	 */
	public void setpREF_CERTIFICATE_REVOKE_STATUS(boolean pREF_CERTIFICATE_REVOKE_STATUS) {
		this.pREF_CERTIFICATE_REVOKE_STATUS = pREF_CERTIFICATE_REVOKE_STATUS;
	}

	/**
	 * Gets the p ROMOTIONA L NOTIFICATION.
	 *
	 * @return the p ROMOTIONA L NOTIFICATION
	 */
	public String getpROMOTIONAL_NOTIFICATION() {
		return pROMOTIONAL_NOTIFICATION;
	}

	/**
	 * Sets the p ROMOTIONA L NOTIFICATION.
	 *
	 * @param pROMOTIONAL_NOTIFICATION
	 *            the new p ROMOTIONA L NOTIFICATION
	 */
	public void setpROMOTIONAL_NOTIFICATION(String pROMOTIONAL_NOTIFICATION) {
		this.pROMOTIONAL_NOTIFICATION = pROMOTIONAL_NOTIFICATION;
	}
}
