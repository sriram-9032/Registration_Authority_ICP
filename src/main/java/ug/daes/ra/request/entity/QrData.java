package ug.daes.ra.request.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QrData {

    private String publicData;

    private String privateData;

    private String keyHash;

    private String dataHash;

    private String orgId;

    private String signedKeyHash;

    private String signedDataHash;

    private String signature;

    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicData() {
        return publicData;
    }

    public void setPublicData(String publicData) {
        this.publicData = publicData;
    }

    public String getPrivateData() {
        return privateData;
    }

    public void setPrivateData(String privateData) {
        this.privateData = privateData;
    }

    public String getSignedKeyHash() {
        return signedKeyHash;
    }

    public void setSignedKeyHash(String signedKeyHash) {
        this.signedKeyHash = signedKeyHash;
    }

    public String getSignedDataHash() {
        return signedDataHash;
    }

    public void setSignedDataHash(String signedDataHash) {
        this.signedDataHash = signedDataHash;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    @Override
    public String toString() {
        return "QrData{" +
                "publicData='" + publicData + '\'' +
                ", privateData='" + privateData + '\'' +
                ", keyHash='" + keyHash + '\'' +
                ", dataHash='" + dataHash + '\'' +
                ", orgId='" + orgId + '\'' +
                ", signedKeyHash='" + signedKeyHash + '\'' +
                ", signedDataHash='" + signedDataHash + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public String toNative(){
        return "{"
                + "\"publicData\" : \"" + publicData + "\","
                + "\"privateData\" : \"" + privateData + "\","
                + "\"keyHash\" : \"" + keyHash + "\","
                + "\"dataHash\" : \"" + dataHash + "\","
                + "\"orgId\" : \"" + orgId + "\","
                + "\"signedKeyHash\" : \"" + signedKeyHash + "\","
                + "\"signature\" : \"" + signature + "\","
                + "\"signedDataHash\" : \"" + signedDataHash + "\""
                + "}";
    }
}
