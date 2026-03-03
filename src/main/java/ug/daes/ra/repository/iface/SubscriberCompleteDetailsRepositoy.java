/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberCompleteDetails;

/**
 * The Interface SubscriberCertificateDataRepository.
 */
@Repository
public interface SubscriberCompleteDetailsRepositoy extends JpaRepository<SubscriberCompleteDetails, String> {

	/**
	 * Find by subscriber unique id.
	 *
	 * @param subscriberUniqueId
	 *            the subscriber unique id
	 * @return the subscriber complete details
	 */
	SubscriberCompleteDetails findBysubscriberUid(String subscriberUniqueId);
	
	
}
