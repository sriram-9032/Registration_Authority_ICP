/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.dto;

/**
 * The Class CertificateDetailsRequest.
 */
public class RARequestDTO {

	/** The geo location. */
	private String geoLocation;


	/** The subscriber unique id. */
	private String subscriberUniqueId;


	/** The reason id. */
	private String reasonId;


	/** The subscriber status. */
	private String subscriberStatus;

	/** The description. */
	private String description;


	private String searchType;

	private String searchValue;
	

	/**
	 * Gets the reason id.
	 *
	 * @return the reason id
	 */
	public String getReasonId() {
		return reasonId;
	}

	/**
	 * Sets the reason id.
	 *
	 * @param reasonId
	 *            the new reason id
	 */
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * Gets the subscriber status.
	 *
	 * @return the subscriber status
	 */
	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	/**
	 * Sets the subscriber status.
	 *
	 * @param subscriberStatus
	 *            the new subscriber status
	 */
	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

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
	 * @param geoLocation
	 *            the new geo location
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
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
	 * @param subscriberUniqueId
	 *            the new subscriber unique id
	 */
	public void setSubscriberUniqueId(String subscriberUniqueId) {
		this.subscriberUniqueId = subscriberUniqueId;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RARequestDTO [geoLocation=" + geoLocation + ", subscriberUniqueId=" + subscriberUniqueId + ", reasonId="
				+ reasonId + ", subscriberStatus=" + subscriberStatus + ", description=" + description + "]";
	}

}
