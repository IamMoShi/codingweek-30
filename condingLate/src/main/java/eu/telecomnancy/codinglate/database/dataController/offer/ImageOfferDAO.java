package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImageOfferDAO {
    public void insert(String imageUrl, int offerId) {
        try (Connection conn = DbConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO imageOffer (path, offer) VALUES (?, ?)")) {
            pstmt.setString(1, imageUrl);
            pstmt.setInt(2, offerId);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Image inserted successfully into imageOffer.");
            } else {
                System.out.println("Failed to insert image into imageOffer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertImagePathIntoDatabase(String localImagePath, int offerId) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO imageOffer (path, offer) VALUES (?, ?)")) {

            pstmt.setString(1, localImagePath);
            pstmt.setInt(2, offerId);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Image inserted successfully into imageOffer.");
            } else {
                System.out.println("Failed to insert image into imageOffer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getImages(Offer offer) {
        ArrayList<String> imageUrls = new ArrayList<>();

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT path FROM imageOffer WHERE offer = ?")) {

            pstmt.setInt(1, offer.getId());

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String imageUrl = resultSet.getString("path");
                    imageUrls.add(imageUrl);
                }
            }

            // Mettez à jour la liste d'images de l'objet "Offer" avec les URL récupérées
            offer.setImages(imageUrls);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageUrls;
    }
}
