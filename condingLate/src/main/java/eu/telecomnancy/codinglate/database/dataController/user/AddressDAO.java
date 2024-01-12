package eu.telecomnancy.codinglate.database.dataController.user;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;

import java.sql.*;

public class AddressDAO {

    public void insert(Address address) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO address (address) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, address.getAddress());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        address.setId(generatedId);
                        System.out.println("Address inserted successfully with ID " + generatedId);
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

    public void update(Address address) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE address SET address = ? WHERE id = ?")) {

            pstmt.setString(1, address.getAddress());
            pstmt.setInt(2, address.getId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Address updated successfully");
            } else {
                System.out.println("No address with the given ID found for update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }


    public Address getAddressByUSer(int idUser) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM address WHERE id = ?")) {

            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String addressStr = rs.getString("address");

                return new Address(id, addressStr);
            } else {
                System.out.println("No address with the given ID found for update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        return null;
    }

    public Address getAddressById(int addressId) {
        Address address = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.connect();
            String sql = "SELECT * FROM address WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, addressId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String addressStr = rs.getString("address");

                address = new Address(id, addressStr);
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

        return address;
    }

    public void deleteAddressById(int addressId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbConnection.connect();
            String sql = "DELETE FROM address WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, addressId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Address with ID " + addressId + " deleted successfully");
            } else {
                System.out.println("No address with the given ID found for deletion");
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
}

