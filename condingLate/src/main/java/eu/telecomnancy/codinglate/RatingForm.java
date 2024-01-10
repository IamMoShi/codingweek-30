package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.FormButton;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RatingForm extends HBox {

    private VBox hBox;

    private Stage stage;

    public RatingForm(Stage stage) {
        this.stage = stage;
        this.hBox = createFormPane();
        //addUIControls(hBox);
    }

    private VBox createFormPane() {
        VBox vBox = new VBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        hBox.getChildren().add(gridPane);

        return hBox;
    }

    private void addUIControls(GridPane gridPane) {


        // Ajouter des contrôles à la grille
        Label valueLabel = new Label("Rating Value:");
        ChoiceBox<Integer> valueChoiceBox = new ChoiceBox<>();
        valueChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        gridPane.add(valueLabel, 0, 2);
        gridPane.add(valueChoiceBox, 1, 2);

        Label commentLabel = new Label("Comment:");
        TextArea commentTextArea = new TextArea();
        gridPane.add(commentLabel, 0, 3);
        gridPane.add(commentTextArea, 1, 3);

        FormButton submitButton = new FormButton("Submit","Envoyer");
        submitButton.setOnAction(e -> {
            if (valueChoiceBox.getValue() != null);
        });
        gridPane.add(submitButton, 1, 4);


    }






}
