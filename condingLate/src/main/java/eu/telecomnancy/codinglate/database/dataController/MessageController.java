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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessageController {

    public void insert(Message message){
        int generatedId = -1; // Valeur par défaut en cas d'échec de l'insertion

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Convertir la String en LocalDate
                LocalDate localDate = LocalDate.parse(rs.getString("date"), formatter);

                Message msg = new Message(id,personController.getPersonByEmail(email1),personController.getPersonByEmail(email2),message,localDate);
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
        }}
}

    //Affiche la liste des emails des utilisateurs avec qui l'utilisateur courant a eu une conversation
    public ListView<String> getListofFriends(){
        Person person = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ListView<String> list = new ListView<>();

        try {
            conn = DbConnection.connect();
            String sql = "SELECT * FROM  WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                float balance = rs.getFloat("balance");
                int adminValue = rs.getInt("admin");

                Address address = null; // Vous devez obtenir l'adresse à partir de la base de données si nécessaire

                if (adminValue == 1) {
                    // L'utilisateur est un administrateur
                    person = new Admin(id, firstname, lastname, email, password, phone, balance, address);
                } else {
                    // L'utilisateur est un utilisateur standard
                    person = new User(id, firstname, lastname, email, password, phone, balance, address);
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

        public List<Person> getFriends(){
            Person person = null;
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = DbConnection.connect();
                String sql = "SELECT * FROM user WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    float balance = rs.getFloat("balance");
                    int adminValue = rs.getInt("admin");

                    Address address = null; // Vous devez obtenir l'adresse à partir de la base de données si nécessaire

                    if (adminValue == 1) {
                        // L'utilisateur est un administrateur
                        person = new Admin(id, firstname, lastname, email, password, phone, balance, address);
                    } else {
                        // L'utilisateur est un utilisateur standard
                        person = new User(id, firstname, lastname, email, password, phone, balance, address);
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


    }
