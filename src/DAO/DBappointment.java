package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import utilities.Helper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

/** This is the DAO class to handle appointments data.*/
public class DBappointment {

    /** Reference field to hold all appointments.*/
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** This method gets a list of all appointments from appointments DB.
     @return List of all appointments*/
    public static ObservableList<Appointment> getAllAppointments() {
        allAppointments.clear();

        String selectStatement = "SELECT * FROM appointments";
        DBquery.setPreparedStatement(DBconnection.getConnection(), selectStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String updatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Customer customerObj = null;
                for (Customer c: DBcustomer.getCustomers()) {
                    if (c.getId() == customerId) {
                        customerObj = c;
                        break;
                    }
                }

                User userObj = null;
                for (User u: DBuser.allUsers) {
                    if (u.getUserId() == userId) {
                        userObj = u;
                        break;
                    }
                }

                Contact contactObj = null;
                for (Contact c: DBcontact.allContacts) {
                    if (c.getId() == contactId) {
                        contactObj = c;
                        break;
                    }
                }

                Appointment appt = new Appointment(
                        id,
                        title,
                        description,
                        location,
                        type,
                        start,
                        end,
                        createDate,
                        createdBy,
                        lastUpdate,
                        updatedBy,
                        customerObj,
                        userObj,
                        contactObj
                );

                allAppointments.add(appt);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allAppointments;
    }

    /** This method gets a list of appointments that occurs this week.
     @return List of appointments this week*/
    public static ObservableList<Appointment> getAppointmentsByWeek() {
        ObservableList<Appointment> weekAppts = FXCollections.observableArrayList();

        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int week = today.get(weekFields.weekOfWeekBasedYear());

        for (Appointment appt: allAppointments) {
            LocalDate apptDay = appt.getStart().toLocalDate();
            int apptWeek = apptDay.get(weekFields.weekOfWeekBasedYear());
            if(week == apptWeek)
                weekAppts.add(appt);
        }
        return weekAppts;
    }

    /** This method gets a list of appointments that occurs this month.
     @return List of appointments this month*/
    public static ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> monthAppts = FXCollections.observableArrayList();

        int month = LocalDate.now().getMonthValue();
        for (Appointment appt: allAppointments) {
            int apptMonth = appt.getStart().toLocalDate().getMonthValue();
            if(month == apptMonth)
                monthAppts.add(appt);
        }
        return monthAppts;
    }

    /** This method gets a list of appointments by Customer ID.
     * @param id Customer ID
     * @return List of appointments for a customer*/
    public static ObservableList<Appointment> getCustomerAppointments(int id) {
        ObservableList<Appointment> custAppts = FXCollections.observableArrayList();

        for (Appointment appt: allAppointments) {
            if(appt.getCustomer().getId() == id)
                custAppts.add(appt);
        }
        return custAppts;
    }

    /** This method gets a list of appointments by Customer ID but filters out the appointment being updated.
     * @param id Customer ID
     * @param updatingAppt Appointment to update
     * @return List of appointments for a customer*/
    public static ObservableList<Appointment> getCustomerAppointments(int id, Appointment updatingAppt) {
        ObservableList<Appointment> custAppts = FXCollections.observableArrayList();

        for (Appointment appt: allAppointments) {
            if ((appt.getCustomer().getId() == id) && (appt != updatingAppt))
                custAppts.add(appt);
        }
        return custAppts;
    }

    /** This method gets a list of appointments by user.
     * @param user User object
     * @return List of appointments for a user*/
    public static ObservableList<Appointment> getUserAppointments(User user) {
        ObservableList<Appointment> userAppts = FXCollections.observableArrayList();

        for (Appointment appt: allAppointments) {
            if (appt.getUser() == user)
                userAppts.add(appt);
        }
        return userAppts;
    }

    /** This method gets a list of appointments by contact.
     * @param contact Contact object
     * @return List of appointments for a contact*/
    public static ObservableList<Appointment> getContactAppointments(Contact contact) {
        ObservableList<Appointment> contactAppts = FXCollections.observableArrayList();

        for (Appointment appt: allAppointments) {
            if (appt.getContact() == contact)
                contactAppts.add(appt);
        }
        return contactAppts;
    }

    /** This method gets a list of all existing types in DB.
     * @return Unique list of types*/
    public static ObservableList<String> getTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();

        for (int i = 0; i < allAppointments.size(); i++)
        {
            boolean unique = true;
            for (int j = 0; j < i; j++){
                if (allAppointments.get(i).getType().equalsIgnoreCase(allAppointments.get(j).getType())){
                    unique = false;
                    break;
                }
            }
            if (unique){
                types.add(allAppointments.get(i).getType());
            }
        }

