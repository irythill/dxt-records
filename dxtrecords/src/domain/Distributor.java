package domain;

public class Distributor {

    private String CID;
    private String tradeName;
    private Double price;
    private String streamingService;

    public Distributor(String CID, String tradeName, Double price, String distributionDate) {
        this.CID = CID;
        this.tradeName = tradeName;
        this.price = price;
        this.streamingService = distributionDate;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(String streamingService) {
        this.streamingService = streamingService;
    }
}
