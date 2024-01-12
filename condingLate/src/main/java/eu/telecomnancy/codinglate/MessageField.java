package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.IconButton;
import eu.telecomnancy.codinglate.database.dataController.MessageDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class MessageField {
    private List<List<Message>> MessageUserWithSelectedUserFromUserList;
    private ListView<String> messageListView;
    private BorderPane borderPane;
    private TextField inputField;

    private String emailUser2;

    private int index;

    private Stage stage;


    public MessageField(List<List<Message>> MessageUserWithSelectedUserFromUserList, int index, String emailUser2) {

        this.MessageUserWithSelectedUserFromUserList = MessageUserWithSelectedUserFromUserList;
        this.inputField = new TextField();
        this.messageListView = new ListView<>();
        this.emailUser2 = emailUser2;

        this.borderPane = new BorderPane();


        List<Message> sortedMessageList = MessageUserWithSelectedUserFromUserList.get(index).stream().sorted(Comparator.comparing(Message::getDate)).toList();


        // Ajout des messages triés à la ListView

        sortedMessageList.forEach(message -> messageListView.getItems().add(message.getSender().getFirstname() + " " + message.getSender().getLastname() + ": " + message.getMessage()));

        HBox hBox = new HBox();
        IconButton sendButton = new IconButton("sendButton", "", "/eu/telecomnancy/codinglate/icon/send_icon.png");
        sendButton.initializeButton();
        hBox.getChildren().add(inputField);
        hBox.getChildren().add(sendButton);

        sendButton.setOnAction(event -> {

            Person currentuser = PersonDAO.getInstance().getCurrentUser();
            PersonDAO personController = new PersonDAO();
            String message = this.inputField.getText();


            Message msg = new Message(-1, currentuser, personController.getPersonByEmail(emailUser2),message, LocalDateTime.now());
            MessageDAO messageController =new MessageDAO();
            messageController.insert(msg);
            System.out.print("Message envoyé!");

            //SceneManager sceneManager = new SceneManager(stage);
            //Scene scene = sceneManager.createMessageScene();
            //sceneManager.switchScene(scene);

        });

        borderPane.setCenter(messageListView);
        borderPane.setBottom(hBox);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }


}

