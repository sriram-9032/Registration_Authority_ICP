/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;


/**
 * The Class GenerateSignature.
 */
public class GenerateSignature {
	/** The cert type. */
	private int certType;

	/** The subscriber unique id. */
	private String subscriberUniqueId;

	/** The signing password. */
	private String signingPassword;

	/** The current signing password. */
	private String currentSigningPassword;

	/** The current PIN. */
	private String currentPIN;

	/** The hash. */
	private String hash;

	/** The hash data. */
	private boolean hashData;

	// Internal//

	/** The key id. */
	private String keyId;

	/** The certificate. */
	private String certificate;

	/** The serial number. */
	private String serialNumber;

	/** The certificate. */
	private String wrappedKey;

	private String correlationId;

	/**
	 * Gets the cert type.
	 *
	 * @return the cert type
	 */
	public int getCertType() {
		return certType;
	}

	/**
	 * Sets the cert type.
	 *
	 * @param certType the new cert type
	 */
	public void setCertType(int certType) {
		this.certType = certType;
	}

	/**
	 * Gets the signing password.
	 *
	 * @return the signing password
	 */
	public String getSigningPassword() {
		return signingPassword;
	}

	/**
	 * Sets the signing password.
	 *
	 * @param signingPassword the new signing password
	 */
	public void setSigningPassword(String signingPassword) {
		this.signingPassword = signingPassword;
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
	 * @param hash the new hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * Checks if is hash data.
	 *
	 * @return true, if is hash data
	 */
	public boolean isHashData() {
		return hashData;
	}

	/**
	 * Sets the hash data.
	 *
	 * @param hashData the new hash data
	 */
	public void setHashData(boolean hashData) {
		this.hashData = hashData;
	}

	/**
	 * Gets the key id.
	 *
	 * @return the key id
	 */
	public String getKeyId() {
		return keyId;
	}

	/**
	 * Sets the key id.
	 *
	 * @param keyId the new key id
	 */
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	/**
	 * Gets the wrapped key.
	 *
	 * @return the wrapped key
	 */
	public String getWrappedKey() {
		return wrappedKey;
	}

	/**
	 * Sets the wrapped key.
	 *
	 * @param wrappedKey the new wrapped key
	 */
	public void setWrappedKey(String wrappedKey) {
		this.wrappedKey = wrappedKey;
	}

	/**
	 * Gets the subscriber unique id.
	 *
	 * @return the subscriber unique id
	 */
	public String getSubscriberUniqueId() {
		return subscriberUniqueId;
	}

	/**
	 * Sets the subscriber unique id.
	 *
	 * @param subscriberUniqueId the new subscriber unique id
	 */
	public void setSubscriberUniqueId(String subscriberUniqueId) {
		this.subscriberUniqueId = subscriberUniqueId;
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
	 * @param certificate the new certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Gets the current signing password.
	 *
	 * @return the current signing password
	 */
	public String getCurrentSigningPassword() {
		return currentSigningPassword;
	}

	/**
	 * Sets the current signing password.
	 *
	 * @param currentSigningPassword the new current signing password
	 */
	public void setCurrentSigningPassword(String currentSigningPassword) {
		this.currentSigningPassword = currentSigningPassword;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the generate signature data.
	 *
	 * @return the generate signature data
	 */
	public String getGenerateSignatureData() {
		return "{\"subscriberDigitalID\":\"" + subscriberUniqueId + "\",\"keyID\":\"" + keyId
				+ "\",\"hash\":\"" + hash + "\",\"hash_data\":" + hashData + ",\"wrappedKey\":\""
				+ wrappedKey + "\",\"currentPIN\":\"" + currentPIN + "\",\"certificate\":\""
				+ certificate + "\",\"serial_number\":\"" + serialNumber + "\",\"currentSigningPassword\":\""
				+ currentSigningPassword + "\",\"signingPassword\":\"" + signingPassword
				+ "\",\"correlationId\":\"" + correlationId + "\"}";
	}

//	public String getGenerateSignatureData() {
//		return "{" + "\"subscriberDigitalID\"" + ":" + "\"" + subscriberUniqueId + "\"," + "\"keyID\"" + ":" + "\""
//				+ keyId + "\"," + "\"hash\"" + ":" + "\"" + hash + "\"," + "\"hash_data\"" + ":" + hashData + ","
//				+ "\"wrappedKey\"" + ":" + "\"" + wrappedKey + "\"," + "\"currentPIN\"" + ":" + "\"" + currentPIN
//				+ "\"," + "\"certificate\"" + ":" + "\"" + certificate + "\"," + "\"serial_number\"" + ":" + "\""
//				+ serialNumber + "\"," + "\"currentSigningPassword\"" + ":" + "\"" + currentSigningPassword + "\","
//				+ "\"signingPassword\"" + ":" + "\"" + signingPassword + "\"" + "\"correlationId\"" + ":" + "\""
//				+ correlationId + "\"" + "}";
//	}

	@Override
	public String toString() {
		return "GenerateSignature [certType=" + certType + ", subscriberUniqueId=" + subscriberUniqueId
				+ ", signingPassword=" + signingPassword + ", currentSigningPassword=" + currentSigningPassword
				+ ", currentPIN=" + currentPIN + ", hash=" + hash + ", hashData=" + hashData + ", keyId=" + keyId
				+ ", certificate=" + certificate + ", serialNumber=" + serialNumber + ", wrappedKey=" + wrappedKey
				+ ", correlationId=" + correlationId + "]";
	}

	// @Override
	// public String toString() {
	// return "GenerateSignature [certType=" + certType + ", subscriberUniqueId=" +
	// subscriberUniqueId
	// + ", signingPassword=" + signingPassword + ", currentSigningPassword=" +
	// currentSigningPassword
	// + ", currentPIN=" + currentPIN + ", hash=" + hash + ", hashData=" + hashData
	// + ", keyId=" + keyId
	// + ", certificate=" + certificate + ", serialNumber=" + serialNumber + ",
	// wrappedKey=" + wrappedKey
	// + "]";
	// }

}
