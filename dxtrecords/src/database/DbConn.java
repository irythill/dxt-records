package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {
    public static java.sql.Connection getConnectionSQL() {
        java.sql.Connection conn = null;

        String host = "localhost";
        String database = "dxtrecords";
        String url = "jdbc:mysql://" + host + "/" + database;
        String username = "root";
        String password = "1234";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Failed to connect. Error: " + e);
        }
        return conn;
    }
}
