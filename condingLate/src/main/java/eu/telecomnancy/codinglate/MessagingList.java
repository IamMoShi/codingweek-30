package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.IconButton;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class MessagingList {

    private ListView<Message> lastMessageWithUserListView;

    private List<Message> MessageUserWithSelectedUserFromUserList;
    private BorderPane borderPane;

    public MessagingList(ListView<Message> lastMessageWithUserListView, List<Message> MessageUserWithSelectedUserFromUserList) {
        this.lastMessageWithUserListView = lastMessageWithUserListView;
        this.MessageUserWithSelectedUserFromUserList = MessageUserWithSelectedUserFromUserList;



        this.borderPane = new BorderPane();

        // Enregistrez le gestionnaire d'événements pour les clics sur la ListView
        lastMessageWithUserListView.setOnMouseClicked(this::handleListViewClick);



        borderPane.setLeft(lastMessageWithUserListView);


    }

    private void handleListViewClick(MouseEvent event) {
        Message selectedMessage = lastMessageWithUserListView.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            MessageField messageField = new MessageField(MessageUserWithSelectedUserFromUserList);
            borderPane.setCenter(messageField.getBorderPane());
        }
    }



    public BorderPane getBorderPane() {
        return borderPane;
    }

}
