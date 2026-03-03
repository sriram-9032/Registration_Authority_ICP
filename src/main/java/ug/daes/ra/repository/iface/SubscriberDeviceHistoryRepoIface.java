package ug.daes.ra.repository.iface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberDeviceHistory;


@Repository
public interface SubscriberDeviceHistoryRepoIface extends JpaRepository<SubscriberDeviceHistory, Integer>{
    @Query("SELECT s FROM SubscriberDeviceHistory s WHERE s.deviceUid = :deviceId ORDER BY s.updatedDate DESC")
    SubscriberDeviceHistory findBydeviceUid(@Param("deviceId") String deviceId);

    @Query("SELECT s FROM SubscriberDeviceHistory s WHERE s.deviceUid = :deviceUid AND s.subscriberUid = :subscriberUid")
    List<SubscriberDeviceHistory> findByDeviceUidAndSubscriberUid(@Param("deviceUid") String deviceUid, @Param("subscriberUid") String subscriberUid);



}
