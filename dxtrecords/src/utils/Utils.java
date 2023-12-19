package utils;

import domain.Genre;
import domain.Role;

public class Utils {

    public static Role getRoleFromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.toString().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + roleString);
    }

    public static Genre getGenreFromString(String genreString) {
        for (Genre genre : Genre.values()) {
            if (genre.getGenre().equalsIgnoreCase(genreString)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + genreString);
    }
}
