package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
import eu.telecomnancy.codinglate.calendar.ReservationCalendarView;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.util.Calendar;

public class BookButton extends HBox {
    public BookButton(Scene previousScene, ReservationCalendarView calendar) {
        super();
        returnButtonAdd(previousScene);
        bookButtonAdd(calendar);
        // Centrer le bouton
        this.setSpacing(10);
        this.setStyle("-fx-alignment: center;");
        setPadding(new javafx.geometry.Insets(100, 10, 100, 10));
    }

    private void bookButtonAdd(ReservationCalendarView calendar) {
        FormButton bookButton = new FormButton("Book", "Book");
        bookButton.initializeButton();
        bookButton.setOnAction(event -> {
            Booking booking = calendar.getBooking();
            if (booking == null) {
                alert("Veuillez sélectionner une période contiguë et unique pour réserver");
            } else {
                Boolean available = new BookingDAO().OfferAvailableChecker(calendar.getOffer(), booking.getStartingDate(), booking.getEndingDate());
                if (!available) {
                    alert("Cette période n'est pas disponible");
                }
            }
        });
        this.getChildren().add(bookButton);
    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void returnButtonAdd(Scene previousScene) {
        FormButton returnButton = new FormButton("Return", "Return");
        returnButton.initializeButton();
        returnButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            sceneManager.switchScene(previousScene);
        });
        this.getChildren().add(returnButton);
    }
}
