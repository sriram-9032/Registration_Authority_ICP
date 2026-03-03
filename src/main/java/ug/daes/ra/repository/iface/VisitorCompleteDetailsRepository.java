//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ug.daes.ra.repository.iface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ug.daes.ra.model.VisitorCompleteDetails;

@Repository
public interface VisitorCompleteDetailsRepository extends JpaRepository<VisitorCompleteDetails, String> {
	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.subscriberUid = ?1")
	VisitorCompleteDetails fetchAllVisitors(String suid);

	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.idDocNumber = ?1 AND v.idDocType = ?2")
	VisitorCompleteDetails fetchVisitorByIdocNumber(String idDoc, String type);

	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.eMail = ?1")
	VisitorCompleteDetails fetchVisitorByEmail(String eMail);

	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.mobileNumber = ?1")
	VisitorCompleteDetails fetchVisitorByMobileNumber(String mobileNumber);

	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.subscriberType = ?1")
	List<VisitorCompleteDetails> fetchOnlyVisitors(String type);

	// JPQL-based query can be avoided for derived query
	@Query("SELECT v FROM VisitorCompleteDetails v WHERE v.idDocNumber = ?1")
	VisitorCompleteDetails idDocNumber(String passportNumber);
}
