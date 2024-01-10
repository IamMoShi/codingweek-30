package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.PasswordField;

public class CustomPasswordField extends PasswordField {

    public CustomPasswordField(String placeholder) {
        super();
        this.setPromptText(placeholder);
        setPrefWidth(400);
        setPrefHeight(45);
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }
}
