package eu.telecomnancy.codinglate;


import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.UI.SearchBar;
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

        Label FirstnameLabel = new Label("Prénom:");
        TextField FirstnameField = new TextField();
        gridPane.add(FirstnameLabel, 0, 0);
        gridPane.add(FirstnameField, 1, 0);

        Label LastnameLabel = new Label("Nom:");
        TextField LastnameField = new TextField();
        gridPane.add(LastnameLabel, 0, 1);
        gridPane.add(LastnameField, 1, 1);

        Label emailLabel = new Label("email:");
        TextField emailField = new TextField();
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);

        Label phoneLabel = new Label("téléphone:");
        TextField phoneField = new TextField();
        gridPane.add(phoneLabel, 0, 3);
        gridPane.add(phoneField, 1, 3);

        Label AdressLabel = new Label("Adress:");
        TextField AdressField = new TextField();
        gridPane.add(AdressLabel, 0, 4);
        gridPane.add(AdressField, 1, 4);


        Label passwordLabel = new Label("Mot de Passe:");
        TextField passwordField = new TextField();
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordField, 1, 5);


        Button submitButton = new Button("Inscription");

        // Event handling for the submit button
        submitButton.setOnAction(e -> {

            String firstName = FirstnameField.getText();
            String lastName = LastnameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            String address = AdressField.getText();
            String password = passwordField.getText();


            Address adress = new Address(address);

            //if nothing is filled
            if(lastName.isBlank() || firstName.isBlank() || email.isBlank() || phone.isBlank() || address.isBlank() || password.isBlank()){

                addNewLabel(gridPane, "Informations Manquantes!");

            }



            if(!isAscii(firstName) && !isAscii(lastName)){
                addNewLabel(gridPane, "Caractères non reconnus!");
            }
            if(!isAscii(password)){
                addNewLabel(gridPane,"Caractères non reconnus!");
            }
            if(!isCorrect(password)) {
                addNewLabel(gridPane,"Mot de Passe trop faible!");
            }

            else{
                User newperson = new User(firstName, lastName, email, password, adress);

                PersonController personcontroller = PersonController.getInstance();
                personcontroller.insert((Person) newperson);
            }
        });

        gridPane.add(submitButton, 0, 6, 2, 6);


    }

    private boolean isAscii(String str) {
        return str.matches("\\A\\p{ASCII}*\\z");
    }

    //Check if the password is relatively strong
    private boolean isCorrect(String str){
        assert (str.contains("0-9"));
        assert (str.contains("a-z"));
        assert (str.contains("A-Z"));
        assert (str.length()>=5);
        return true;
    }



    private void addNewLabel(GridPane root, String str) {

        Label Label = new Label(str);

        root.add(Label,1,8);
    }
}





