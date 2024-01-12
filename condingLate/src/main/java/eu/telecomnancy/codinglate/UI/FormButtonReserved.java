package eu.telecomnancy.codinglate.UI;

import javafx.geometry.Pos;

public class FormButtonReserved extends AbstractButton{
    public FormButtonReserved(String buttonName, String Label) {
        super(buttonName, Label, null);
        setPrefWidth(700);
        setPrefHeight(20);

    }

    @Override
    protected void handleAction() {
    }
}
