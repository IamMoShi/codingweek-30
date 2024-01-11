package eu.telecomnancy.codinglate.database.dataController;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.scene.control.ListView;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageController {



    public void insert(Message message) {
        int generatedId = -1;

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, message.getSender().getId());
            pstmt.setInt(2, message.getReceiver().getId());
            pstmt.setString(3, message.getMessage());
            pstmt.setObject(4, message.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));


            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                    } else {
                        System.out.println("Aucun ID généré trouvé.");
                    }
                }
            } else {
                System.out.println("L'insertion a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }

        message.setId(generatedId);

    }

    public ArrayList<Message> getConversation(Person user, Person other) {
        ArrayList<Message> conversation = new ArrayList<>();

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)")) {

            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, other.getId());
            pstmt.setInt(3, other.getId());
            pstmt.setInt(4, user.getId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("id");


                String content = rs.getString("message");
                LocalDateTime startDate = LocalDateTime.parse(rs.getString("date"));

                // Créer un objet Message et l'ajouter à la liste
                if (rs.getInt("receiver") == user.getId()) {
                    Message message = new Message(messageId, other, user, content, startDate);
                    conversation.add(message);
                } else {
                    Message message = new Message(messageId, user, other, content, startDate);
                    conversation.add(message);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conversation;
    }


    public ArrayList<Person> getConversationList(Person person) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT DISTINCT sender, receiver FROM message WHERE sender = ? OR receiver = ?")) {

            pstmt.setInt(1, person.getId());
            pstmt.setInt(2, person.getId());

            ResultSet rs = pstmt.executeQuery();

            ArrayList<Integer> ids = new ArrayList<>();

            while (rs.next()) {
                int sender = Integer.parseInt(rs.getString("sender"));
                int receiver = Integer.parseInt(rs.getString("receiver"));

                if (sender == person.getId()) {
                    // Regarde dans la liste si la personne n'est pas déjà présente
                    if (!ids.contains(receiver)) {
                        ids.add(receiver);
                    }
                } else {
                    // Regarde dans la liste si la personne n'est pas déjà présente
                    if (!ids.contains(sender)) {
                        ids.add(sender);
                    }
                }
            }

            ArrayList<Person> people = new ArrayList<>();
            for (Integer id : ids) {
                Person p = PersonController.getInstance().getPersonById(id);
                if (p != null) {
                    people.add(p);
                }
            }

            return people;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}