/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.response.entity;

/**
 * The Class DashboardDetails.
 */
public class DashboardDetails {

	/** The subscriber count. */
	private int subscriberCount;

	/** The active subscriber count. */
	private int activeSubscriberCount;

	/** The in active subscriber count. */
	private int inActiveSubscriberCount;

	/**
	 * Gets the subscriber count.
	 *
	 * @return the subscriber count
	 */
	public int getSubscriberCount() {
		return subscriberCount;
	}

	/**
	 * Sets the subscriber count.
	 *
	 * @param subscriberCount
	 *            the new subscriber count
	 */
	public void setSubscriberCount(int subscriberCount) {
		this.subscriberCount = subscriberCount;
	}

	/**
	 * Gets the active subscriber count.
	 *
	 * @return the active subscriber count
	 */
	public int getActiveSubscriberCount() {
		return activeSubscriberCount;
	}

	/**
	 * Sets the active subscriber count.
	 *
	 * @param activeSubscriberCount
	 *            the new active subscriber count
	 */
	public void setActiveSubscriberCount(int activeSubscriberCount) {
		this.activeSubscriberCount = activeSubscriberCount;
	}

	/**
	 * Gets the in active subscriber count.
	 *
	 * @return the in active subscriber count
	 */
	public int getInActiveSubscriberCount() {
		return inActiveSubscriberCount;
	}

	/**
	 * Sets the in active subscriber count.
	 *
	 * @param inActiveSubscriberCount
	 *            the new in active subscriber count
	 */
	public void setInActiveSubscriberCount(int inActiveSubscriberCount) {
		this.inActiveSubscriberCount = inActiveSubscriberCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"TotalSubscribers\":" + subscriberCount + "," + "\"ActiveSubscribers\":" + activeSubscriberCount
				+ "," + "\"InActiveSubscribers\":" + inActiveSubscriberCount + "" + "}";
	}

}
