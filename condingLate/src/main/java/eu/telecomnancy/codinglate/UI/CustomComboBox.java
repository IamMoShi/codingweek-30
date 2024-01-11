package eu.telecomnancy.codinglate.UI;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CustomComboBox<T> extends ComboBox<T> {
    public CustomComboBox(ObservableList<T> items) {
        super(items);
        initialize();
    }

    private void initialize() {
        setPrefWidth(400);
        setPrefHeight(45);
        setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-pref-width: 400px;");
    }
}
