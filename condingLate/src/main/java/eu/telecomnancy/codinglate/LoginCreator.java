package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginCreator {

    private VBox vbox;

    public VBox getVbox() {
        return this.vbox;
    }


    public LoginCreator() {
        this.vbox = createFormPane();
        addUIControls((GridPane) vbox.getChildren().get(0));
    }


    private VBox createFormPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setStyle("-fx-background-color: #336699;");

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: red;");
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private void addUIControls(GridPane gridPane) {
        // Add controls to the gridPane

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailField, 1, 0);


        Label PasswordLabel = new Label("Password:");
        TextField PasswordField = new TextField();
        gridPane.add(PasswordLabel, 0, 1);
        gridPane.add(PasswordField, 1, 1);


    Button submitButton = new Button("Se Connecter");

    // Event handling for the submit button
        submitButton.setOnAction(e ->

    {
        String email = emailField.getText();
        String password = PasswordField.getText();
        PersonController personcontroller = PersonController.getInstance();
        try {
            if(personcontroller.VerifierBase(email,password)){
                System.out.print("Utilisateur inscrit!");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    });

    }



}
