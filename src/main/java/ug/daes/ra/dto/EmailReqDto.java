package ug.daes.ra.dto;

import java.io.Serializable;

public class EmailReqDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailOtp;
	
	private int ttl;
	
	private String emailId;

	private boolean org;
	
	private boolean userSubscription;

	public boolean isOrg() {
		return org;
	}

	public void setOrg(boolean org) {
		this.org = org;
	}

	public String getEmailOtp() {
		return emailOtp;
	}

	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isUserSubscription() {
		return userSubscription;
	}

	public void setUserSubscription(boolean userSubscription) {
		this.userSubscription = userSubscription;
	}

	@Override
	public String toString() {
		return "EmailReqDto [emailOtp=" + emailOtp + ", ttl=" + ttl + ", emailId=" + emailId + ", org=" + org
				+ ", userSubscription=" + userSubscription + "]";
	}
}
