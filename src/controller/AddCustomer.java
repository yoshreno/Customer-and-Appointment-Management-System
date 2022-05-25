package controller;

import DAO.DBcountry;
import DAO.DBcustomer;
import DAO.DBdivision;
import DAO.DBuser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Division;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of AddCustomer.fxml.*/
public class AddCustomer implements Initializable {

    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ComboBox<Country> countryBox;
    public ComboBox<Division> divisionBox;
    public Label addressError;
    public Label countryError;
    public Label divisionError;
    public Label postalError;
    public Label phoneError;
    public Label nameError;

    /** This field holds the input from nameField.*/
    private String name;
    /** This field holds the input from addressField.*/
    private String address;
    /** This field holds the input from postalField.*/
    private String postalCode;
    /** This field holds the input from phoneField.*/
    private String phone;
    /** This field holds the input from countryBox.*/
    private String country;
    /** This field holds the input from divisionBox.*/
    private String division;

    /** This method initializes the Add Customer screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBcountry.allCountries);
    }

    /** This method navigates back to the Customer screen.*/
    public void onCancel(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.setScene(new Scene(root,1289, 572));
            stage.centerOnScreen();
            stage.setTitle("Customer Management");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method saves the customer if inputs are valid. Else, an error message is displayed.*/
    public void onSave(ActionEvent actionEvent) {
        if (areValidInputs()) {

            DBcustomer.addCustomer(
                    name,
                    address,
                    postalCode,
                    phone,
                    LocalDateTime.now(),
                    DBuser.currentUser.getName(),
                    LocalDateTime.now(),
                    DBuser.currentUser.getName(),
                    divisionBox.getSelectionModel().getSelectedItem().getDivisionId()
                    );

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                Stage stage = (Stage)cancelButton.getScene().getWindow();
                stage.setScene(new Scene(root,1289, 572));
                stage.centerOnScreen();
                stage.setTitle("Customer Management");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    /** This method checks whether all inputs are valid inputs.
     @return Returns true if inputs are valid*/
    private boolean areValidInputs() {
        boolean validInput = true;

        // Check Name field is not empty
        name = nameField.getText().trim();
        if (name.isEmpty()) {
            nameError.setText("Please enter a name");
            nameError.setVisible(true);
            validInput = false;
        } else
            nameError.setVisible(false);

        address = addressField.getText().trim();
        if (address.isEmpty()) {
            addressError.setText("Please enter an address");
            addressError.setVisible(true);
            validInput = false;
        } else
            addressError.setVisible(false);

        phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            phoneError.setText("Please enter a phone number");
            phoneError.setVisible(true);
            validInput = false;
        } else
            phoneError.setVisible(false);

        postalCode = postalField.getText().trim();
        if (postalCode.isEmpty()) {
            postalError.setText("Please enter the postal code");
            postalError.setVisible(true);
            validInput = false;
        } else
            postalError.setVisible(false);

        if (countryBox.getSelectionModel().getSelectedItem() == null) {
            countryError.setText("Please choose a country");
            countryError.setVisible(true);
            validInput = false;
        }
        else {
            country = countryBox.getSelectionModel().getSelectedItem().getCountryName();
            countryError.setVisible(false);
        }

        if (divisionBox.getSelectionModel().getSelectedItem() == null) {
            divisionError.setText("Please choose a division");
            divisionError.setVisible(true);
            validInput = false;
        }
        else {
            division = divisionBox.getSelectionModel().getSelectedItem().getDivisionName();
            divisionError.setVisible(false);
        }

        //return true;
        return validInput;
    }

    /** This method sets the items for divisionBox based on which country is selected.*/
    public void onCountrySelect(ActionEvent actionEvent) {
        Country c = countryBox.getSelectionModel().getSelectedItem();
        divisionBox.setItems(DBdivision.getAllDivisionsByCountry(c.getCountryId()));
    }
}
