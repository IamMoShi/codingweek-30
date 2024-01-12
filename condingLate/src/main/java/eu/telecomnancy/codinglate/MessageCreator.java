package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.FormButton;
import eu.telecomnancy.codinglate.database.dataController.MessageController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MessageCreator {

    private VBox vbox;
    private Stage stage;

    public VBox getVbox() {
        return this.vbox;
    }

    public MessageCreator(Stage stage) {
        this.stage = stage;
        this.vbox = createFormPane();
        addUIControls((GridPane) vbox.getChildren().get(0));
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

    private void addUIControls(GridPane gridPane) {
        // Ajouter des contrôles à la grille

        CustomTextField emailField = new CustomTextField("Email du destinataire");
        gridPane.add(emailField, 1, 0);

        CustomTextField messageField = new CustomTextField("Message");
        gridPane.add(messageField, 1, 1);

        FormButton sendButton = new FormButton("Send", "Envoyer");
        sendButton.initializeButton();
        sendButton.setPrefWidth(200);
        gridPane.add(sendButton, 1, 2);


        // Gestion d'événements pour le bouton envoyer
        sendButton.setOnAction(e -> {

            String email = emailField.getText();
            String message = messageField.getText();

            PersonController personController = PersonController.getInstance();

            Person currentUser = personController.getCurrentUser();

            if(currentUser instanceof User) {

                User user = (User) currentUser;
                Person user2 = personController.getPersonByEmail(email);

                if(user2 instanceof User){
                    User receiver = (User) user2;


                    Message msg = new Message(-1, user, receiver,message, LocalDateTime.now());
                    MessageController messageController =new MessageController();
                    messageController.insert(msg);

                    System.out.print("Message envoyé!");
                    SceneManager sceneManager = new SceneManager(stage);
                    Scene scene = sceneManager.createMessageScene();
                    sceneManager.switchScene(scene);
                }

            }





        });
    }



}



