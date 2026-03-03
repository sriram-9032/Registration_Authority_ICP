/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberCertificatePinHistory;

/**
 * The Interface SubscriberCertificateDataRepository.
 */
@Repository
public interface SubscriberCertificatePinHistoryRepository
		extends JpaRepository<SubscriberCertificatePinHistory, Integer> {

	/**
	 * Find bysubscriber certificate data id.
	 *
	 * @param subscriberUniqueId
	 *            the subscriber unique id
	 * @return the subscriber certificate pin history
	 */
	SubscriberCertificatePinHistory findBysubscriberUniqueId(String subscriberUniqueId);
}
