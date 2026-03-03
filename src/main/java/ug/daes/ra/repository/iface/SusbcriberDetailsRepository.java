package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.SusbcriberDetails;



@Repository
public interface SusbcriberDetailsRepository extends JpaRepository<SusbcriberDetails, String>{

	SusbcriberDetails findBysubscriberUid(String subscriberUniqueId);

	@Query("SELECT s.subscriberUid FROM SusbcriberDetails s WHERE s.mobileNumber = ?1")
	String findBymobileNumber(String mobileNumber);

	@Query("SELECT s.subscriberUid FROM SusbcriberDetails s WHERE s.eMail = ?1")
	String findByemailId(String eMail);
}
