package ug.daes.ra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@Entity
@Table(name = "certificate_count_view")
public class CertificateCountView {

    @Id
    private Long id = 1L; // dummy ID

    @Column(name = "cert_count")
    private BigInteger certCount;

    @Column(name = "active_cert_count")
    private BigInteger activeCertCount;

    @Column(name = "revoke_cert_count")
    private BigInteger revokeCertCount;

    @Column(name = "expired_cert_count")
    private BigInteger expiredCertCount;

    // getters/setters
}





