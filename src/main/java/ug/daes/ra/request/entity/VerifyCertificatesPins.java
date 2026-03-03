/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

/**
 * The Class VerifyCertificatesPins.
 */
public class VerifyCertificatesPins {

	/** The subscriber U id. */
	private String subscriberUId;

	/** The signing pin. */
	private String signingPin;

	/** The auth pin. */
	private String authPin;

	/** The signing certificate pin. */
	private String signingPassword;

	/** The signing certificate pin. */
	private String currentSigningPassword;

	/** The signing wrapped key. */
	private String wrappedKey;

	/**
	 * Gets the subscriber U id.
	 *
	 * @return the subscriber U id
	 */
	public String getSubscriberUId() {
		return subscriberUId;
	}

	/**
	 * Sets the subscriber U id.
	 *
	 * @param subscriberUId
	 *            the new subscriber U id
	 */
	public void setSubscriberUId(String subscriberUId) {
		this.subscriberUId = subscriberUId;
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
	 * @param signingPassword
	 *            the new signing password
	 */
	public void setSigningPassword(String signingPassword) {
		this.signingPassword = signingPassword;
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
	 * @param currentSigningPassword
	 *            the new current signing password
	 */
	public void setCurrentSigningPassword(String currentSigningPassword) {
		this.currentSigningPassword = currentSigningPassword;
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
	 * @param wrappedKey
	 *            the new wrapped key
	 */
	public void setWrappedKey(String wrappedKey) {
		this.wrappedKey = wrappedKey;
	}

	/**
	 * Gets the signing pin.
	 *
	 * @return the signing pin
	 */
	public String getSigningPin() {
		return signingPin;
	}

	/**
	 * Sets the signing pin.
	 *
	 * @param signingPin
	 *            the new signing pin
	 */
	public void setSigningPin(String signingPin) {
		this.signingPin = signingPin;
	}

	/**
	 * Gets the auth pin.
	 *
	 * @return the auth pin
	 */
	public String getAuthPin() {
		return authPin;
	}

	/**
	 * Sets the auth pin.
	 *
	 * @param authPin
	 *            the new auth pin
	 */
	public void setAuthPin(String authPin) {
		this.authPin = authPin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"signingPassword\":\"" + signingPassword + "\"," + "\"currentSigningPassword\":\""
				+ currentSigningPassword + "\"," + "\"wrappedKey\":\"" + wrappedKey + "\"" + "}";
	}

}
