/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberDevice;

import java.util.List;


/**
 * The Interface SubscriberDeviceRepoIface.
 */
@Repository
public interface SubscriberDeviceRepository extends JpaRepository<SubscriberDevice, Integer> {

	@Query("SELECT sd FROM SubscriberDevice sd WHERE sd.deviceUid = ?1 ORDER BY sd.updatedDate DESC")
	List<SubscriberDevice> findBydeviceUid(String deviceId);

	SubscriberDevice findBysubscriberUid(String subscriberUid);

	@Query("SELECT sd FROM SubscriberDevice sd WHERE sd.deviceUid = ?1 AND sd.deviceStatus = ?2")
	SubscriberDevice findBydeviceUidAndStatus(String deviceId, String status);

	@Query("SELECT sd FROM SubscriberDevice sd WHERE sd.subscriberUid = ?1 AND sd.updatedDate = " +
			"(SELECT MAX(s.updatedDate) FROM SubscriberDevice s WHERE s.subscriberUid = ?1)")
	SubscriberDevice getSubscriber(String suid);

}
