package dao;

import domain.*;
import database.*;

import java.sql.*;
import java.util.ArrayList;

public class DistributorDAO {
    public int insert(Distributor dis) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Distributor(CID, tradeName, price, streamingService) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dis.getCID());
            ps.setString(2, dis.getTradeName());
            ps.setDouble(3, dis.getPrice());
            ps.setString(4, dis.getStreamingService());

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public Distributor read(String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Distributor WHERE CID = ?"
            );
            ps.setString(1, cid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String disName = rs.getString(2);
                Double disPrice = rs.getDouble(3);
                String disDistribution = rs.getString(4);

                Distributor distributor = new Distributor(cid, disName, disPrice, disDistribution);
                return distributor;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }

    public ArrayList<Distributor> list() {
        ArrayList<Distributor> disList = new ArrayList<>();

        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Distributor"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cid = rs.getString(1);
                String disName = rs.getString(2);
                Double disPrice = rs.getDouble(3);
                String disService = rs.getString(4);

                Distributor distributor = new Distributor(cid, disName, disPrice, disService);
                disList.add(distributor);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return disList;
    }

    public int update(Distributor dis) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Distributor SET tradeName = ?, price = ?, streamingService = ? WHERE CID = ?"
            );
            ps.setString(1, dis.getTradeName());
            ps.setDouble(2, dis.getPrice());
            ps.setString(3, dis.getStreamingService());
            ps.setString(4, dis.getCID());


            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public int delete(String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Distributor WHERE CID = ?"
            );
            ps.setString(1, cid);

            int rowCount = ps.executeUpdate();
            conn.close();

            return rowCount;
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return 0;
    }

    public boolean hasMusicDistributed(String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM MusicDistributor WHERE distributorCID = ?"
            );
            ps.setString(1, cid);
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


    public Distributor getDistributorByCID(String cid) {
        try {
            Connection conn = DbConn.getConnectionSQL();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Distributor WHERE CID = ?"
            );
            ps.setString(1, cid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String disName = rs.getString(2);
                Double disPrice = rs.getDouble(3);
                String disService = rs.getString(4);

                Distributor distributor = new Distributor(cid, disName, disPrice, disService);
                return distributor;
            }
        } catch (SQLException e) {
            System.out.println("Failed. Error: " + e);
        }
        return null;
    }
}
