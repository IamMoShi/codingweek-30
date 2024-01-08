package eu.telecomnancy.codinglate;

<<<<<<< HEAD
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
=======
import eu.telecomnancy.codinglate.UI.SearchBar;
>>>>>>> 562f948477b3484c000cc8feb5d452b634c1278b
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CompteCreator {

    private VBox vbox;

    public VBox getVbox() {
        return this.vbox;
    }


    public CompteCreator() {
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
            String firstName = FirstnameField.getText();
            String lastName = LastnameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = AdressField.getText();
            String password = passwordField.getText();
            Address adress = new Address(id,address);
            User newperson = new User(id, firstName, lastName, email, password, phone, 0.0, adress);
            PersonController personcontroller = PersonController.getInstance();
            personcontroller.insert((Person) newperson);
        });
    }

}



