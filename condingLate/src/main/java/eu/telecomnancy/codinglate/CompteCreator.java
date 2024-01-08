package eu.telecomnancy.codinglate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CompteCreator extends Application {


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Form Creator");

        GridPane gridPane = createFormPane();
        addUIControls(gridPane);

        Scene scene = new Scene(gridPane, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createFormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add controls to the gridPane

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);


        Label FirstnameLabel = new Label("FirstName:");
        TextField FirstnameField = new TextField();
        gridPane.add(FirstnameLabel, 0, 1);
        gridPane.add(FirstnameField, 1, 1);

        Label LastnameLabel = new Label("LastName:");
        TextField LastnameField = new TextField();
        gridPane.add(LastnameLabel, 0, 2);
        gridPane.add(LastnameField, 1, 2);

        Label emailLabel = new Label("emailName:");
        TextField emailField = new TextField();
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);

        Label phoneLabel = new Label("phone:");
        TextField phoneField = new TextField();
        gridPane.add(phoneLabel, 0, 4);
        gridPane.add(phoneField, 1, 4);

        Label AdressLabel = new Label("Adress:");
        TextField AdressField = new TextField();
        gridPane.add(AdressLabel, 0, 5);
        gridPane.add(AdressField, 1, 5);


        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        gridPane.add(passwordLabel, 0, 6);
        gridPane.add(passwordField, 1, 6);


        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 0, 6, 1, 7);

        // Event handling for the submit button
        submitButton.setOnAction(e -> {
            int  id = Integer.getInteger(idField.getText());
            String firstName = FirstnameField.getText();
            String lastName = LastnameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String Adress = AdressField.getText();
            String password = passwordField.getText();

        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
