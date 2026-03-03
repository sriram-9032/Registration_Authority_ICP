package ug.daes.ra.request.entity;

public class CompareFaceRequest {

    private String suId;

    private String storedImage;

    private String capturedImage;

    public String getStoredImage() {
        return storedImage;
    }

    public void setStoredImage(String storedImage) {
        this.storedImage = storedImage;
    }

    public String getCapturedImage() {
        return capturedImage;
    }

    public void setCapturedImage(String capturedImage) {
        this.capturedImage = capturedImage;
    }

    public String getSuId() {
        return suId;
    }

    public void setSuId(String suId) {
        this.suId = suId;
    }

    @Override
    public String toString() {
        return "CompareFaceRequest{" +
                "suId='" + suId + '\'' +
                ", storedImage='" + storedImage + '\'' +
                ", capturedImage='" + capturedImage + '\'' +
                '}';
    }

    public String toStringForNative() {
        return "{"
                + "\"storedImage\"" + ":" + "\"" + storedImage + "\","
                + "\"capturedImage\"" + ":" + "\"" + capturedImage + "\"" +
                "}";
    }
}
