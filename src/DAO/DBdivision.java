package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import java.sql.*;

/** This is the DAO class to handle division data.*/
public class DBdivision {

    /** Reference field to hold all divisions.*/
    public static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    /** This method gets a fresh list of all divisions from divisions DB.
     @return List of all divisions*/
    public static ObservableList<Division> getAllDivisions() {
        allDivisions.clear();

        String selectStatement = "SELECT * FROM first_level_divisions";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int cId = rs.getInt("Country_ID");

                Division d = new Division(id, name, cId);
                allDivisions.add(d);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allDivisions;
    }

    /** This method gets a list of divisions filtered on country ID.
     * @param countryId Country ID
     * @return List of all division*/
    public static ObservableList<Division> getAllDivisionsByCountry(int countryId) {
        ObservableList<Division> allDivisionsByCountry  = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, String.valueOf(countryId));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int cId = rs.getInt("Country_ID");

                Division d = new Division(id, name, cId);
                allDivisionsByCountry.add(d);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allDivisionsByCountry;
    }

    /** This method gets a unique list of divisions filtered on country ID where customer exists.
     * @param countryId Country ID
     * @return List of divisions*/
    public static ObservableList<Division> getCustomerDivisionsByCountry(int countryId) {
        ObservableList<Division> customerDivisions  = FXCollections.observableArrayList();

        for (int i = 0; i < DBcustomer.allCustomers.size(); i++)
        {
            boolean unique = true;
            for (int j = 0; j < i; j++){
                if (DBcustomer.allCustomers.get(i).getDivision().equals(DBcustomer.allCustomers.get(j).getDivision())){
                    unique = false;
                    break;
                }
            }
            if (unique) {
                if(DBcustomer.allCustomers.get(i).getDivision().getCountryId() == countryId)
                    customerDivisions.add(DBcustomer.allCustomers.get(i).getDivision());
            }
        }

        return customerDivisions;
    }
}
