package domain;

public enum Genre {
    ROCK(0L, "Rock"),
    POP(1L, "Pop"),
    HIP_HOP(2L, "Hip-hop"),
    JAZZ(3L, "Jazz"),
    ELECTRONIC(4L, "Electronic"),
    COUNTRY(5L, "Country"),
    RNB(6L, "R&B"),
    REGGAE(7L, "Reggae"),
    CLASSIC(8L, "Classic"),
    FUNK(9L, "Funk");

    private final Long ID;
    private final String genre;

    Genre(Long ID, String genre) {
        this.ID = ID;
        this.genre = genre;
    }

    public Long getID() {
        return ID;
    }

    public String getGenre() {
        return genre;
    }
}
