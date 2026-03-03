package ug.daes.ra.dto;

public class SubscriberCertForAgentDto {

    private String subscriberCertificate;

    private String wrappedKey;

    private String organizationCertificate;

    private String orgWrappedKey;

    public String getSubscriberCertificate() {
        return subscriberCertificate;
    }

    public void setSubscriberCertificate(String subscriberCertificate) {
        this.subscriberCertificate = subscriberCertificate;
    }

    public String getWrappedKey() {
        return wrappedKey;
    }

    public String getOrganizationCertificate() {
        return organizationCertificate;
    }

    public void setOrganizationCertificate(String organizationCertificate) {
        this.organizationCertificate = organizationCertificate;
    }

    public String getOrgWrappedKey() {
        return orgWrappedKey;
    }

    public void setOrgWrappedKey(String orgWrappedKey) {
        this.orgWrappedKey = orgWrappedKey;
    }

    public void setWrappedKey(String wrappedKey) {
        this.wrappedKey = wrappedKey;
    }

    @Override
    public String toString() {
        return "SubscriberCertForAgentDto{" +
                "subscriberCertificate='" + subscriberCertificate + '\'' +
                ", wrappedKey='" + wrappedKey + '\'' +
                ", organizationCertificate='" + organizationCertificate + '\'' +
                ", orgWrappedKey='" + orgWrappedKey + '\'' +
                '}';
    }
}
