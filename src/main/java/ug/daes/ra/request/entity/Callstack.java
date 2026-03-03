/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

/**
 * The Class Callstack.
 */
public class Callstack {

	/** The subscriber digital ID. */
	private String subscriberDigitalID;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The key ID. */
	private String keyID;

	/** The certificate ID. */
	private int certificateID;

	/** The serial number. */
	private String serial_number;

	/** The reason. */
	private String reason;

	/** The certificate. */
	private String certificate;

	/** The common name. */
	private String commonName;

	/** The country name. */
	private String countryName;

	/** The token sign. */
	private String token_sign;

	/** The hash. */
	private String hash;

	/**
	 * Gets the subscriber digital ID.
	 *
	 * @return the subscriber digital ID
	 */
	public String getSubscriberDigitalID() {
		return subscriberDigitalID;
	}

	/**
	 * Sets the subscriber digital ID.
	 *
	 * @param subscriberDigitalID
	 *            the new subscriber digital ID
	 */
	public void setSubscriberDigitalID(String subscriberDigitalID) {
		this.subscriberDigitalID = subscriberDigitalID;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the key ID.
	 *
	 * @return the key ID
	 */
	public String getKeyID() {
		return keyID;
	}

	/**
	 * Sets the key ID.
	 *
	 * @param keyID
	 *            the new key ID
	 */
	public void setKeyID(String keyID) {
		this.keyID = keyID;
	}

	/**
	 * Gets the certificate ID.
	 *
	 * @return the certificate ID
	 */
	public int getCertificateID() {
		return certificateID;
	}

	/**
	 * Sets the certificate ID.
	 *
	 * @param certificateID
	 *            the new certificate ID
	 */
	public void setCertificateID(int certificateID) {
		this.certificateID = certificateID;
	}

	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerial_number() {
		return serial_number;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serial_number
	 *            the new serial number
	 */
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets the reason.
	 *
	 * @param reason
	 *            the new reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Gets the certificate.
	 *
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * Sets the certificate.
	 *
	 * @param certificate
	 *            the new certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * Gets the common name.
	 *
	 * @return the common name
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * Sets the common name.
	 *
	 * @param commonName
	 *            the new common name
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName
	 *            the new country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Gets the token sign.
	 *
	 * @return the token sign
	 */
	public String getToken_sign() {
		return token_sign;
	}

	/**
	 * Sets the token sign.
	 *
	 * @param token_sign
	 *            the new token sign
	 */
	public void setToken_sign(String token_sign) {
		this.token_sign = token_sign;
	}

	/**
	 * Gets the hash.
	 *
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Sets the hash.
	 *
	 * @param hash
	 *            the new hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Callstack [subscriberDigitalID=" + subscriberDigitalID + ", username=" + username + ", password="
				+ password + ", keyID=" + keyID + ", certificateID=" + certificateID + ", serial_number="
				+ serial_number + ", reason=" + reason + ", certificate=" + certificate + ", commonName=" + commonName
				+ ", countryName=" + countryName + "]";
	}

	/**
	 * Gets the issue cert callstack.
	 *
	 * @return the issue cert callstack
	 */
	public String getIssueCertCallstack1() {
		return "{" + "\"subscriberDigitalID\"" + ":" + "\"" + subscriberDigitalID + "\"," + "\"keyID\"" + ":" + "\""
				+ keyID + "\"," + "\"commonName\"" + ":" + "\"" + commonName + "\"," + "\"countryName\"" + ":" + "\""
				+ countryName + "\"" + "}";
	}

	/**
	 * Gets the revoke cert callstack.
	 *
	 * @return the revoke cert callstack
	 */
	public String getRevokeCertCallstack() {
		return "{" + "\"serial_number\"" + ":" + "\"" + serial_number + "\"," + "\"reason\"" + ":" + "\"" + reason
				+ "\"" + "}";
	}

	/**
	 * Gets the certificate status call stack.
	 *
	 * @return the certificate status call stack
	 */
	public String getCertificateStatusCallStack() {
		return "{" + "\"serial_number\"" + ":" + "\"" + serial_number + "\"," + "\"certificate\"" + ":" + "\""
				+ certificate + "\"" + "}";
	}
}
