package eu.telecomnancy.codinglate.database.dataController;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.AddressController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Admin;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.scene.control.ListView;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageController {

    public void insert(Message message){
        int generatedId = -1;

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, message.getSender().getEmail());
            pstmt.setString(2, message.getReceiver().getEmail());
            pstmt.setString(3, message.getMessage());
            pstmt.setString(4, message.getDate().toString());


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

    }

    public List<Message> getConversation(String email1, String email2){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();

        PersonController personController = new PersonController();


        try {
            conn = DbConnection.connect();
            String sql = "SELECT * FROM message WHERE receiver = ? AND sender = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email1);
            pstmt.setString(2, email2);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                // Convertir la chaîne en LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("date"), formatter);

                Message msg = new Message(id,personController.getPersonByEmail(email1),personController.getPersonByEmail(email2),message,localDateTime);
                messages.add(msg);
            }
            rs.close();
            pstmt.close();

            sql = "SELECT * FROM message WHERE receiver = ? AND sender = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email2);
            pstmt.setString(2, email1);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                // Convertir la chaîne en LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("date"), formatter);

                Message msg = new Message(id,personController.getPersonByEmail(email1),personController.getPersonByEmail(email2),message,localDateTime);
                if(!messages.contains(msg)){
                    messages.add(msg);
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    //Retourne la liste des personnes avec qui l'utilisateur a une conversation
    public List<Person> getFriends(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Person> persons = new ArrayList<>();

        PersonController personController = new PersonController();
        Person currentuser = personController.getCurrentUser();

        if(currentuser!=null) {
            try {
                conn = DbConnection.connect();
                String sql = "SELECT sender FROM message WHERE receiver = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, currentuser.getEmail());


                rs = pstmt.executeQuery();

                if (rs.next()) {
                    String sender = rs.getString("sender");

                    Person person = personController.getPersonByEmail(sender);

                    if (!persons.contains(person)) {
                        persons.add(person);
                    }

                }
                rs.close();
                pstmt.close();

                sql = "SELECT receiver FROM message WHERE sender = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, currentuser.getEmail());


                rs = pstmt.executeQuery();

                if (rs.next()) {

                    String receiver = rs.getString("receiver");

                    Person person = personController.getPersonByEmail(receiver);

                    if (!persons.contains(person)) {
                        persons.add(person);
                    }

                }


            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return persons;

    }

    //Retourne la liste des emails des utilisateurs avec qui l'utilisateur courant a eu une conversation
    public ListView<String> getListofFriends() {
        List<Person> persons = this.getFriends();

        ListView<String> emaillist = new ListView<>();

        List<String> emails = persons.stream()
                .map(Person::getEmail)
                .toList();

        emaillist.getItems().addAll(emails);

        return emaillist;

    }


}