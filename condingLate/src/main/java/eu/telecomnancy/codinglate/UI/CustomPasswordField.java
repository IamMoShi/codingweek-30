package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.PasswordField;

public class CustomPasswordField extends PasswordField {

    public CustomPasswordField(String placeholder) {
        super();
        this.setPromptText(placeholder);
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }
}
