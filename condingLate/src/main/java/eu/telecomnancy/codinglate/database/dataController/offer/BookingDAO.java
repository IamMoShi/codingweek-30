package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.BookingStatus;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

            if (booking.getOffer().getId() == -1)
                throw new SQLException("L'offre doit être insérée dans la base de données avant l'insertion de la réservation.");
            pstmt.setInt(1, booking.getOffer().getId());
            if (booking.getUser().getId() == -1)
                throw new SQLException("L'utilisateur doit être inséré dans la base de données avant l'insertion de la réservation.");
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

    private Booking createBooking(ResultSet rs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Booking booking = null;
        try {
            int id = rs.getInt("id");
            int offerId = rs.getInt("offer");
            Offer offer = new OfferController().getOfferById(offerId);
            int userId = rs.getInt("user");
            User user = (User) new PersonController().getPersonById(userId);
            LocalDateTime startingDate = null;
            if (rs.getObject("startingDate") != null) startingDate = LocalDateTime.parse(rs.getString("startingDate"), formatter);
            LocalDateTime endingDate = null;
            if (rs.getObject("endingDate") != null) endingDate = endingDate = LocalDateTime.parse(rs.getString("endingDate"), formatter);
            BookingStatus status = BookingStatus.values()[rs.getInt("status")];
            booking = new Booking(id, offer, user, startingDate, endingDate, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return booking;
    }

    public ArrayList<Booking> getBookingsByUser(int userId) {
        ArrayList<Booking> bookings = new ArrayList<>();

        try (Connection conn = DbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM booking WHERE user = ?")) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBooking(rs);
                    if (booking != null) bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public ArrayList<Booking> getAllBooking() {
        ArrayList<Booking> bookings = new ArrayList<>();

        try {
            Connection conn = DbConnection.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM booking");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = createBooking(rs);
                if (booking != null) bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    public ArrayList<Booking> getBookingsByOffer(Offer offer) {
        ArrayList<Booking> bookings = new ArrayList<>();

        try (Connection conn = DbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM booking WHERE offer = ?")) {

            stmt.setInt(1, offer.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBooking(rs);
                    if (booking != null) bookings.add(booking);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    public Boolean OfferAvailableChecker(Offer offer, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            System.out.println("La date de début doit être avant la date de fin.");
            return false;
        }
        // Test si l'offre est globalement disponible à cette date
        if (endDate.isAfter(offer.getEndingDate())) {
            System.out.println("L'offre n'est pas planifié jusqu'à cette date.");
            return false;
        }
        if (startDate.isBefore(offer.getStartingDate())) {
            System.out.println("L'offre n'est pas planifié à partir de cette date.");
            return false;
        }

        ArrayList<Booking> bookings = getBookingsByOffer(offer);
        for (Booking booking : bookings) {
            LocalDateTime bookingStartDate = booking.getStartingDate();
            LocalDateTime bookingEndDate = booking.getEndingDate();
            if (startDate.isAfter(bookingStartDate) && startDate.isBefore(bookingEndDate)) {
                return false;
            }
            if (endDate.isAfter(bookingStartDate) && endDate.isBefore(bookingEndDate)) {
                return false;
            }
            if (startDate.isBefore(bookingStartDate) && endDate.isAfter(bookingEndDate)) {
                return false;
            }
        }
        System.out.println("L'offre est disponible à cette date.");
        return true;
    }

}
