package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;

import java.sql.*;

public class OfferController {

    public void insert(Service service) {
        if (service.getUser().getId() == -1) {
            System.out.println("L'utilisateur doit être inséré dans la base de données avant l'insertion de l'offre.");
            return;
        }
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO offer (user, title, description, price, priceType, startingDate, endingDate, category, brand, model, condition, year, service) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, service.getUser().getId());
            pstmt.setString(2, service.getTitle());
            pstmt.setString(3, service.getDescription());
            pstmt.setDouble(4, service.getPrice());
            pstmt.setInt(5, service.getPriceType().ordinal());
            pstmt.setObject(6, service.getStartingDate());
            pstmt.setObject(7, service.getEndingDate());
            pstmt.setNull(8, Types.INTEGER); // category (null for Service)
            pstmt.setNull(9, Types.VARCHAR); // brand (null for Service)
            pstmt.setNull(10, Types.VARCHAR); // model (null for Service)
            pstmt.setNull(11, Types.INTEGER); // condition (null for Service)
            pstmt.setNull(12, Types.INTEGER); // year (null for Service)
            pstmt.setInt(13, 1); // offerType (1 for Service)

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        service.setId(generatedId);
                        System.out.println("Service inserted successfully with ID " + generatedId);
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

    public void insert(Product product) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO offer (user, title, description, price, priceType, startingDate, endingDate, category, brand, model, condition, year, service) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, product.getUser().getId());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getPriceType().ordinal());

            if (product.getStartingDate() != null) {
                pstmt.setObject(6, product.getStartingDate());
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            if (product.getEndingDate() != null) {
                pstmt.setObject(7, product.getEndingDate());
            } else {
                pstmt.setNull(7, Types.DATE);
            }

            pstmt.setInt(8, product.getCategory().ordinal()); // category (not null for Product)
            pstmt.setString(9, product.getBrand()); // brand (not null for String)
            pstmt.setString(10, product.getModel()); // model (not null String)

            if (product.getCondition() == null) {
                pstmt.setNull(11, Types.INTEGER);
            } else {
                pstmt.setNull(11, Types.DATE);
            }

            pstmt.setInt(12, product.getYear()); // year (not null int)
            pstmt.setInt(13, 0); // offerType (0 for Product)

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        product.setId(generatedId);
                        System.out.println("Product inserted successfully with ID " + generatedId);
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


}
