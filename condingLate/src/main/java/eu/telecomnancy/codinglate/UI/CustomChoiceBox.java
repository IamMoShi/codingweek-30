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
        setPrefWidth(400);
        setPrefHeight(45);
        this.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-pref-width: 400px;");
    }
}
