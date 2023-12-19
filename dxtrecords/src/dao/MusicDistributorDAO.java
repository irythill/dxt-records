package dao;

import database.DbConn;
import domain.ArtistMusic;
import domain.MusicDistributor;

import java.sql.*;
import java.util.ArrayList;

public class MusicDistributorDAO {
    public int startDistribution(String isrc, String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO MusicDistributor(musicISRC, distributorCID) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, isrc);
            ps.setString(2, cid);
            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public void stopDistribution(String isrc, String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM MusicDistributor WHERE musicISRC = ? AND distributorCID = ?"
            );
            ps.setString(1, isrc);
            ps.setString(2, cid);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
    }

    public boolean isMusicDistributed(String isrc, String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM MusicDistributor WHERE musicISRC = ? AND distributorCID = ?"
            );
            ps.setString(1, isrc);
            ps.setString(2, cid);
            ResultSet rs = ps.executeQuery();

            boolean result = rs.next();

            conn.close();

            return result;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
            return false;
        }
    }


    public ArrayList<MusicDistributor> list() {
        ArrayList<MusicDistributor> musDisList = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM MusicDistributor"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String musISRC = rs.getString(1);
                String disCID = rs.getString(2);

                MusicDistributor musicDistributor = new MusicDistributor(musISRC, disCID);
                musDisList.add(musicDistributor);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return musDisList;
    }
}
