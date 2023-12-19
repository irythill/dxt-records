package domain;

public class ArtistMusic {
    private int artistID;
    private String musicISRC;

    public ArtistMusic(int arMusID, String arMusISRC) {
        this.artistID = arMusID;
        this.musicISRC = arMusISRC;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public String getMusicISRC() {
        return musicISRC;
    }

    public void setMusicISRC(String musicISRC) {
        this.musicISRC = musicISRC;
    }
}
