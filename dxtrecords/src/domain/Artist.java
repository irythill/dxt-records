package domain;

public class Artist {
    private int ID;
    private String name;
    private String country;
    private Role role;
    private String socialNetwork;

    public Artist(int ID, String name, String country, Role role, String socialNetwork) {
        this.ID = ID;
        this.name = name;
        this.country = country;
        this.role = role;
        this.socialNetwork = socialNetwork;
    }

    public Artist(String name, String country, Role role, String socialNetwork) {
        this.name = name;
        this.country = country;
        this.role = role;
        this.socialNetwork = socialNetwork;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
}
