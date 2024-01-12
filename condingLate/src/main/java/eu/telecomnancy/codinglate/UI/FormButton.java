package eu.telecomnancy.codinglate.UI;

import javafx.geometry.Pos;

public class FormButton extends AbstractButton{
    public FormButton(String buttonName, String Label) {
        super(buttonName, Label, null);
        setPrefWidth(400);
        setMinWidth(400);
        setPrefHeight(45);

    }

    @Override
    protected void handleAction() {
    }
}
