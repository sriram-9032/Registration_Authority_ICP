/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

/**
 * The Class PushNotificationRequest.
 */
public class PushNotificationRequest {

	/** The title. */
	private String title;

	/** The message. */
	private String message;

	/** The data. */
	private String data;

	/** The topic. */
	private String topic;

	/** The token. */
	private String token;

	/** The priority. */
	private String priority;

	/** The to. */
	private String to;

	/**
	 * Instantiates a new push notification request.
	 */
	public PushNotificationRequest() {
	}

	/**
	 * Sets the topic.
	 *
	 * @param topic
	 *            the new topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the topic.
	 *
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to
	 *            the new to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PushNotificationRequest [title=" + title + ", message=" + message + ", data=" + data + ", topic="
				+ topic + ", token=" + token + ", priority=" + priority + ", to=" + to + "]";
	}

	/**
	 * Gets the notification rquest.
	 *
	 * @return the notification rquest
	 */
	public String getNotificationRquest() {
		return "{" + "\"to\":\"" + to + "\"," + "\"priority\":\"" + priority + "\"," + "\"data\":" + data + "}";
	}
}