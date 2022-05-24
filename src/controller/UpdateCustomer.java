package controller;

import DAO.*;
import javafx.collections.ObservableList;
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
import model.Customer;
import model.Division;
import model.User;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of UpdateCustomer.fxml.*/
public class UpdateCustomer implements Initializable {

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
    /** This field holds the reference of the customer to update.*/
    private static Customer customerToUpdate;

    /** This method initializes the Update Customer screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBcountry.allCountries);
        divisionBox.setItems(DBdivision.getAllDivisionsByCountry(customerToUpdate.getCountry().getCountryId()));

        idField.setText(String.valueOf(customerToUpdate.getId()));
        nameField.setText(customerToUpdate.getName());
        addressField.setText(customerToUpdate.getAddress());
        countryBox.getSelectionModel().select(customerToUpdate.getCountry());
        divisionBox.getSelectionModel().select(customerToUpdate.getDivision());
        postalField.setText(customerToUpdate.getPostalCode());
        phoneField.setText(customerToUpdate.getPhone());
    }

    /** This method sets this class's reference field customerToUpdate to the customer object.
     @param c Customer object that is being updated*/
    public static void setCustomerToUpdate(Customer c) { customerToUpdate = c; }

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

            DBcustomer.updateCustomer(
                    customerToUpdate.getId(),
                    name,
                    address,
                    postalCode,
                    phone,
                    LocalDateTime.now(),
                    DBuser.currentUser.getUserName(),
                    divisionBox.getSelectionModel().getSelectedItem().getDivisionId()
                    );

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                Stage stage = (Stage)saveButton.getScene().getWindow();
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
        divisionBox.setPromptText("Choose a division");
    }
}
