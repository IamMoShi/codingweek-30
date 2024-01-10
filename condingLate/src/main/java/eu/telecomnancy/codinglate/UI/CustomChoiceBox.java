package eu.telecomnancy.codinglate.UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class CustomChoiceBox extends ChoiceBox<String> {


    public CustomChoiceBox(ObservableList<String> items) {
        super(items);
        initialize();
    }

    private void initialize() {
        this.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-pref-width: 230px;");
    }
}
