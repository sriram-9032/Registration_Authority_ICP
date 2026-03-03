/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;


/**
 * The Class SetPinModel.
 */
public class SetPinModel {
	/** The geo location. */
	private String geoLocation;


	/** The cert type. */
	private int certType;

	/** The reset PIN. */
	private boolean resetPIN;

	/** The change pin. */
	private boolean changePin;

	/** The signing password. */
	private String signingPassword;

	/** The old signing password. */
	private String oldSigningPassword;

	/** The current signing password. */
	private String currentSigningPassword;

	/** The subscriber unique id. */
	private String subscriberUniqueId;

	/** The wrapped key. */
	private String wrappedKey;

	/**
	 * Gets the geo location.
	 *
	 * @return the geo location
	 */
	public String getGeoLocation() {
		return geoLocation;
	}

	/**
	 * Sets the geo location.
	 *
	 * @param geoLocation the new geo location
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

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
	 * Checks if is reset PIN.
	 *
	 * @return true, if is reset PIN
	 */
	public boolean isResetPIN() {
		return resetPIN;
	}

	/**
	 * Sets the reset PIN.
	 *
	 * @param resetPIN the new reset PIN
	 */
	public void setResetPIN(boolean resetPIN) {
		this.resetPIN = resetPIN;
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
	 * Checks if is change pin.
	 *
	 * @return true, if is change pin
	 */
	public boolean isChangePin() {
		return changePin;
	}

	/**
	 * Sets the change pin.
	 *
	 * @param changePin the new change pin
	 */
	public void setChangePin(boolean changePin) {
		this.changePin = changePin;
	}

	/**
	 * Gets the old signing password.
	 *
	 * @return the old signing password
	 */
	public String getOldSigningPassword() {
		return oldSigningPassword;
	}

	/**
	 * Sets the old signing password.
	 *
	 * @param oldSigningPassword the new old signing password
	 */
	public void setOldSigningPassword(String oldSigningPassword) {
		this.oldSigningPassword = oldSigningPassword;
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

	/**
	 * Gets the setting pin.
	 *
	 * @return the setting pin
	 */
	public String getSettingPin() {
		return "{" + "\"wrappedKey\":" + "\"" + wrappedKey + "\"," + "\"resetPIN\":" + resetPIN + ","
				+ "\"signingPassword\":" + "\"" + signingPassword + "\"" + "}";

	}

	/**
	 * Gets the change pin.
	 *
	 * @return the change pin
	 */
	public String getChangePin() {
		return "{" + "\"wrappedKey\":" + "\"" + wrappedKey + "\"," + "\"changePin\":"
				+ changePin + "," + "\"currentSigningPassword\":\"" + currentSigningPassword + "\","
				+ "\"oldSigningPassword\":\"" + oldSigningPassword + "\","
				+ "\"signingPassword\":" + "\""
				+ signingPassword + "\"" + "}";
	}
}
