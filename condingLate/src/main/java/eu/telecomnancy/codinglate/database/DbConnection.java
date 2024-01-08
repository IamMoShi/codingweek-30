package eu.telecomnancy.codinglate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection conn = null;

    public static Connection connect() {
        if (conn != null)
            return conn;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:CodingWeekDatabase.db");
            System.out.println("Connection to SQLite has been established.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e+"");
        }

        return conn;
    }

}
