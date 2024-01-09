package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.DatePicker;

public class CustomDatePicker extends DatePicker {

    public CustomDatePicker(String placeholder) {
        super();
        this.setPromptText(placeholder);
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }
}
