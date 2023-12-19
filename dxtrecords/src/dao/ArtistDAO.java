package dao;

import domain.*;
import database.*;
import utils.*;

import java.sql.*;
import java.util.ArrayList;

public class ArtistDAO {
    public int insert(Artist art) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Artist(name, country, role, socialNetwork) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, art.getName());
            ps.setString(2, art.getCountry());
            ps.setString(3, art.getRole().getRole());
            ps.setString(4, art.getSocialNetwork());

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public Artist read(int id) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Artist WHERE ID = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String artName = rs.getString(2);
                String artCountry = rs.getString(3);

                String roleStr = rs.getString(4);
                Role artRole = getRoleFromString(roleStr);

                String artSocial = rs.getString(5);

                Artist artist = new Artist(id, artName, artCountry, artRole, artSocial);
                return artist;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }

    public ArrayList<Artist> list() {
        ArrayList<Artist> artList = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Artist"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String artName = rs.getString(2);
                String artCountry = rs.getString(3);

                String roleStr = rs.getString(4);
                Role artRole = getRoleFromString(roleStr);

                String artSocial = rs.getString(5);

                Artist artist = new Artist(id, artName, artCountry, artRole, artSocial);
                artList.add(artist);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return artList;
    }

    public int update(Artist art) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Artist SET name = ?, country = ?, role = ?, socialNetwork = ? WHERE ID = ?"
            );
            ps.setString(1, art.getName());
            ps.setString(2, art.getCountry());
            ps.setString(3, art.getRole().getRole());
            ps.setString(4, art.getSocialNetwork());
            ps.setInt(5, art.getID());

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public int delete(int id) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Artist WHERE ID = ?"
            );
            ps.setInt(1, id);

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public Artist getArtistByID(int id) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Artist WHERE ID = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String artName = rs.getString(2);
                String artCountry = rs.getString(3);

                String roleStr = rs.getString(4);
                Role artRole = getRoleFromString(roleStr);

                String artSocial = rs.getString(5);

                Artist artist = new Artist(artName, artCountry, artRole, artSocial);
                return artist;
            }
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }

    public boolean hasMusicAssociation(int id){
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM ArtistMusic WHERE artistID = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                int count = rs.getInt(1);
                return count > 0;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return false;
    }

    private Role getRoleFromString(String roleString) {
        return Utils.getRoleFromString(roleString);
    }
}
