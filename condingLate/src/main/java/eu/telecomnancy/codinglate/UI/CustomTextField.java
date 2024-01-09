package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    public CustomTextField(String placeholder) {
        super();
        this.setPromptText(placeholder);
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }

}
