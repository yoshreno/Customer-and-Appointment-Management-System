package controller;

import DAO.DBappointment;
import DAO.DBcontact;
import DAO.DBcustomer;
import DAO.DBuser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.Helper;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of UpdateAppointment.fxml.*/
public class UpdateAppointment implements Initializable {

    public TextField idField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField typeField;
    public ComboBox<Contact> contactBox;
    public DatePicker datePicker;
    public TextField locationField;
    public ComboBox<LocalTime> startTimeBox;
    public ComboBox<LocalTime> endTimeBox;
    public ComboBox<Customer> customerBox;
    public ComboBox<User> userBox;
    public Button cancelButton;
    public Button saveButton;

    private String title;
    private String description;
    private String location;
    private Contact contact;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Customer customer;
    private User user;

    /** This field holds the reference of the appointment to update.*/
    private static Appointment apptToUpdate;
    /** This field is set to the first available start time for working day.*/
    private static final LocalTime firstStartTime = LocalTime.of(8,0);
    /** This field is set to the last available start time for working day.*/
    private static final LocalTime lastStartTime = LocalTime.of(21,30);
    /** This field is set to the last available end time for working day.*/
    private static final LocalTime lastEndTime = LocalTime.of(22,0);

    /** This method initializes the Update Appointment screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTimeBox.setItems(Helper.getEasternTimeSlots(firstStartTime, lastStartTime, LocalDate.now()));
        endTimeBox.setItems(Helper.getEasternTimeSlots(firstStartTime.plusMinutes(30), lastEndTime, LocalDate.now()));

        contactBox.setItems(DBcontact.allContacts);
        customerBox.setItems(DBcustomer.allCustomers);
        userBox.setItems(DBuser.allUsers);

        idField.setText(String.valueOf(apptToUpdate.getId()));
        titleField.setText(apptToUpdate.getTitle());
        descriptionField.setText(apptToUpdate.getDescription());
        locationField.setText(apptToUpdate.getLocation());
        contactBox.getSelectionModel().select(apptToUpdate.getContact());
        typeField.setText(apptToUpdate.getType());
        datePicker.setValue(apptToUpdate.getStart().toLocalDate());
        startTimeBox.getSelectionModel().select(apptToUpdate.getStart().toLocalTime());
        endTimeBox.getSelectionModel().select(apptToUpdate.getEnd().toLocalTime());
        customerBox.getSelectionModel().select(apptToUpdate.getCustomer());
        userBox.getSelectionModel().select(apptToUpdate.getUser());
    }

    /** This method sets this class's reference field apptToUpdate to the appointment object.
     @param appt Appointment object that is being updated*/
    public static void setApptToUpdate(Appointment appt) { apptToUpdate = appt; }

    /** This method navigates back to the Appointment screen.*/
    public void onCancel(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.setScene(new Scene(root,1289, 572));
            stage.centerOnScreen();
            stage.setTitle("Appointment Management");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method saves the appointment if inputs are valid and there is no conflicting appointment. Else, an error message is displayed.*/
    public void onSave(ActionEvent actionEvent) {
        if (areValidInputs()) {

            ObservableList<Appointment> customerAppts = DBappointment.getCustomerAppointments(customer.getId(), apptToUpdate);

            if (Helper.checkForOverlap(customerAppts, LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime)) == false) {
                DBappointment.updateAppointment(
                        apptToUpdate.getId(),
                        title,
                        description,
                        location,
                        type,
                        LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime),
                        apptToUpdate.getCreateDate(),
                        user.getUserName(),
                        LocalDateTime.now(),
                        DBuser.currentUser.getUserName(),
                        customer,
                        user,
                        contact
                );
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
                    Stage stage = (Stage)saveButton.getScene().getWindow();
                    stage.setScene(new Scene(root,1289, 572));
                    stage.centerOnScreen();
                    stage.setTitle("Appointment Management");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Save Error");
                error.setHeaderText("Conflicting Appointments");
                error.setContentText("There is a conflicting appointment with this customer. Please select another time slots");
                error.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText("Empty Field(s)");
            alert.setContentText("All fields are required. Please enter a value to all fields.");
            alert.showAndWait();
        }
    }

    /** This method checks whether all inputs are valid inputs.
     @return Returns true if inputs are valid*/
    private boolean areValidInputs() {
        boolean validInput = true;

        // These are all checks for empty fields
        title = titleField.getText().trim();
        if (title.isEmpty()) {
            validInput = false;
            //System.out.println("Title is empty");
        }

        description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            validInput = false;
            //System.out.println("Description is empty");
        }

        location = locationField.getText().trim();
        if (location.isEmpty()) {
            validInput = false;
            //System.out.println("Location is empty");
        }

        if (contactBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            //System.out.println("Contact is empty");
        }
        else
            contact = contactBox.getSelectionModel().getSelectedItem();

        type = typeField.getText().trim();
        if (type.isEmpty()) {
            validInput = false;
            //System.out.println("Type is empty");
        }

        if (datePicker.getValue() == null) {
            validInput = false;
            //System.out.println("Date is empty");
        }
        else
            date = datePicker.getValue();

        if (startTimeBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            //System.out.println("Start is empty");
        }
        else
            startTime = startTimeBox.getSelectionModel().getSelectedItem();

        if (endTimeBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            //System.out.println("End is empty");
        }
        else
            endTime = endTimeBox.getSelectionModel().getSelectedItem();

        if (customerBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            //System.out.println("Customer is empty");
        }
        else
            customer = customerBox.getSelectionModel().getSelectedItem();

        if (userBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            //System.out.println("User is empty");
        }
        else
            user = userBox.getSelectionModel().getSelectedItem();

        //return true;
        return validInput;
    }

    /** This method sets the time for endTimeBox based on which start time is selected.*/
    public void onStartTimeSelect(ActionEvent actionEvent) {
        endTimeBox.getSelectionModel().clearSelection();
        LocalTime selectedStart = startTimeBox.getSelectionModel().getSelectedItem();

        ZonedDateTime zdt = LocalDateTime.of(LocalDate.now(),selectedStart).atZone(ZoneId.systemDefault());
        LocalDateTime est = zdt.withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();

        endTimeBox.setItems(Helper.getEasternTimeSlots(est.toLocalTime().plusMinutes(30), lastEndTime, LocalDate.now()));
    }
}
