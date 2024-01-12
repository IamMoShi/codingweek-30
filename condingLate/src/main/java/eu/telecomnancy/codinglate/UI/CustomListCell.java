package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.database.dataController.MessageDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CustomListCell extends ListCell<Person> {

    public CustomListCell(BorderPane borderPane) {
        setOnMouseClicked(event -> {

            if (!isEmpty() && getItem() != null) {
                MessageDAO messageController = new MessageDAO();
                ArrayList<Message> ListMessage = messageController.getConversation(getItem(), PersonDAO.getInstance().getCurrentUser());

                VBox root = new VBox(10);
                root.setPadding(new Insets(10));

                VBox sendroot = new VBox(10);
                sendroot.setPadding(new Insets(0, 400, 0, 240));

                VBox chatBox = new VBox(5);

                chatBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");

                for (Message message : ListMessage) {
                    if (message.getSender().equals(PersonDAO.getInstance().getCurrentUser())) {
                        appendMessage(message.getMessage(), true, chatBox);
                    }
                    else {
                        appendMessage(message.getMessage(), false, chatBox);
                    }
                }

                ScrollPane scrollPane = new ScrollPane(chatBox);
                scrollPane.setFitToWidth(true);

                HBox messageInputBox = createMessageInputBox(chatBox);

                root.getChildren().addAll(scrollPane);
                sendroot.getChildren().addAll(messageInputBox);


                borderPane.setCenter(root);
                borderPane.setBottom(sendroot);

            }


        });
    }

    private void appendMessage(String message, boolean sentByUser, VBox chatBox) {
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 5px; -fx-border-radius: 5px;");

        if (sentByUser) {
            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setMaxWidth(1000); // Définissez la largeur maximale pour l'alignement à droite
        } else {
            messageLabel.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setMaxWidth(1000); // Définissez la largeur maximale pour l'alignement à gauche
        }

        chatBox.getChildren().add(messageLabel);
    }

    private HBox createMessageInputBox(VBox chatBox) {
        HBox messageInputBox = new HBox(10);
        messageInputBox.setAlignment(Pos.CENTER_LEFT);

        CustomTextField messageField = new CustomTextField("Ecrivez votre message ici");
        messageField.setPrefWidth(700);
        messageField.setPromptText("Type your message...");

        IconButton sendButton = new IconButton("Send", "","/eu/telecomnancy/codinglate/icon/send_icon.png");
        sendButton.initializeButton();
        messageInputBox.getChildren().addAll(messageField, sendButton);

        sendButton.setOnAction(event -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                MessageDAO messageController = new MessageDAO();
                Message message1 = new Message(PersonDAO.getInstance().getCurrentUser(),getItem(),message, LocalDateTime.now());
                messageController.insert(message1);

                appendMessage(message, true, chatBox);
                messageField.clear();


            }
        });

        VBox.setMargin(messageInputBox, new Insets(10));

        return messageInputBox;
    }
    @Override
    protected void updateItem(Person person, boolean empty) {
        super.updateItem(person, empty);
        setPrefWidth(400);
        setPrefHeight(45);

        if (person == null || empty) {
            setText(null);
            HBox customLayout = new HBox();
            setGraphic(customLayout);
            setPrefHeight(100);
        } else {
            HBox customLayout = new HBox();

            // Afficher le nom et l'email de la personne
            Label nameLabel = new Label(person.getLastname());
            Label firstnameLabel = new Label(person.getFirstname());
            Label emailLabel = new Label(person.getEmail());

            customLayout.getChildren().addAll(emailLabel);

            // Utiliser cette mise en page personnalisée comme élément graphique dans la cellule
            setGraphic(customLayout);
            setPrefHeight(100);
        }
    }
}






