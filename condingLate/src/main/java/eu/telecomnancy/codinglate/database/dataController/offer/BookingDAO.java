package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;

import java.sql.*;

public class BookingDAO {
    public void insert(Booking booking) {
        if (booking.getOffer().getId() == -1 || booking.getUser().getId() == -1) {
            System.out.println("L'offre et l'utilisateur doivent être insérés dans la base de données avant l'insertion de la réservation.");
            return;
        }

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO booking (offer, user, startingDate, endingDate, status) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            if (booking.getOffer().getId() == -1) throw new SQLException("L'offre doit être insérée dans la base de données avant l'insertion de la réservation.");
            pstmt.setInt(1, booking.getOffer().getId());
            if (booking.getUser().getId() == -1) throw new SQLException("L'utilisateur doit être inséré dans la base de données avant l'insertion de la réservation.");
            pstmt.setInt(2, booking.getUser().getId());
            if (booking.getStartingDate() == null) pstmt.setNull(3, Types.DATE);
            else pstmt.setObject(3, booking.getStartingDate());
            if (booking.getEndingDate() == null) pstmt.setNull(4, Types.DATE);
            else pstmt.setObject(4, booking.getEndingDate());
            pstmt.setInt(5, booking.getStatus().ordinal());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        booking.setId(generatedId);
                        System.out.println("Réservation insérée avec succès avec l'ID " + generatedId);
                    } else {
                        System.out.println("Aucun ID généré trouvé.");
                    }
                }
            } else {
                System.out.println("L'insertion de la réservation a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

}
