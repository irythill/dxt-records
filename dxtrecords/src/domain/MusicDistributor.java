package domain;

public class MusicDistributor {
    private String musicISRC;
    private String distributorCID;

    public MusicDistributor(String musicISRC, String distributorCID) {
        this.musicISRC = musicISRC;
        this.distributorCID = distributorCID;
    }

    public String getMusicISRC() {
        return musicISRC;
    }

    public void setMusicISRC(String musicISRC) {
        this.musicISRC = musicISRC;
    }

    public String getDistributorCID() {
        return distributorCID;
    }

    public void setDistributorCID(String distributorCID) {
        this.distributorCID = distributorCID;
    }
}
