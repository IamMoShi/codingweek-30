package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CustomCellViewOurBooking extends ListCell<Booking> {

    private HBox container;
    private VBox textContainer;
    private Label titleLabel;
    private Label dateLabel;

    public CustomCellViewOurBooking() {
        container = new HBox(10);
        textContainer = new VBox(5);
        titleLabel = new Label();
        dateLabel = new Label();

        titleLabel.setStyle("-fx-font-weight: bold");
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        textContainer.getChildren().addAll(titleLabel, dateLabel);
        container.getChildren().add(textContainer); // Ajout du textContainer au conteneur principal
        container.setAlignment(Pos.CENTER_LEFT);
        container.setStyle("-fx-padding: 10px;");
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
