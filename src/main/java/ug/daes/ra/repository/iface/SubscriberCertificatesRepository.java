package ug.daes.ra.repository.iface;


import java.util.List;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SubscriberCertificates;

@Repository
public interface SubscriberCertificatesRepository extends JpaRepository<SubscriberCertificates, String> {


	List<SubscriberCertificates> findBysubscriberUniqueId(String subscriberRADataId);

	Optional<SubscriberCertificates> findBycertificateSerialNumber(String serialNumber);

	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.certificateStatus = ?1 AND sc.subscriberUniqueId = ?2")
	List<SubscriberCertificates> findByCertificateStatusAndsubscriberUniqueId(String certificateStatus,
																			  String subscriberUniqueId);

	Optional<SubscriberCertificates> findFirstBySubscriberUniqueIdOrderByCreationDateDesc(String subscriberUid); // Corrected property name


	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.subscriberUniqueId = ?1 ORDER BY sc.creationDate DESC") // Corrected property name
	Optional<SubscriberCertificates> findLatestBySubscriberUniqueIdJPQL(String subscriberUid);


	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.certificateStatus = ?1")
	List<SubscriberCertificates> findByCertificateStatus(String certificateStatus);

	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.certificateStatus = ?1 AND sc.subscriberUniqueId = ?2 AND sc.certificateType = ?3")
	SubscriberCertificates findByCertificateStatusAndsubscriberUniqueIdAndCertificateType(String certificateStatus,
																						  String subscriberUniqueId, String certificateType);


	@Query("SELECT COUNT(sc) FROM SubscriberCertificates sc")
	int getAllCertificateCount();


	@Query("SELECT COUNT(sc.certificateType) FROM SubscriberCertificates sc WHERE sc.certificateStatus = 'ACTIVE'")
	int getIssuedCertificatesCount();


	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.certificateEndDate <= CURRENT_TIMESTAMP AND sc.certificateStatus = 'ACTIVE'")
	List<SubscriberCertificates> findByCertificateStatusExpired();

	@Query("""
    SELECT 
        scv.subscriberCount,
        scv.activeSubscriberCount,
        scv.inactiveSubscriberCount,
        scv.disableSubscriberCount,
        scv.certRevokeSubscriberCount,
        scv.certExpiredSubscriberCount,
        scv.onboardedSubscriberCount,
        ccv.activeCertCount,
        ccv.revokeCertCount,
        ccv.expiredCertCount,
        ccv.certCount
    FROM SubscriberCountView scv, CertificateCountView ccv
""")
	Object[] getSubscriberAndCertCount();





	@Query("SELECT sc FROM SubscriberCertificates sc WHERE sc.subscriberUniqueId = ?1")
	List<SubscriberCertificates> findBySubscriberUniqueIdToExpireCert(String subscriberUid);



	SubscriberCertificates findTopBySubscriberUniqueIdOrderByCreationDateDesc(String subscriberUniqueId);
}