package eu.telecomnancy.codinglate.database.dataController.user;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Admin;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;


import java.sql.*;

public class PersonController {

    private static PersonController instance = null;

    private Person CurrentUser;

    public static PersonController getInstance() {
        if (instance == null) {
            instance = new PersonController();
        }
        return instance;
    }

    public Person getCurrentUser(){
        return CurrentUser;
    }

    public void setCurrentUser(Person currentUser){
        this.CurrentUser=currentUser;
    }

    public void insert(Person person) {
        int generatedId = -1; // Valeur par défaut en cas d'échec de l'insertion

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO user (firstname, lastname, email, password, phone, balance, admin, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, person.getFirstname());
            pstmt.setString(2, person.getLastname());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getPassword());
            pstmt.setString(5, person.getPhone());
            pstmt.setFloat(6, person.getBalance());
            pstmt.setInt(7, (person instanceof Admin) ? 1 : 0);

            if (person.getAddress().getId() == -1) {
                // L'adresse n'existe pas encore dans la base de données
                AddressController addressController = new AddressController();
                addressController.insert(person.getAddress());
                pstmt.setInt(8, person.getAddress().getId());
            } else {
                // L'adresse existe déjà dans la base de données
                pstmt.setInt(8, person.getAddress().getId());
            }

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

        person.setId(generatedId);
    }


    public void update(Person person) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE user SET firstname = ?, lastname = ?, email = ?, password = ?, phone = ?, balance = ?, admin = ? WHERE id = ?")) {

            pstmt.setString(1, person.getFirstname());
            pstmt.setString(2, person.getLastname());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getPassword());
            pstmt.setString(5, person.getPhone());
            pstmt.setFloat(6, person.getBalance());
            pstmt.setInt(7, (person instanceof Admin) ? 1 : 0);
            pstmt.setInt(8, person.getId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Person updated successfully");
            } else {
                System.out.println("No person with the given ID found for update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }


    public Person getPersonById(int userId) {
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

                Address address = new AddressController().getAddressByUSer(id); // Vous devez obtenir l'adresse à partir de la base de données si nécessaire

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

        return person;
    }


    public boolean isEmailUsed(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean emailUsed = false;

        try {
            conn = DbConnection.connect();

            String sql = "SELECT * FROM user WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            // Vérifier si l'e-mail existe dans la base de données
            if (rs.next()) {
                emailUsed = true;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        } finally {
            // Fermer les ressources (ResultSet, PreparedStatement et Connection)
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception selon vos besoins
            }
        }

        return emailUsed;
    }


    public void delete(Person person) {
        deletePersonById(person.getId());
    }

    public void deletePersonById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbConnection.connect();
            String sql = "DELETE FROM user WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Person with ID " + userId + " deleted successfully");
            } else {
                System.out.println("No person with the given ID found for deletion");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        } finally {
            try {
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

    public Boolean VerifierBase(String email, String password) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Boolean utilisateurTrouve = false;

        try {
            conn = DbConnection.connect();

            String sql = "SELECT * FROM user WHERE email = ? AND password= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            utilisateurTrouve = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

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


        return utilisateurTrouve;

    }

    public Person getPersonByEmail(String email) {
        Person person = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.connect();
            String sql = "SELECT * FROM user WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                float balance = rs.getFloat("balance");
                int adminValue = rs.getInt("admin");
                Address address = new AddressController().getAddressById(rs.getInt("address"));

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

        return person;
    }



}
