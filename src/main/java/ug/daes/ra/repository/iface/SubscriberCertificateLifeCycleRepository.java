/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberCertificateLifeCycle;

/**
 * The Interface SubscriberCertificateDataRepository.
 */
@Repository
public interface SubscriberCertificateLifeCycleRepository
		extends JpaRepository<SubscriberCertificateLifeCycle, Integer> {

	/**
	 * Find by subscriber unique id.
	 *
	 * @param subscriberUniqueId
	 *            the subscriber unique id
	 * @return the list
	 */
	List<SubscriberCertificateLifeCycle> findBysubscriberUniqueId(String subscriberUniqueId);
	
	List<SubscriberCertificateLifeCycle> findBySubscriberUniqueIdAndCertificateStatus(String subscriberUniqueId , String status);
}
