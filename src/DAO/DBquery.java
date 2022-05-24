package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** This is the DAO class to handle PreparedStatement.*/
public class DBquery {

    /** Reference field to hold all PreparedStatement.*/
    private static PreparedStatement preparedStatement;

    /** This method sets the PreparedStatement.*/
    public static void setPreparedStatement(Connection conn, String sql) {
        try {
            preparedStatement = conn.prepareStatement(sql);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method returns the PreparedStatement.
     * @return This class's reference of the PreparedStatement.*/
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
