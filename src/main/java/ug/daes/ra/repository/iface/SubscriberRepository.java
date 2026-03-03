/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.repository.iface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.Subscriber;

/**
 * The Interface SubscriberRepoIface.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

	Subscriber findBysubscriberUid(String subscriberUniqueId);

	@Query("SELECT s.subscriberUid FROM Subscriber s WHERE s.idDocType = ?1 AND s.idDocNumber = ?2")
	String findByidDocTypeAndidDocNumber(String searchType, String idDocNumber);

	@Query("SELECT s.subscriberUid FROM Subscriber s WHERE s.mobileNumber = ?1")
	String findBymobileNumber(String mobileNumber);

	@Query("SELECT s.subscriberUid FROM Subscriber s WHERE s.emailId = ?1")
	String findByemailId(String emailId);

	// Assumes a mapped entity named SubscriberCompleteDetails for the view/table
	@Query("SELECT s.mobileNumber FROM SubscriberCompleteDetails s WHERE s.mobileNumber LIKE %?1%")
	List<String> getSubscriberListByMobileNo(String mobileNo);

	@Query("SELECT s.eMail FROM SubscriberCompleteDetails s WHERE s.eMail LIKE CONCAT('%', :email, '%')")
	List<String> getSubscriberListByEmailId(@Param("email") String email);

	@Query("SELECT s.idDocNumber FROM SubscriberCompleteDetails s WHERE s.idDocType = ?1 AND s.idDocNumber LIKE %?2%")
	List<String> getSubscriberListByDocTypeAndDocNumber(String docType, String idDocNumber);

	@Query("SELECT s FROM Subscriber s WHERE s.emailId = ?1 ORDER BY s.emailId DESC")
	List<Subscriber> getbyEmailId(String email);  // limit 1 can't be done in JPQL directly, so fetch List and pick 1 in code
	@Query("SELECT s FROM Subscriber s WHERE s.mobileNumber = ?1 ORDER BY s.createdDate DESC")
	Subscriber findLatestByMobileNo(String mobileNumber);

//	@Query("SELECT s FROM Subscriber s WHERE s.mobileNumber = ?1 ORDER BY s.mobileNumber DESC")
//	List<Subscriber> getbyMobileNo(String mobNo);
//
// same as above

//	@Query("SELECT s FROM Subscriber s WHERE s.mobileNumber = ?1")
//	Subscriber getbyMobileNo(String mobNo);


}
