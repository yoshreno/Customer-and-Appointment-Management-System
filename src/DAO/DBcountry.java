package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the DAO class to handle country data.*/
public class DBcountry {

    /** Reference field to hold all countries.*/
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /** This method gets a fresh list of all countries from countries DB.
     @return List of all countries*/
    public static ObservableList<Country> getAllCountries() {
        allCountries.clear();
        String selectStatement = "SELECT * FROM countries";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country c = new Country(id, name);
                allCountries.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allCountries;
    }
}
