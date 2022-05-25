package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.*;

/** This is the DAO class to handle user data.*/
public class DBuser {

    /** Reference field to hold current user logged in.*/
    public static User currentUser;
    /** Reference field to hold all users.*/
    public static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /** This method gets a list of all users from users DB.
     @return List of all users*/
    public static ObservableList<User> getAllUsers() {
        allUsers.clear();

        String selectStatement = "SELECT * FROM users;";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                allUsers.add(new User(id, name));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    /** This method checks whether supplied credentials are valid or invalid.
     * @param userName Supplied user name
     * @param password Supplied password
     * @return True if valid credentials*/
    public static boolean verifyCredentials(String userName, String password) {
        String selectStatement = "SELECT * FROM users WHERE User_Name = ?";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("Password").equals(password)) {

                    for (User user: DBuser.allUsers) {
                        if (user.getName().equals(userName)) {
                            currentUser = user;
                            break;
                        }
                    }
                    return true;
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
