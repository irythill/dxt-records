package domain;

public enum Role {
    ARTIST(0L, "Artist"),
    BAND(2L, "Band"),
    PRODUCER(3L, "Producer"),
    SONGWRITER(4L, "Songwriter"),
    MIXING(5L, "Mixing"),
    MASTERING(6L, "Mastering");

    private final Long ID;
    private final String role;

    Role(Long ID, String role) {
        this.ID = ID;
        this.role = role;
    }

    public Long getID() {
        return ID;
    }

    public String getRole() {
        return role;
    }
}
