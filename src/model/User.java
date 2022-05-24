package model;

import java.sql.Timestamp;

/** This is the model class for User object.*/
public class User {

    private int userId;
    private String userName;
    private String password;
    private Timestamp lastLogIn;

    /** This is the constructor method that sets the attributes for User object.*/
    public User(int id, String name) {
        userId = id;
        userName = name;
    }

    /** This method overrides the toString() method to return User object as string name instead of its memory address.
     * @return Concatenated string of ID and user name */
    @Override
    public String toString() {
        String idAndName = "ID: " + userId + " - " + userName;
        return idAndName;
    }

    /** This method gets user ID.
     * @return User ID */
    public int getUserId() {
        return userId;
    }

    /** This method sets user ID.
     * @param id User ID to set */
    public void setUserId(int id) {
        userId = id;
    }

    /** This method gets user name.
     * @return User name */
    public String getUserName() {
        return userName;
    }

    /** This method sets user name.
     * @param name User name to set */
    public void setUserName(String name) {
        userName = name;
    }
}
