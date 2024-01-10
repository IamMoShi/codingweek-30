package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.IconButton;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageField {
    private List<List<Message>> MessageUserWithSelectedUserFromUserList;
    private ListView<String> messageListView;
    private BorderPane borderPane;
    private TextField inputField;

    private int index;

    private Stage stage;


    public MessageField(List<List<Message>> MessageUserWithSelectedUserFromUserList, int index) {
        this.MessageUserWithSelectedUserFromUserList = MessageUserWithSelectedUserFromUserList;
        this.inputField = new TextField();
        this.messageListView = new ListView<>();

        this.borderPane = new BorderPane();

        List<Message> sortedMessageList = MessageUserWithSelectedUserFromUserList.get(index).stream().sorted(Comparator.comparing(Message::getDate)).toList();


        // Ajout des messages triés à la ListView

        sortedMessageList.forEach(message -> messageListView.getItems().add(message.getSender() + ": " + message.getMessage()));

        HBox hBox = new HBox();
        IconButton sendButton = new IconButton("sendButton", "", "/eu/telecomnancy/codinglate/icon/send_icon.png");
        sendButton.initializeButton();
        hBox.getChildren().add(inputField);
        hBox.getChildren().add(sendButton);

        sendButton.setOnAction(event -> {
            //PersonController personController = new PersonController();
            //Message message = new Message(-1, personController.getCurrentUser(), personController.getPersonByEmail(), String message, LocalDate date);
            SceneManager sceneManager = new SceneManager(stage);
            Scene scene = sceneManager.createMessageScene();
            sceneManager.switchScene(scene);
        });

        borderPane.setCenter(messageListView);
        borderPane.setBottom(hBox);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}

