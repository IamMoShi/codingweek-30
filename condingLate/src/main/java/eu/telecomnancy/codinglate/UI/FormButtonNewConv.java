package eu.telecomnancy.codinglate.UI;

import javafx.geometry.Pos;

public class FormButtonNewConv extends AbstractButton{
    public FormButtonNewConv(String buttonName, String Label) {
        super(buttonName, Label, null);
        setPrefWidth(200);
        setMinWidth(200);
        setPrefHeight(50);
        setTranslateY(50);
        setTranslateX(20);

    }

    @Override
    protected void handleAction() {
    }
}
