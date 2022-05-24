package controller;

import DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utilities.Helper;
import utilities.Logger;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of LogInScreen.fxml.*/
public class LogInScreen implements Initializable {

    public TextField userNameField;
    public TextField passwordField;
    public Button loginButton;
    public Button exitButton;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label locationLabel;
    public Label realLocation;

    private ResourceBundle rb;

    /** This method initializes the Log In screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        userNameLabel.setText(rb.getString("UserName"));
        passwordLabel.setText(rb.getString("Password"));
        locationLabel.setText(rb.getString("Location"));
        realLocation.setText(ZoneId.systemDefault().toString());
        loginButton.setText(rb.getString("LogIn"));
        exitButton.setText(rb.getString("Exit"));

        DBuser.getAllUsers();
    }

    /** This method handles the log in functionality. If successful, the app will navigate to the Main Menu. Else, it will display an error message.*/
    public void onLogin(ActionEvent actionEvent) throws IOException {
        //userNameField.setText("test");
        //passwordField.setText("test");

        boolean correctCredentials = DBuser.verifyCredentials(userNameField.getText(), passwordField.getText());

        if (correctCredentials) {
            Logger.printLogInSuccess();

            DBcountry.getAllCountries();
            DBdivision.getAllDivisions();
            DBcustomer.getCustomers();
            DBcontact.getAllContacts();
            DBappointment.getAllAppointments();

            Helper.checkUpcomingAppointments();

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage)loginButton.getScene().getWindow();
                stage.setScene(new Scene(root,513, 343));
                stage.setTitle("Main Menu");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Logger.printLogInFail();

            ButtonType okButton = new ButtonType(rb.getString("ButtonText"));
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("LogInErrorMessage"), okButton);
            alert.setTitle(rb.getString("LogInErrorTitle"));
            alert.setContentText(rb.getString("LogInErrorMessage"));
            alert.showAndWait();
        }
    }

    /** This method exits the app.*/
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }
}
