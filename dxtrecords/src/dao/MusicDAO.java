package dao;

import domain.*;
import database.*;
import utils.*;

import java.sql.*;
import java.util.ArrayList;

public class MusicDAO {
    public int insert(Music mus) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Music(ISRC, name, genre, duration, releaseDate) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mus.getISRC());
            ps.setString(2, mus.getName());
            ps.setString(3, mus.getGenre().getGenre());
            ps.setString(4, mus.getDuration());
            ps.setString(5, mus.getReleaseDate());

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public Music read(String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Music WHERE ISRC = ?"
            );
            ps.setString(1, isrc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String musName = rs.getString(2);

                String genreStr = rs.getString(3);
                Genre musGenre = getGenreFromString(genreStr);

                String musDuration = rs.getString(4);
                String musRelease = rs.getString(5);

                Music music = new Music(isrc, musName, musGenre, musDuration, musRelease);
                return music;
            }
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }

    public ArrayList<Music> list() {
        ArrayList<Music> musList = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Music"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String isrc = rs.getString(1);
                String musName = rs.getString(2);

                String genreStr = rs.getString(3);
                Genre musGenre = getGenreFromString(genreStr);

                String musDuration = rs.getString(4);
                String musRelease = rs.getString(5);

                Music music = new Music(isrc, musName, musGenre, musDuration, musRelease);
                musList.add(music);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return musList;
    }

    public int update(Music mus) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Music SET name = ?, genre = ?, duration = ?, releaseDate = ? WHERE ISRC = ?"
            );
            ps.setString(1, mus.getName());
            ps.setString(2, mus.getGenre().getGenre());
            ps.setString(3, mus.getDuration());
            ps.setString(4, mus.getReleaseDate());
            ps.setString(5, mus.getISRC());

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public int delete(String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Music WHERE ISRC = ?"
            );
            ps.setString(1, isrc);

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public boolean hasArtistAssociation(String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM ArtistMusic WHERE musicISRC = ?"
            );
            ps.setString(1, isrc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return false;
    }

    public Music getMusicByISRC(String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Music WHERE ISRC = ?"
            );
            ps.setString(1, isrc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String musName = rs.getString(2);

                String genreStr = rs.getString(3);
                Genre musGenre = getGenreFromString(genreStr);

                String musDuration = rs.getString(4);
                String musRelease = rs.getString(5);

                Music music = new Music(isrc, musName, musGenre, musDuration, musRelease);
                return music;
            }
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }

    private Genre getGenreFromString(String genreString) {
        return Utils.getGenreFromString(genreString);
    }
}
