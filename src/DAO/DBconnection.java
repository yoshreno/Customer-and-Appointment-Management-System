package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This is the DAO class to handle DB connection.*/
public abstract class DBconnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    /** This field holds the reference to Connection object.*/
    public static Connection connection;  // Connection Interface

    /** This method opens connection to DB and sets it to this class's connection reference.
     * @return Connection object*/
    public static Connection openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /** This method returns this class's connection reference.
     * @return Connection object*/
    public static Connection getConnection() {
        return connection;
    }

    /** This method closes connection to DB.*/
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
