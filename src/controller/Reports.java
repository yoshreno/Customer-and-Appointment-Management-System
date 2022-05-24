package controller;

import DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of Reports.fxml.*/
public class Reports implements Initializable {

    public Button mainMenuButton;
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Appointment, Integer> Id;
    public TableColumn<Appointment, String> Title;
    public TableColumn<Appointment, String> Description;
    public TableColumn<Appointment, String> Location;
    public TableColumn<Appointment, Contact> Contact;
    public TableColumn<Appointment, String> Type;
    public TableColumn<Appointment, Timestamp> Start;
    public TableColumn<Appointment, Timestamp> End;
    public TableColumn<Appointment, Customer> Customer;
    public TableColumn<Appointment, User> User;
    public ComboBox<String> TypeBox;
    public ComboBox<Month> MonthBox;
    public TextField appointmentsField;
    public ComboBox<Contact> ContactBox;
    public ComboBox<Country> CountryBox;
    public ComboBox<Division> DivisionBox;
    public TextField customersField;

    /** This method initializes the Reports screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBcustomer.getCustomers();
        DBappointment.getAllAppointments();

        TypeBox.setItems(DBappointment.getTypes());
        ContactBox.setItems(DBcontact.allContacts);
        CountryBox.setItems(DBcountry.allCountries);

        AppointmentTable.setItems(DBappointment.getAllAppointments());
        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        User.setCellValueFactory(new PropertyValueFactory<>("User"));
        AppointmentTable.getSortOrder().add(Start);
    }

    /** This method returns to the Main Menu.*/
    public void onMainMenu(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)mainMenuButton.getScene().getWindow();
            stage.setScene(new Scene(root,513, 343));
            stage.centerOnScreen();
            stage.setTitle("Main Menu");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method sets the items on MonthBox and number of appointments on appointmentsField based on selected type.*/
    public void onType(ActionEvent actionEvent) {
        MonthBox.setDisable(false);
        MonthBox.setItems(DBappointment.getMonthsByType(TypeBox.getValue()));
        appointmentsField.setText(String.valueOf(DBappointment.getAppointmentsByType(TypeBox.getValue())));
    }

    /** This method sets number of appointments on appointmentsField based on selected type and month.*/
    public void onMonth(ActionEvent actionEvent) {
        appointmentsField.setText(String.valueOf(DBappointment.getAppointmentsByTypeMonth(TypeBox.getValue(), MonthBox.getValue())));
    }

    /** This method sets the AppointmentTable based on selected contact.*/
    public void onContact(ActionEvent actionEvent) {
        AppointmentTable.setItems(DBappointment.getContactAppointments(ContactBox.getValue()));
        AppointmentTable.getSortOrder().add(Start);
    }

    /** This method sets the items on DivisionBox and number of customers on customersField based on selected country.*/
    public void onCountry(ActionEvent actionEvent) {
        DivisionBox.setDisable(false);
        DivisionBox.setItems(DBdivision.getCustomerDivisionsByCountry(CountryBox.getSelectionModel().getSelectedItem().getCountryId()));
        customersField.setText(String.valueOf(DBcustomer.getCustomersByCountry(CountryBox.getValue())));
    }

    /** This method sets number of customers on customersField based on selected country and division.*/
    public void onDivision(ActionEvent actionEvent) {
        customersField.setText(String.valueOf(DBcustomer.getCustomersByCountryDivison(CountryBox.getValue(), DivisionBox.getValue())));
    }
}
