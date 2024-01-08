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

        Scene scene = new Scene(gridPane, 400, 200);
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
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 0, 2, 2, 1);

        // Event handling for the submit button (you can add your own logic)
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = passwordField.getText();
            
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}



