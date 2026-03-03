package ug.daes.ra.dto;

public class CountResponseDTO
{
    private SubscriberCountDTO subscriberCount;
    private CertificateCountDTO certificateCount;

    public CountResponseDTO() {}

    public SubscriberCountDTO getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(SubscriberCountDTO subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public CertificateCountDTO getCertificateCount() {
        return certificateCount;
    }

    public void setCertificateCount(CertificateCountDTO certificateCount) {
        this.certificateCount = certificateCount;
    }

    @Override
    public String toString() {
        return "CountResponseDTO{" +
                "subscriberCount=" + subscriberCount +
                ", certificateCount=" + certificateCount +
                '}';
    }
}
