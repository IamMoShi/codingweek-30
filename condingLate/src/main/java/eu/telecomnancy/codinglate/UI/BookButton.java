package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
import eu.telecomnancy.codinglate.calendar.ReservationCalendarView;
import javafx.scene.Scene;
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
            System.out.println(calendar.getBooking().getStartingDate());
        });
        this.getChildren().add(bookButton);
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
