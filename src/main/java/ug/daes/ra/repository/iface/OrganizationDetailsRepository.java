package ug.daes.ra.repository.iface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.OrganizationDetails;

@Repository
public interface OrganizationDetailsRepository extends JpaRepository<OrganizationDetails, Integer> {

	OrganizationDetails findByOrganizationUid(String paramString);


	@Query("SELECT o FROM OrganizationDetails o WHERE o.organizationUid = ?1")
	List<OrganizationDetails> findOrganizationByUid(String paramString);

	@Query("SELECT o FROM OrganizationDetails o WHERE o.organizationName LIKE CONCAT('%', :name, '%')")
	List<OrganizationDetails> getOrganizationByName(@Param("name") String name);


	@Query("SELECT o FROM OrganizationDetails o WHERE o.organizationName = ?1")
	List<OrganizationDetails> getOrgnizationDetails(String organizationName);

	@Query("SELECT COUNT(o) FROM OrganizationDetails o WHERE o.organizationName = ?1")
	int isOrgnizationExist(String orgName);




}
