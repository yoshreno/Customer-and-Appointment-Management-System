package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;
import utilities.Helper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;

/** This is the DAO class to handle customers data.*/
public class DBcustomer {

    /** Reference field to hold all customers.*/
    public static ObservableList<Customer> allCustomers= FXCollections.observableArrayList();

    /** This method gets a list of all customers from customers DB.
     @return List of all customers*/
    public static ObservableList<Customer> getCustomers() {
        allCustomers.clear();

        String getCustomersAndDivision = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Division_ID, first_level_divisions.Division, countries.Country, " +
                "customers.Postal_Code, customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By " +
                "FROM ((customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID) " +
                "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID);";

        DBquery.setPreparedStatement(DBconnection.getConnection(), getCustomersAndDivision);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String updatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                String divisionString = rs.getString("Division");
                String countryString = rs.getString("Country");

                Country countryObj = null;
                for (Country c: DBcountry.allCountries) {
                    if (c.getCountryName().equals(countryString)) {
                        countryObj = c;
                        break;
                    }
                }

                Division divisionObj = null;
                for (Division d: DBdivision.allDivisions) {
                    if (d.getDivisionName().equals(divisionString)) {
                        divisionObj = d;
                        break;
                    }
                }

                Customer c = new Customer(
                        id,
                        name,
                        address,
                        postalCode,
                        phone,
                        createDate,
                        createdBy,
                        lastUpdate,
                        updatedBy,
                        divisionId,
                        divisionObj,
                        countryObj
                );

                allCustomers.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Sort allCustomers by id
        Comparator<Customer> comparator = Comparator.comparingInt(Customer::getId);
        FXCollections.sort(allCustomers, comparator);

        return allCustomers;
    }

    /** This method adds a customer to DB.
     * @param name Customer's name
     * @param address Customer's address
     * @param postalCode Customer's postal code
     * @param phone Customer's phone number
     * @param createDate Customer's created date
     * @param createdBy Customer's created by
     * @param lastUpdate Customer's last updated time
     * @param updatedBy Customer's last updated by
     * @param divisionId Customer's division ID
     */
    public static void addCustomer(String name, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy,
                                   LocalDateTime lastUpdate, String updatedBy, int divisionId) {

        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        DBquery.setPreparedStatement(DBconnection.getConnection(), insertStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, Helper.convertToUtc(createDate).toString());
            ps.setString(6, createdBy);
            ps.setString(7, Helper.convertToUtc(lastUpdate).toString());
            ps.setString(8, updatedBy);
            ps.setString(9, String.valueOf(divisionId));

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method updates a customer using Customer ID.
     * @param id Customer's ID
     * @param name Customer's name
     * @param address Customer's address
     * @param postalCode Customer's postal code
     * @param phone Customer's phone number
     * @param lastUpdate Customer's last updated time
     * @param updatedBy Customer's last updated by
     * @param divisionId Customer's division ID
     */
    public static void updateCustomer(int id, String name, String address, String postalCode, String phone,
                                      LocalDateTime lastUpdate, String updatedBy, int divisionId) {

        String insertStatement = "UPDATE customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?" +
                "WHERE Customer_ID = ?;";

        DBquery.setPreparedStatement(DBconnection.getConnection(), insertStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, Helper.convertToUtc(lastUpdate).toString());
            ps.setString(6, updatedBy);
            ps.setString(7, String.valueOf(divisionId));
            ps.setString(8, String.valueOf(id));

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method delete a customer from DB using Customer ID.
     * @param id Customer ID*/
    public static void deleteCustomer(int id) {

        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?;";

        DBquery.setPreparedStatement(DBconnection.getConnection(), deleteStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, String.valueOf(id));
            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method returns the number of customers filtered on country.
     * @param country Customer's country*/
    public static int getCustomersByCountry(Country country) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for (Customer cust: allCustomers) {
            if(cust.getCountry().equals(country))
                customers.add(cust);
        }
        return customers.size();
    }

    /** This method returns the number of customers filtered on country and division.
     * @param country Customer's country
     * @param division Customer's division*/
    public static int getCustomersByCountryDivison(Country country, Division division) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for (Customer cust: allCustomers) {
            if(cust.getCountry().equals(country) && cust.getDivision().equals(division))
                customers.add(cust);
        }
        return customers.size();
    }
}