        return types;
    }

    /** This method gets a list months by selected type.
     * @param type Type object to filter on
     * @return Unique list of months*/
    public static ObservableList<Month> getMonthsByType(String type) {
        ObservableList<Month> months = FXCollections.observableArrayList();

        for (Appointment appt: allAppointments) {
            if (appt.getType().equalsIgnoreCase(type)) {
                months.add(appt.getStart().toLocalDate().getMonth());
            }
        }

        for (int i = 0; i < months.size(); i++)
        {
            boolean unique = true;
            for (int j = 0; j < i; j++){
                if (months.get(i).equals(months.get(j))){
                    unique = false;
                    break;
                }
            }
            if (!unique){
                months.remove(i);
            }
        }
        return months;
    }

    /** This method gets a list of appointments filtered on type.
     * @param type Type object to filter on
     * @return Number of appointments*/
    public static int getAppointmentsByType(String type) {
        ObservableList<Appointment> appts = FXCollections.observableArrayList();

        for (Appointment a: allAppointments) {
            if (a.getType().equalsIgnoreCase(type)) {
                appts.add(a);
            }
        }
        return appts.size();
    }

    /** This method gets a list of appointments filtered on type and month.
     * @param type Type object to filter on
     * @param month Month object to filter on
     * @return Number of appointments*/
    public static int getAppointmentsByTypeMonth(String type, Month month) {
        ObservableList<Appointment> appts = FXCollections.observableArrayList();

        for (Appointment a: allAppointments) {
            if (a.getType().equalsIgnoreCase(type) && a.getStart().toLocalDate().getMonth().equals(month)) {
                appts.add(a);
            }
        }
        return appts.size();
    }

    /** This method adds an appointment to DB.
     * @param title Appointment's title
     * @param description Appointment's description
     * @param location Appointment's location
     * @param type Appointment's type
     * @param start Appointment's start time
     * @param end Appointment's end time
     * @param createDate Appointment's created date
     * @param createdBy Appointment's created by
     * @param lastUpdate Appointment's last updated time
     * @param updatedBy Appointment's last updated by
     * @param customer Appointment's customer
     * @param user Appointment's user
     * @param contact Appointment's contact
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                                   LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, Customer customer,
                                   User user, Contact contact) {

        String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By," +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        DBquery.setPreparedStatement(DBconnection.getConnection(), insertStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, Helper.convertToUtc(start).toString());
            ps.setString(6, Helper.convertToUtc(end).toString());
            ps.setString(7, Helper.convertToUtc(createDate).toString());
            ps.setString(8, createdBy);
            ps.setString(9, Helper.convertToUtc(lastUpdate).toString());
            ps.setString(10, updatedBy);
            ps.setString(11, String.valueOf(customer.getId()));
            ps.setString(12, String.valueOf(user.getUserId()));
            ps.setString(13, String.valueOf(contact.getId()));

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method delete an appointment by Appointment ID.
     * @param id ID of the appointment to delete*/
    public static void deleteAppointment(int id) {

        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?;";

        DBquery.setPreparedStatement(DBconnection.getConnection(), deleteStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, String.valueOf(id));
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method updates an appointment using Appointment ID.
     * @param id Appointment's ID
     * @param title Appointment's title
     * @param description Appointment's description
     * @param location Appointment's location
     * @param type Appointment's type
     * @param start Appointment's start time
     * @param end Appointment's end time
     * @param createDate Appointment's created date
     * @param createdBy Appointment's created by
     * @param lastUpdate Appointment's last updated time
     * @param updatedBy Appointment's last updated by
     * @param customer Appointment's customer
     * @param user Appointment's user
     * @param contact Appointment's contact
     */
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                                         LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, Customer customer,
                                         User user, Contact contact) {

        String updateStatement = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?;";

        DBquery.setPreparedStatement(DBconnection.getConnection(), updateStatement);
        PreparedStatement ps = DBquery.getPreparedStatement();

        try {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, Helper.convertToUtc(start).toString());
            ps.setString(6, Helper.convertToUtc(end).toString());
            ps.setString(7, Helper.convertToUtc(createDate).toString());
            ps.setString(8, createdBy);
            ps.setString(9, Helper.convertToUtc(lastUpdate).toString());
            ps.setString(10, updatedBy);
            ps.setString(11, String.valueOf(customer.getId()));
            ps.setString(12, String.valueOf(user.getUserId()));
            ps.setString(13, String.valueOf(contact.getId()));
            ps.setString(14, String.valueOf(id));

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
