package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.DatePicker;

public class CustomDatePicker extends DatePicker {

    public CustomDatePicker(String placeholder) {
        super();
        this.setPromptText(placeholder);
        setPrefWidth(400);
        setPrefHeight(45);
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }
}
