package eu.telecomnancy.codinglate.UI;

public class SearchBarButton extends AbstractButton{
    public SearchBarButton(String buttonName, String Label) {
        super(buttonName, Label, null);
        setPrefHeight(50);
        setStyle("-fx-font-size: 20");

    }

    @Override
    protected void handleAction() {
    }
}
