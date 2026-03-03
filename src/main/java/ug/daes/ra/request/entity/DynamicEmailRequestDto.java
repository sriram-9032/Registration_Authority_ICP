package ug.daes.ra.request.entity;

import java.io.Serializable;
import java.util.List;

public class DynamicEmailRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailBody;
	private List<String> recipients;
    private String subject;
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "DynamicEmailRequestDto [emailBody=" + emailBody + ", recipients=" + recipients + ", subject=" + subject
				+ "]";
	}
}
