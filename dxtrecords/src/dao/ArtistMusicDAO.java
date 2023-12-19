package dao;

import domain.*;
import database.*;
import utils.*;

import java.sql.*;
import java.util.ArrayList;

public class ArtistMusicDAO {
    public int linkArtistToMusic(int id, String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ArtistMusic(artistID, musicISRC) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, id);
            ps.setString(2, isrc);
            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public void unlinkArtistToMusic(int id, String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM ArtistMusic WHERE artistID = ? AND musicISRC = ?"
            );
            ps.setInt(1, id);
            ps.setString(2, isrc);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
    }

    public boolean isArtistLinkedToMusic(int id, String isrc) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM ArtistMusic WHERE artistID = ? AND musicISRC = ?"
            );
            ps.setInt(1, id);
            ps.setString(2, isrc);
            ResultSet rs = ps.executeQuery();

            boolean result = rs.next();

            conn.close();

            return result;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
            return false;
        }
    }

    public ArrayList<Artist> searchArtistByMusic(String isrc) {
        ArrayList<Artist> artists = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Artist.*, ArtistMusic.musicISRC FROM ArtistMusic " +
                            "JOIN Artist ON ArtistMusic.artistID = Artist.ID WHERE ArtistMusic.musicISRC = ?"
            );
            ps.setString(1, isrc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int artID = rs.getInt(1);
                String artName = rs.getString(2);
                String artCountry = rs.getString(3);

                String roleStr = rs.getString(4);
                Role artRole = getRoleFromString(roleStr);

                String artSocial = rs.getString(5);

                Artist artist = new Artist(artID, artName, artCountry, artRole, artSocial);
                artists.add(artist);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return artists;
    }

    public ArrayList<Music> searchMusicByArtist(int id) {
        ArrayList<Music> musics = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Music.*, ArtistMusic.artistID FROM ArtistMusic " +
                            "JOIN Music ON ArtistMusic.musicISRC = Music.ISRC WHERE ArtistMusic.artistID = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String musISRC = rs.getString(1);
                String musName = rs.getString(2);

                String genreStr = rs.getString(3);
                Genre musGenre = getGenreFromString(genreStr);

                String musDuration = rs.getString(4);
                String musRelease = rs.getString(5);

                Music music = new Music(musISRC, musName, musGenre, musDuration, musRelease);
                musics.add(music);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return musics;
    }

    public ArrayList<ArtistMusic> list() {
        ArrayList<ArtistMusic> artMusList = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM ArtistMusic"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int artID = rs.getInt(1);
                String musISRC = rs.getString(2);

                ArtistMusic artistMusic = new ArtistMusic(artID, musISRC);
                artMusList.add(artistMusic);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return artMusList;
    }

    private Role getRoleFromString(String roleString) {
        return Utils.getRoleFromString(roleString);
    }

    private Genre getGenreFromString(String genreString) {
        return Utils.getGenreFromString(genreString);
    }
}
