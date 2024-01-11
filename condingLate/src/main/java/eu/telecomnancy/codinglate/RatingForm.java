package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.CustomChoiceBox;
import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.FormButton;
import eu.telecomnancy.codinglate.database.dataController.offer.RatingDAO;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Rating;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RatingForm extends HBox {

    private VBox vBox;
    private Stage stage;
    private Label affichagelabel;
    private Offer offer;

    public VBox getVbox() {
        return this.vBox;
    }

    public RatingForm(Stage stage, Offer offer) {
        this.offer = offer;
        this.stage = stage;
        this.vBox = createFormPane();
        this.affichagelabel = new Label("");
        addUIControls((GridPane) vBox.getChildren().get(0));
    }

    private VBox createFormPane() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        vBox.getChildren().add(gridPane);

        return vBox;
    }

    private void addUIControls(GridPane gridPane) {
        Label titleLabel = new Label("Evaluation");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #000000;");
        gridPane.add(titleLabel, 0, 0, 2, 1);

        CustomChoiceBox valueChoiceBox = new CustomChoiceBox(
                FXCollections.observableArrayList("1", "2", "3", "4", "5")
        );
        gridPane.add(valueChoiceBox, 1, 2);

        CustomTextField commentTextArea = new CustomTextField("Commentaire");
        gridPane.add(commentTextArea, 1, 3);
        commentTextArea.setPrefHeight(100);
        commentTextArea.setPrefWidth(400);

        FormButton submitButton = new FormButton("Submit", "Envoyer");
        submitButton.initializeButton();
        submitButton.setOnAction(e -> {
            if (valueChoiceBox.getValue() == null) {
                addNewLabel(gridPane, "Veuillez choisir une note");
            }
            evaluate(valueChoiceBox, commentTextArea);
        });
        gridPane.add(submitButton, 1, 4);
    }

    private void evaluate(CustomChoiceBox customchoiceBox, CustomTextField customTextField) {
        String value = customchoiceBox.getValue();
        String comment = customTextField.getText();

        Rating rating = new Rating(offer, (User) PersonController.getInstance().getCurrentUser(), Integer.parseInt(value));
        rating.setComment(comment);
        RatingDAO ratingDAO = new RatingDAO();
        ratingDAO.insert(rating);
    }

    private void addNewLabel(GridPane root, String str) {
        affichagelabel.setText(str);
        root.add(affichagelabel, 1, 8);
    }
}
