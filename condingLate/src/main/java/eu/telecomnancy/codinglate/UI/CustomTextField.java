package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    public CustomTextField(String placeholder) {
        super();
        this.setPromptText(placeholder);
        setPrefWidth(400);
        setPrefHeight(45);
        setFont(javafx.scene.text.Font.font("Arial", 18));
        this.setStyle(" -fx-text-fill: black; -fx-font-size: 16px;");
    }

}
