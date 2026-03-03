package ug.daes.ra.repository.iface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ug.daes.ra.model.OrganizationWrappedKey;

@Repository
public interface OrganizationWrappedKeyRepository extends JpaRepository<OrganizationWrappedKey, String> {

	OrganizationWrappedKey findBycertificateSerialNumber(String paramString);
}
