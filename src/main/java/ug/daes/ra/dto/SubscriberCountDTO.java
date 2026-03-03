package ug.daes.ra.dto;

import java.math.BigInteger;

public class SubscriberCountDTO {

    private BigInteger totalSubscribers;
    private BigInteger activeSubscribers;
    private BigInteger inActiveSubscribers;
    private BigInteger disabledSubscribers;
    private BigInteger certificateRevokedSubscribers;
    private BigInteger certificateExpiredSubscribers;

    private BigInteger onboardedSubscribers;

    public SubscriberCountDTO() {}

    public SubscriberCountDTO(
            BigInteger totalSubscribers,
            BigInteger activeSubscribers,
            BigInteger inActiveSubscribers,
            BigInteger disabledSubscribers,
            BigInteger certificateRevokedSubscribers,
            BigInteger certificateExpiredSubscribers,
            BigInteger onboardedSubscribers) {

        this.totalSubscribers = totalSubscribers;
        this.activeSubscribers = activeSubscribers;
        this.inActiveSubscribers = inActiveSubscribers;
        this.disabledSubscribers = disabledSubscribers;
        this.certificateRevokedSubscribers = certificateRevokedSubscribers;
        this.certificateExpiredSubscribers = certificateExpiredSubscribers;
        this.onboardedSubscribers = onboardedSubscribers;
    }

    public BigInteger getTotalSubscribers() {
        return totalSubscribers;
    }

    public void setTotalSubscribers(BigInteger totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public BigInteger getActiveSubscribers() {
        return activeSubscribers;
    }

    public void setActiveSubscribers(BigInteger activeSubscribers) {
        this.activeSubscribers = activeSubscribers;
    }

    public BigInteger getInActiveSubscribers() {
        return inActiveSubscribers;
    }

    public void setInActiveSubscribers(BigInteger inActiveSubscribers) {
        this.inActiveSubscribers = inActiveSubscribers;
    }

    public BigInteger getDisabledSubscribers() {
        return disabledSubscribers;
    }

    public void setDisabledSubscribers(BigInteger disabledSubscribers) {
        this.disabledSubscribers = disabledSubscribers;
    }

    public BigInteger getCertificateRevokedSubscribers() {
        return certificateRevokedSubscribers;
    }

    public void setCertificateRevokedSubscribers(BigInteger certificateRevokedSubscribers) {
        this.certificateRevokedSubscribers = certificateRevokedSubscribers;
    }

    public BigInteger getCertificateExpiredSubscribers() {
        return certificateExpiredSubscribers;
    }

    public void setCertificateExpiredSubscribers(BigInteger certificateExpiredSubscribers) {
        this.certificateExpiredSubscribers = certificateExpiredSubscribers;
    }

    public BigInteger getOnboardedSubscribers() {
        return onboardedSubscribers;
    }

    public void setOnboardedSubscribers(BigInteger onboardedSubscribers) {
        this.onboardedSubscribers = onboardedSubscribers;
    }

    @Override
    public String toString() {
        return "SubscriberCountDTO{" +
                "totalSubscribers=" + totalSubscribers +
                ", activeSubscribers=" + activeSubscribers +
                ", inActiveSubscribers=" + inActiveSubscribers +
                ", disabledSubscribers=" + disabledSubscribers +
                ", certificateRevokedSubscribers=" + certificateRevokedSubscribers +
                ", certificateExpiredSubscribers=" + certificateExpiredSubscribers +
                ", onboardedSubscribers=" + onboardedSubscribers +
                '}';
    }
}
