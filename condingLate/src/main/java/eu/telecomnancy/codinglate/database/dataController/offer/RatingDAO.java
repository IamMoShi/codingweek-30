package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.offer.Rating;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingDAO {
    public void insert(Rating rating) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO rating (offer, user, value, comment) VALUES (?, ?, ?, ?)")) {

            if (rating.getOffer().getId() == -1) {
                throw new IllegalArgumentException("L'offre n'a pas été insérée dans la base de données.");
            }

            if (rating.getUser().getId() == -1) {
                throw new IllegalArgumentException("L'utilisateur n'a pas été inséré dans la base de données.");
            }

            pstmt.setInt(1, rating.getOffer().getId());
            pstmt.setInt(2, rating.getUser().getId());
            pstmt.setInt(3, rating.getValue());
            pstmt.setString(4, rating.getComment());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Rating inserted successfully");

                rating.setId(pstmt.getGeneratedKeys().getInt(1));
            } else {
                System.out.println("L'insertion a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    public int getNumberOfEvaluation(Person person) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM rating WHERE user = ?")) {

            pstmt.setInt(1, person.getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        return -1;
    }

    public double getAverageEvaluation(Person person) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT AVG(value) FROM rating WHERE user = ?")) {

            pstmt.setInt(1, person.getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        return -1;
    }
}
