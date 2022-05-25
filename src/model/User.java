package model;

import java.sql.Timestamp;

/** This is the model class for User object.*/
public class User extends Person {

    private String password;
    private Timestamp lastLogIn;

    /** This is the constructor method that sets the attributes for User object.*/
    public User(int id, String name) {
        super(id, name);
    }

}
