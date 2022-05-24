package controller;

import DAO.DBappointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of AppointmentScreen.fxml.*/
public class AppointmentScreen implements Initializable {

    public Button mainMenuButton;
    public Button addButton;
    public Button updateButton;
    public Button deleteButton;
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
    public ToggleGroup Filter;
    public RadioButton allFilter;
    public RadioButton weekFilter;
    public RadioButton monthFilter;

    /** This method initializes the Appointment screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    /** This method navigates back to the Main Menu.*/
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

    /** This method navigates to the Add Appointment screen.*/
    public void onAdd(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            Stage stage = (Stage)addButton.getScene().getWindow();
            stage.setScene(new Scene(root,513, 565));
            stage.centerOnScreen();
            stage.setTitle("Add Appointment");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method navigates to the Update Appointment screen.*/
    public void onUpdate(ActionEvent actionEvent) {

        Appointment selectedAppt = AppointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppt != null) {
            UpdateAppointment.setApptToUpdate(selectedAppt);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
                Stage stage = (Stage)addButton.getScene().getWindow();
                stage.setScene(new Scene(root,513, 565));
                stage.centerOnScreen();
                stage.setTitle("Update Appointment");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment to update");
            alert.showAndWait();
        }
    }

    /** This method deletes the selected appointment.*/
    public void onDelete(ActionEvent actionEvent) {
        Appointment selectedAppt = AppointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppt != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Appointment ID: " + selectedAppt.getId() + " ?");
            alert.setContentText("Please confirm to delete the selected appointment");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                DBappointment.deleteAppointment(selectedAppt.getId());

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Appointment");
                alert.setHeaderText("Delete Success");
                alert.setContentText("Appointment ID: " + selectedAppt.getId() + " has been deleted from the database");
                alert.showAndWait();

                AppointmentTable.setItems(DBappointment.getAllAppointments());
            }
            else {
                // Do nothing and close the dialog.
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        }
    }

    /** This method displays all appointments on the table.*/
    public void onAll(ActionEvent actionEvent) {
        AppointmentTable.setItems(DBappointment.allAppointments);
        AppointmentTable.getSortOrder().add(Start);
    }

    /** This method displays this week's appointments on the table.*/
    public void onWeek(ActionEvent actionEvent) {
        AppointmentTable.setItems(DBappointment.getAppointmentsByWeek());
        AppointmentTable.getSortOrder().add(Start);
    }

    /** This method displays this month's appointments on the table.*/
    public void onMonth(ActionEvent actionEvent) {
        AppointmentTable.setItems(DBappointment.getAppointmentsByMonth());
        AppointmentTable.getSortOrder().add(Start);
    }
}
