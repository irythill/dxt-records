package domain;

public class Music {
    private String ISRC;
    private String name;
    private Genre genre;
    private String duration;
    private String releaseDate;

    public Music(String ISRC, String name, Genre genre, String duration, String date) {
        this.ISRC = ISRC;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.releaseDate = date;
    }

    public String getISRC() {
        return ISRC;
    }

    public void setISRC(String ISRC) {
        this.ISRC = ISRC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
