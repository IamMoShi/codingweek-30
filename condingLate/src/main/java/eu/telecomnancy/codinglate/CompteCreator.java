package eu.telecomnancy.codinglate;


import eu.telecomnancy.codinglate.UI.CustomPasswordField;
import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.FormButton;
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

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private void addUIControls(GridPane gridPane) {
        // Add controls to the gridPane

        CustomTextField firstNameField = new CustomTextField("Prénom");
        gridPane.add(firstNameField, 1, 0);

        CustomTextField lastNameField = new CustomTextField("Nom");
        gridPane.add(lastNameField, 1, 1);

        CustomTextField emailField = new CustomTextField("Email");
        gridPane.add(emailField, 1, 2);

        CustomTextField phoneField = new CustomTextField("Téléphone");
        gridPane.add(phoneField, 1, 3);

        CustomTextField addressField = new CustomTextField("Adresse");
        gridPane.add(addressField, 1, 4);

        CustomPasswordField passwordField = new CustomPasswordField("Mot de Passe");
        gridPane.add(passwordField, 1, 5);

        FormButton submitButton = new FormButton("submitButton", "S'inscrire");
        submitButton.initializeButton();
        submitButton.setPrefWidth(240);


        // Event handling for the submit button
        submitButton.setOnAction(e -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            String address = addressField.getText();
            Address adress = new Address(address);
            String password = passwordField.getText();


            //if nothing is filled
            if (lastName.isBlank() || firstName.isBlank() || email.isBlank() || phone.isBlank() || address.isBlank() || password.isBlank()) {

                addNewLabel(gridPane, "Informations Manquantes!");
                return;

            }


            //if the email is not valid
            PersonController personController = PersonController.getInstance();
            if (personController.isEmailUsed(email)) {
                addNewLabel(gridPane, "Email déjà utilisé!");
                return;
            }

            if (!isValidEmail(email)) {
                addNewLabel(gridPane, "Email non valide!");
                return;
            }


            //if the phone number is not valid
            if (!isValidPhoneNumber(phone)) {
                addNewLabel(gridPane, "Numéro de téléphone non valide!");
                return;
            }


            if (!isAscii(firstName) && !isAscii(lastName)) {
                addNewLabel(gridPane, "Caractères non reconnus!");
                return;
            }
            if (!isAscii(password)) {
                addNewLabel(gridPane, "Caractères non reconnus!");
                return;
            }
            if (!isCorrect(password)) {
                addNewLabel(gridPane, "Mot de Passe trop faible!");
                return;
            }


            User newperson = new User(firstName, lastName, email, password, adress);
            newperson.setPhone(phone);
            PersonController personcontroller = PersonController.getInstance();

            personcontroller.insert((Person) newperson);

            personcontroller.setCurrentUser(newperson);
            System.out.println("User created");
            //SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            //Scene scene = sceneManager.createSceneProfil(personcontroller);
            //sceneManager.switchScene(scene);


        });

        gridPane.add(submitButton, 0, 6, 2, 6);


    }

    private boolean isAscii(String str) {
        return str.matches("\\A\\p{ASCII}*\\z");
    }

    //Check if the password is relatively strong
    private boolean isCorrect(String str) {
        assert (str.contains("0-9"));
        assert (str.contains("a-z"));
        assert (str.contains("A-Z"));
        assert (str.length() >= 5);
        return true;
    }

    private boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière pour valider l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifier si le numéro de téléphone ne contient que des chiffres
        if (phoneNumber.matches("\\d+")) {
            return true;
        }
        // Vérifier si le numéro de téléphone commence par un plus (+) suivi de chiffres
        else if (phoneNumber.matches("\\+\\d+")) {
            return true;
        }
        // Le numéro de téléphone n'est pas valide
        else {
            return false;
        }
    }

    private void addNewLabel(GridPane root, String str) {

        Label Label = new Label(str);

        root.add(Label, 1, 8);
    }
}





