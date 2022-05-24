package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of MainMenu.fxml.*/
public class MainMenu implements Initializable {

    public Button customerButton;
    public Button logOutButton;
    public Button apptButton;
    public Button reportsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /** This method navigates to the Customer screen.*/
    public void onCustomer(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage)customerButton.getScene().getWindow();
            stage.setScene(new Scene(root,1289, 572));
            stage.centerOnScreen();
            stage.setTitle("Customer Management");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method logs out of the app and returns to the Log In screen.*/
    public void onLogOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Log Out Confirmation");
        alert.setContentText("Please confirm to log out from the application");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Parent root = null;

            try {
                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/rb", Locale.getDefault());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInScreen.fxml"));
                loader.setResources(rb);
                root = loader.load();

                Stage stage = (Stage)logOutButton.getScene().getWindow();
                stage.setScene(new Scene(root, 513, 343));
                stage.centerOnScreen();
                stage.setTitle(rb.getString("LogInTitle"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** This method navigates to the Appointment screen.*/
    public void onAppt(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage)apptButton.getScene().getWindow();
            stage.setScene(new Scene(root,1289, 572));
            stage.centerOnScreen();
            stage.setTitle("Appointment Management");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method navigates to the Reports screen.*/
    public void onReports(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
            Stage stage = (Stage)reportsButton.getScene().getWindow();
            stage.setScene(new Scene(root,1289, 572));
            stage.centerOnScreen();
            stage.setTitle("Reports");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}