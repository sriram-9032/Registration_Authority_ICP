package ug.daes.ra.dto;

import java.math.BigInteger;

public class CertificateCountDTO {

    private BigInteger activeCertificates;
    private BigInteger revokeCertificates;
    private BigInteger expiredCertificates;
    private BigInteger totalCertificates;

    public CertificateCountDTO() {}

    public CertificateCountDTO(
            BigInteger activeCertificates,
            BigInteger revokeCertificates,
            BigInteger expiredCertificates,
            BigInteger totalCertificates) {

        this.activeCertificates = activeCertificates;
        this.revokeCertificates = revokeCertificates;
        this.expiredCertificates = expiredCertificates;
        this.totalCertificates = totalCertificates;
    }

    public BigInteger getActiveCertificates() {
        return activeCertificates;
    }

    public void setActiveCertificates(BigInteger activeCertificates) {
        this.activeCertificates = activeCertificates;
    }

    public BigInteger getRevokeCertificates() {
        return revokeCertificates;
    }

    public void setRevokeCertificates(BigInteger revokeCertificates) {
        this.revokeCertificates = revokeCertificates;
    }

    public BigInteger getExpiredCertificates() {
        return expiredCertificates;
    }

    public void setExpiredCertificates(BigInteger expiredCertificates) {
        this.expiredCertificates = expiredCertificates;
    }

    public BigInteger getTotalCertificates() {
        return totalCertificates;
    }

    public void setTotalCertificates(BigInteger totalCertificates) {
        this.totalCertificates = totalCertificates;
    }

    @Override
    public String toString() {
        return "CertificateCountDTO{" +
                "activeCertificates=" + activeCertificates +
                ", revokeCertificates=" + revokeCertificates +
                ", expiredCertificates=" + expiredCertificates +
                ", totalCertificates=" + totalCertificates +
                '}';
    }
}
