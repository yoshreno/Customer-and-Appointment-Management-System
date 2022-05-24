package controller;

import DAO.DBcountry;
import DAO.DBcustomer;
import DAO.DBdivision;
import DAO.DBquery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class that handles the functionality of CustomerScreen.fxml.*/
public class CustomerScreen implements Initializable {

    public Button mainMenuButton;
    public Button addButton;
    public TableView<Customer> CustomerTable;
    public TableColumn<Customer, Integer> Id;
    public TableColumn<Customer, String> Name;
    public TableColumn<Customer, String> Address;
    public TableColumn<Customer, String> PostalCode;
    public TableColumn<Customer, String> Phone;
    public TableColumn<Customer, Timestamp> CreateDate;
    public TableColumn<Customer, String> CreatedBy;
    public TableColumn<Customer, Timestamp> LastUpdate;
    public TableColumn<Customer, String> UpdatedBy;
    public TableColumn<Customer, String> Division;
    public TableColumn<Customer, String> Country;
    public Button updateButton;
    public Button deleteButton;

    /** This method initializes the Customer screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerTable.setItems(DBcustomer.getCustomers());

        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Division.setCellValueFactory(new PropertyValueFactory<>("Division"));
        Country.setCellValueFactory(new PropertyValueFactory<>("Country"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        CreateDate.setCellValueFactory(new PropertyValueFactory<>("CreateDate"));
        CreatedBy.setCellValueFactory(new PropertyValueFactory<>("CreatedBy"));
        LastUpdate.setCellValueFactory(new PropertyValueFactory<>("LastUpdate"));
        UpdatedBy.setCellValueFactory(new PropertyValueFactory<>("UpdatedBy"));

        //CustomerTable.getSortOrder().add(Id);
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

    /** This method navigates to the Add Customer screen.*/
    public void onAdd(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            Stage stage = (Stage)addButton.getScene().getWindow();
            stage.setScene(new Scene(root,513, 565));
            stage.centerOnScreen();
            stage.setTitle("Add Customer");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method navigates to the Update Customer screen.*/
    public void onUpdate(ActionEvent actionEvent) {
        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            UpdateCustomer.setCustomerToUpdate(selectedCustomer);

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
                Stage stage = (Stage)addButton.getScene().getWindow();
                stage.setScene(new Scene(root,513, 565));
                stage.centerOnScreen();
                stage.setTitle("Update Customer");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to update");
            alert.showAndWait();
        }
    }

    /** This method deletes the selected customer.*/
    public void onDelete(ActionEvent actionEvent) {
        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete '" + selectedCustomer.getName() + "'?");
            alert.setContentText("Please confirm to delete the selected customer");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                DBcustomer.deleteCustomer(selectedCustomer.getId());

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Customer");
                alert.setHeaderText("Delete Success");
                alert.setContentText("'" + selectedCustomer.getName() + "' has been deleted from the database");
                alert.showAndWait();

                CustomerTable.setItems(DBcustomer.getCustomers());
            }
            else {
                // Do nothing and close the dialog.
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a customer to delete");
            alert.showAndWait();
        }
    }
}
