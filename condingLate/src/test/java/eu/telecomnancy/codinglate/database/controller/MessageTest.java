package eu.telecomnancy.codinglate.database.controller;

import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Admin;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataController.MessageController;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private final MessageController messageController = new MessageController();
    private final PersonController personController = new PersonController();

    @Test
    public void testInsertMessage() {
        // Créer deux utilisateurs pour simuler l'envoi et la réception du message
        User sender = new User("sender", "SenderLastName", "sender@example.com", "sender123", new Address("Sender Address"));
        Admin receiver = new Admin("receiver", "ReceiverLastName", "receiver@example.com", "receiver123", new Address("Receiver Address"));
        personController.insert(sender);
        personController.insert(receiver);

        // Créer un message
        Message newMessage = new Message(-1, sender, receiver, "Test Message", LocalDateTime.now());

        // Insérer le message
        messageController.insert(newMessage);

        // Vérifier si l'ID du message a été généré
        assertNotEquals(-1, newMessage.getId());

        // Récupérer le message de la base de données
        List<Message> retrievedMessages = messageController.getConversation(sender.getEmail(), receiver.getEmail());

        // Vérifier si le message récupéré correspond au message inséré
        assertTrue(retrievedMessages.contains(newMessage));
    }

    @Test
    public void testGetConversation() {
        // Créer deux utilisateurs pour simuler l'envoi et la réception du message
        User sender = new User("sender", "SenderLastName", "sender@example.com", "sender123", new Address("Sender Address"));
        Admin receiver = new Admin("receiver", "ReceiverLastName", "receiver@example.com", "receiver123", new Address("Receiver Address"));
        personController.insert(sender);
        personController.insert(receiver);

        // Créer quelques messages
        Message message1 = new Message(-1, sender, receiver, "Message 1", LocalDateTime.now());
        Message message2 = new Message(-1, receiver, sender, "Message 2", LocalDateTime.now().plusMinutes(1));
        Message message3 = new Message(-1, sender, receiver, "Message 3", LocalDateTime.now().plusMinutes(2));

        // Insérer les messages
        messageController.insert(message1);
        messageController.insert(message2);
        messageController.insert(message3);

        // Récupérer la conversation
        List<Message> conversation = messageController.getConversation(sender.getEmail(), receiver.getEmail());

        // Vérifier si la conversation contient les messages insérés
        assertTrue(conversation.contains(message1));
        assertFalse(conversation.contains(message2)); // Le message 2 est envoyé par le récepteur
        assertTrue(conversation.contains(message3));
    }

    @Test
    public void testGetFriends() {
        // Créer deux utilisateurs pour simuler la liste d'amis
        User user = new User("user", "UserLastName", "user@example.com", "user123", new Address("User Address"));
        Admin friend1 = new Admin("friend1", "Friend1LastName", "friend1@example.com", "friend123", new Address("Friend1 Address"));
        Admin friend2 = new Admin("friend2", "Friend2LastName", "friend2@example.com", "friend123", new Address("Friend2 Address"));
        personController.insert(user);
        personController.insert(friend1);
        personController.insert(friend2);

        // Créer quelques messages
        Message message1 = new Message(-1, user, friend1, "Message 1", LocalDateTime.now());
        Message message2 = new Message(-1, friend1, user, "Message 2", LocalDateTime.now().plusMinutes(1));
        Message message3 = new Message(-1, user, friend2, "Message 3", LocalDateTime.now().plusMinutes(2));

        // Insérer les messages
        messageController.insert(message1);
        messageController.insert(message2);
        messageController.insert(message3);

        // Récupérer la liste d'amis
        //List<Person> friends = messageController.getFriends();

        // Vérifier si la liste d'amis contient les utilisateurs avec qui l'utilisateur a eu une conversation
        //assertTrue(friends.contains(friend1));
        //assertTrue(friends.contains(friend2));
        //assertFalse(friends.contains(user)); // L'utilisateur lui-même ne doit pas être inclus
    }

    @Test
    public void testGetListOfFriends() {
        // Créer deux utilisateurs pour simuler la liste d'amis
        User user = new User("user", "UserLastName", "user@example.com", "user123", new Address("User Address"));
        Admin friend1 = new Admin("friend1", "Friend1LastName", "friend1@example.com", "friend123", new Address("Friend1 Address"));
        Admin friend2 = new Admin("friend2", "Friend2LastName", "friend2@example.com", "friend123", new Address("Friend2 Address"));
        personController.insert(user);
        personController.insert(friend1);
        personController.insert(friend2);

        // Créer quelques messages
        Message message1 = new Message(-1, user, friend1, "Message 1", LocalDateTime.now());
        Message message2 = new Message(-1, friend1, user, "Message 2", LocalDateTime.now().plusMinutes(1));
        Message message3 = new Message(-1, user, friend2, "Message 3", LocalDateTime.now().plusMinutes(2));

        // Insérer les messages
        messageController.insert(message1);
        messageController.insert(message2);
        messageController.insert(message3);

        // Récupérer la liste d'amis sous forme de ListView
        //ListView<String> listOfFriends = messageController.getListofFriends();

        // Vérifier si la liste d'amis contient les emails des utilisateurs avec qui l'utilisateur a eu une conversation
        //assertTrue(listOfFriends.getItems().contains(friend1.getEmail()));
        //assertTrue(listOfFriends.getItems().contains(friend2.getEmail()));
        //assertFalse(listOfFriends.getItems().contains(user.getEmail())); // L'utilisateur lui-même ne doit pas être inclus
    }

}
