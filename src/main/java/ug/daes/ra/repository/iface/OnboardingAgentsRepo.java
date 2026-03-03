package ug.daes.ra.repository.iface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.OnboardingAgents;

@Repository
public interface OnboardingAgentsRepo extends JpaRepository<OnboardingAgents,Integer> {

	@Query("SELECT o FROM OnboardingAgents o WHERE o.deviceId = ?1 ORDER BY o.updatedOn DESC")
	List<OnboardingAgents> findByAgentdeviceUid(String deviceId);

}
