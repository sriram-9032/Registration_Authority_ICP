package ug.daes.ra.request.entity;

public class AgentCert {
    private String id;
    private String certType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    @Override
    public String toString() {
        return "AgentCert{" +
                "id='" + id + '\'' +
                ", certTypre='" + certType + '\'' +
                '}';
    }
}
