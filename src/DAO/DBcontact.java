package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the DAO class to handle contacts data.*/
public class DBcontact {

    /** Reference field to hold all contacts.*/
    public static ObservableList<Contact> allContacts= FXCollections.observableArrayList();

    /** This method gets a fresh list of all contacts from contacts DB.
     @return List of all contacts*/
    public static ObservableList<Contact> getAllContacts() {
        allContacts.clear();

        String selectStatement = "SELECT * FROM contacts";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(id, name, email);
                allContacts.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allContacts;
    }
}
