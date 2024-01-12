package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
import eu.telecomnancy.codinglate.calendar.ReservationCalendarView;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.time.LocalDateTime;
import java.util.Calendar;

public class BookButton extends HBox {
    public BookButton(Scene previousScene, ReservationCalendarView calendar) {
        super();
        returnButtonAdd(previousScene);
        bookButtonAdd(calendar, previousScene);
        // Centrer le bouton
        this.setSpacing(10);
        this.setStyle("-fx-alignment: center;");
        setPadding(new javafx.geometry.Insets(100, 10, 100, 10));
    }

    private void bookButtonAdd(ReservationCalendarView calendar, Scene previousScene) {
        if (PersonController.getInstance().getCurrentUser() == null) {
            SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            sceneManager.switchScene(sceneManager.createSceneConnexion());

        }
        FormButton bookButton = new FormButton("Book", "Book");
        bookButton.initializeButton();
        bookButton.setOnAction(event -> {
            Booking booking = calendar.getBooking();
            if (booking == null) {
                alert("Veuillez sélectionner une période contiguë et unique pour réserver");
            } else {
                if (booking.getStartingDate().isBefore(LocalDateTime.now())) {
                    alert("La date de début de la réservation doit être dans le futur");
                    return;
                }
                Boolean available = new BookingDAO().OfferAvailableChecker(calendar.getOffer(), booking.getStartingDate(), booking.getEndingDate());
                if (!available) {
                    alert("Cette période n'est pas disponible");
                    return;
                }

                new BookingDAO().insert(booking);
                alertSuccess("La réservation a été effectuée avec succès");
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                sceneManager.switchScene(previousScene);

            }
        });
        this.getChildren().add(bookButton);
    }

    private void alertSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
