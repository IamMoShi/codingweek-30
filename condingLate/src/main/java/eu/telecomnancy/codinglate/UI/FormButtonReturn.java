package eu.telecomnancy.codinglate.UI;

import javafx.geometry.Pos;

public class FormButtonReturn extends AbstractButton{
    public FormButtonReturn(String buttonName, String Label) {
        super(buttonName, Label, null);
        setPrefWidth(200);
        setPrefHeight(20);

    }

    @Override
    protected void handleAction() {
    }
}
