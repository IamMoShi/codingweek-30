package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.CustomPasswordField;
import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.FormButton;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginCreator extends HBox{

    private VBox vbox;
    private Stage stage;

    public VBox getVbox() {
        return this.vbox;
    }

    public LoginCreator(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.vbox = createFormPane();
        addUIControls((GridPane) vbox.getChildren().get(0), previousScene);
        addBottomMessage(vbox, stage);
    }

    private VBox createFormPane() {

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private void addUIControls(GridPane gridPane, Scene previousScene) {
        // Ajouter des contrôles à la grille

        CustomTextField emailField = new CustomTextField("Email");
        gridPane.add(emailField, 1, 0);

        CustomPasswordField passwordField = new CustomPasswordField("Mot de passe");
        gridPane.add(passwordField, 1, 1);

        FormButton submitButton = new FormButton("Submit", "Se connecter");
        submitButton.initializeButton();
        submitButton.setPrefWidth(200);
        gridPane.add(submitButton, 1, 2);

        // Gestion d'événements pour le bouton de soumission
        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            PersonController personController = PersonController.getInstance();

            if (personController.VerifierBase(email, password)) {
                PersonController.getInstance();
                personController.setCurrentUser(personController.getPersonByEmail(email));
                System.out.println("Connexion réussie");

                SceneManager sceneManager = new SceneManager(stage);
                sceneManager.switchScene(previousScene);
            }
        });
    }

    private void addBottomMessage(VBox vbox,Stage stage) {
        HBox bottomMessage = new HBox();
        bottomMessage.setAlignment(Pos.CENTER);
        bottomMessage.setSpacing(10);

        Label messageLabel = new Label("Vous n'avez pas de compte ?");
        Hyperlink signupLink = new Hyperlink("Inscrivez-vous ici");
        signupLink.setOnAction(e -> {
            SceneManager sceneManager = new SceneManager(stage);
            Scene scene = sceneManager.createSceneCompteCreator();
            sceneManager.switchScene(scene);
        });

        bottomMessage.getChildren().addAll(messageLabel, signupLink);

        vbox.getChildren().add(bottomMessage);
    }
}
