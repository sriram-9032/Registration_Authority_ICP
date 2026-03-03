package ug.daes.ra.dto;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@NamedStoredProcedureQuery(name = "SubAndCertCountDTO.subscriberAndCertCount",
        procedureName = "subscriber_and_cert_count")
public class SubAndCertCountDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="active_subscriber_count")
    private BigInteger activeSubscriberCount;
    @Column(name="inactive_subscriber_count")
    private BigInteger inactiveSubscriberCount;
    @Column(name="disable_subscriber_count")
    private BigInteger disableSubscriberCount;
    @Column(name="onboarded_subscriber_count")
    private BigInteger onboardedSubscriberCount;
    @Column(name="cert_revoke_subscriber_count")
    private BigInteger certRevokeSubscriberCount;
    @Column(name="cert_expired_subscriber_count")
    private BigInteger certExpiredSubscriberCount;
    @Column(name="subscriber_count")
    private BigInteger subscriberCount;
    @Column(name="revoke_cert_count")
    private BigInteger revokeCertCount;
    @Column(name="active_cert_count")
    private BigInteger activeCertCount;
    @Column(name="expired_cert_count")
    private BigInteger expiredCertCount;
    @Column(name="cert_count")
    private BigInteger certCount;

    public SubAndCertCountDTO() {}

    public BigInteger getActiveSubscriberCount() {
        return activeSubscriberCount;
    }

    public void setActiveSubscriberCount(BigInteger activeSubscriberCount) {
        this.activeSubscriberCount = activeSubscriberCount;
    }

    public BigInteger getInactiveSubscriberCount() {
        return inactiveSubscriberCount;
    }

    public void setInactiveSubscriberCount(BigInteger inactiveSubscriberCount) {
        this.inactiveSubscriberCount = inactiveSubscriberCount;
    }

    public BigInteger getDisableSubscriberCount() {
        return disableSubscriberCount;
    }

    public void setDisableSubscriberCount(BigInteger disableSubscriberCount) {
        this.disableSubscriberCount = disableSubscriberCount;
    }

    public BigInteger getOnboardedSubscriberCount() {
        return onboardedSubscriberCount;
    }

    public void setOnboardedSubscriberCount(BigInteger onboardedSubscriberCount) {
        this.onboardedSubscriberCount = onboardedSubscriberCount;
    }

    public BigInteger getCertRevokeSubscriberCount() {
        return certRevokeSubscriberCount;
    }

    public void setCertRevokeSubscriberCount(BigInteger certRevokeSubscriberCount) {
        this.certRevokeSubscriberCount = certRevokeSubscriberCount;
    }

    public BigInteger getCertExpiredSubscriberCount() {
        return certExpiredSubscriberCount;
    }

    public void setCertExpiredSubscriberCount(BigInteger certExpiredSubscriberCount) {
        this.certExpiredSubscriberCount = certExpiredSubscriberCount;
    }

    public BigInteger getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(BigInteger subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public BigInteger getRevokeCertCount() {
        return revokeCertCount;
    }

    public void setRevokeCertCount(BigInteger revokeCertCount) {
        this.revokeCertCount = revokeCertCount;
    }

    public BigInteger getActiveCertCount() {
        return activeCertCount;
    }

    public void setActiveCertCount(BigInteger activeCertCount) {
        this.activeCertCount = activeCertCount;
    }

    public BigInteger getExpiredCertCount() {
        return expiredCertCount;
    }

    public void setExpiredCertCount(BigInteger expiredCertCount) {
        this.expiredCertCount = expiredCertCount;
    }

    public BigInteger getCertCount() {
        return certCount;
    }

    public void setCertCount(BigInteger certCount) {
        this.certCount = certCount;
    }

    @Override
    public String toString() {
        return "SubAndCertCountDTO{" +
                "activeSubscriberCount=" + activeSubscriberCount +
                ", inactiveSubscriberCount=" + inactiveSubscriberCount +
                ", disableSubscriberCount=" + disableSubscriberCount +
                ", onboardedSubscriberCount=" + onboardedSubscriberCount +
                ", certRevokeSubscriberCount=" + certRevokeSubscriberCount +
                ", certExpiredSubscriberCount=" + certExpiredSubscriberCount +
                ", subscriberCount=" + subscriberCount +
                ", revokeCertCount=" + revokeCertCount +
                ", activeCertCount=" + activeCertCount +
                ", expiredCertCount=" + expiredCertCount +
                ", certCount=" + certCount +
                '}';
    }
}
