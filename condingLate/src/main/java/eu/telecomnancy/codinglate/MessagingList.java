
package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.FormButton;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class MessagingList {

    private ListView<String> UserYouHadAConversationWith;

    private List<List<Message>> MessageUserWithSelectedUserFromUserList;
    private BorderPane borderPane;

    public MessagingList(List<List<Message>> MessageUserWithSelectedUserFromUserList, ListView<String> UserYouHadAConversationWith) {

        this.MessageUserWithSelectedUserFromUserList = MessageUserWithSelectedUserFromUserList;

        this.UserYouHadAConversationWith=UserYouHadAConversationWith;

        this.borderPane = new BorderPane();

        // Enregistrez le gestionnaire d'événements pour les clics sur la ListView
        UserYouHadAConversationWith.setOnMouseClicked(this::handleListViewClick);



        borderPane.setLeft(UserYouHadAConversationWith);




    }

    private void handleListViewClick(MouseEvent event) {
        String selectedUser = UserYouHadAConversationWith.getSelectionModel().getSelectedItem();
        int index = UserYouHadAConversationWith.getSelectionModel().getSelectedIndex();

        if (selectedUser != null) {
            MessageField messageField = new MessageField(MessageUserWithSelectedUserFromUserList, index, selectedUser);
            borderPane.setCenter(messageField.getBorderPane());
        }
    }



    public BorderPane getBorderPane() {
        return borderPane;
    }

}
