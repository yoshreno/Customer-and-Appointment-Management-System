package main;

import DAO.DBconnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class creates the Customer and Appointment Management app. JavaDoc for this app can be found at C175 Performance Assessment\javadoc\index.html.*/
public class Main extends Application {

    /** This method is the main entry point for this JavaFX application.*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;

        try {
            ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/rb", Locale.getDefault());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInScreen.fxml"));
            loader.setResources(rb);
            root = loader.load();

            primaryStage.setTitle(rb.getString("LogInTitle"));
            primaryStage.setScene(new Scene(root, 513, 343));
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** This is the main method.*/
    public static void main(String[] args) {
        DBconnection.openConnection();

        launch(args);

        if (DBconnection.getConnection() != null)
            DBconnection.closeConnection();
    }
}
