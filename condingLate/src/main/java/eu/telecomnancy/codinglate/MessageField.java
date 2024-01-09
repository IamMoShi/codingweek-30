package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.IconButton;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageField {
    private List<Message> MessageUserWithSelectedUserFromUserList;

    private ListView<String> messageListView;
    private BorderPane borderPane;
    private TextField inputField;


    public MessageField(List<Message> MessageUserWithSelectedUserFromUserList) {
        this.MessageUserWithSelectedUserFromUserList = MessageUserWithSelectedUserFromUserList;
        this.inputField = new TextField();
        this.messageListView = new ListView<>();

        this.borderPane = new BorderPane();

        List<Message> sortedMessageList = MessageUserWithSelectedUserFromUserList.stream()
                .sorted(Comparator.comparing(Message::getDate))
                .toList();

        // Ajout des messages triés à la ListView
        sortedMessageList.forEach(message -> messageListView.getItems().add(message.getSender() + ": " + message.getMessage()));

        HBox hBox = new HBox();
        IconButton sendButton = new IconButton("sendButton", "", "/eu/telecomnancy/codinglate/icon/send_icon.png");
        sendButton.initializeButton();
        hBox.getChildren().add(inputField);
        hBox.getChildren().add(sendButton);

        borderPane.setCenter(messageListView);
        borderPane.setBottom(hBox);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}


