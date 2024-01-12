package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomCellViewBooking extends ListCell<Booking> {

    private HBox container;
    private VBox textContainer;
    private Label titleLabel;
    private Label dateLabel;
    private FormButton rateButton;

    public CustomCellViewBooking(Booking booking) {
        container = new HBox(10);
        textContainer = new VBox(5);
        titleLabel = new Label();
        dateLabel = new Label();
        rateButton = new FormButton("Rate", "Evaluer");
        rateButton.initializeButton();
        rateButton.setMinWidth(100);
        rateButton.setMaxWidth(100);
        rateButton.setStyle("-fx-background-color: #808080; ");


        titleLabel.setStyle("-fx-font-weight: bold");
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        textContainer.getChildren().addAll(titleLabel, dateLabel);
        container.getChildren().addAll(textContainer, rateButton);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setStyle("-fx-padding: 10px;");

        rateButton.setOnAction(event -> handleRateButtonClick(booking));
    }

    private void handleRateButtonClick(Booking booking) {
        Offer offer = booking.getOffer();
        SceneManager sceneManager = new SceneManager((Stage) getScene().getWindow());
        sceneManager.switchScene(sceneManager.createSceneRateOffer(offer));
    }

    @Override
    protected void updateItem(Booking booking, boolean empty) {
        super.updateItem(booking, empty);

        if (empty || booking == null) {
            setGraphic(null);
            setText(null);
        } else {
            titleLabel.setText(booking.getOffer().getTitle());
            dateLabel.setText("Date: " + booking.getStartingDate().toLocalDate() +
                    " au " + booking.getEndingDate().toLocalDate());

            setGraphic(container);
        }
    }
}


