/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberStatusModel;

/**
 * The Interface SubscriberStatusRepository.
 */
@Repository
public interface SubscriberStatusRepository extends JpaRepository<SubscriberStatusModel, String> {

	SubscriberStatusModel findBysubscriberUid(String subscriberUniqueId);

	@Query("SELECT COUNT(s.subscriberUid) FROM SubscriberStatus s")
	int getSubscriberCount();

	@Query("SELECT COUNT(s.subscriberUid) FROM SubscriberStatus s WHERE s.subscriberStatus = 'ACTIVE'")
	int getActiveSubscriberCount();

	@Query("SELECT COUNT(s.subscriberUid) FROM SubscriberStatus s WHERE s.subscriberStatus = 'DISABLED'")
	int getInActiveSubscriberCount();
}
