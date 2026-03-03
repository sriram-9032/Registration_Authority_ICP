package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberCompleteDetailsForAssisted;

@Repository
public interface SubscriberCompleteDetailsForAssistedRepo extends JpaRepository<SubscriberCompleteDetailsForAssisted, String> {
	
	/**
	 * Find by subscriber unique id.
	 *
	 * @param subscriberUniqueId
	 *            the subscriber unique id
	 * @return the subscriber complete details
	 */
	SubscriberCompleteDetailsForAssisted findBysubscriberUid(String subscriberUniqueId);

}
