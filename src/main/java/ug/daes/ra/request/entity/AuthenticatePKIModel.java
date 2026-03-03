/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

/**
 * The Class AuthenticatePKIModel.
 */
public class AuthenticatePKIModel {

	/** The cert type. */
	private int certType;

	/** The subscriber unique id. */
	private String subscriberUniqueId;

	/** The hash data. */
	private boolean hashData;

	/** The hash. */
	private String random;

	/** The service name. */
	private String signature;
	// Internal //
	/** The key id. */
	private String keyId;

	/** The certificate. */
	private String certificate;

	/** The correlationId. */
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
	 * Gets the random.
	 *
	 * @return the random
	 */
	public String getRandom() {
		return random;
	}

	/**
	 * Sets the random.
	 *
	 * @param random the new random
	 */
	public void setRandom(String random) {
		this.random = random;
	}

	/**
	 * Gets the signature.
	 *
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Sets the signature.
	 *
	 * @param signature the new signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the authenticate PKI data.
	 *
	 * @return the authenticate PKI data
	 */
	public String getauthenticatePKIData() {
		return "{\"signature\":\"" + signature + "\",\"random\":\"" + random + "\",\"hash_data\":"
				+ hashData + ",\"certificate\":\"" + certificate + "\",\"subscriberDigitalID\":\""
				+ subscriberUniqueId + "\",\"keyID\":\"" + keyId + "\",\"correlationId\":\""
				+ correlationId + "\"}";
	}
	
//	public String getauthenticatePKIData() {
//		return "{" + "\"signature\"" + ":" + "\"" + signature + "\"," + "\"random\"" + ":" + "\"" + random + "\","
//				+ "\"hash_data\"" + ":" + hashData + "," + "\"certificate\"" + ":" + "\"" + certificate + "\","
//				+ "\"subscriberDigitalID\"" + ":" + "\"" + subscriberUniqueId + "\"," + "\"keyID\"" + ":" + "\"" + keyId
//				+ "\"" + "\"correlationId\"" + ":" + "\"" + correlationId + "\"" + "}";
//	}

	

	// public String getauthenticatePKIData() {
	// return "{" + "\"signature\"" + ":" + "\"" + signature + "\"," + "\"random\""
	// + ":" + "\"" + random + "\","
	// + "\"hash_data\"" + ":" + hashData + "," + "\"certificate\"" + ":" + "\"" +
	// certificate + "\","
	// + "\"subscriberDigitalID\"" + ":" + "\"" + subscriberUniqueId + "\"," +
	// "\"keyID\"" + ":" + "\"" + keyId
	// + "\"" + "}";
	// }

}
