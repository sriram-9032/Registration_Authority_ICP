/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberFcmToken;

/**
 * The Interface SubscriberFcmTokenRepoIface.
 */
@Repository
public interface SubscriberFcmTokenRepository extends JpaRepository<SubscriberFcmToken, Integer> {

	/**
	 * Find bysubscriber uid.
	 *
	 * @param subscriberUid the subscriber uid
	 * @return the string
	 */
	@Query("SELECT sft.fcmToken FROM SubscriberFcmToken sft WHERE sft.subscriberUid = ?1")
	String findBysubscriberUid(String subscriberUid);
}
