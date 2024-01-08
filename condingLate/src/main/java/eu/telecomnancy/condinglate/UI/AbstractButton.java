package eu.telecomnancy.condinglate.UI;

import javafx.scene.control.Button;

public abstract class AbstractButton extends Button {
    private String buttonName;
    private String Label;

    public AbstractButton(String buttonName, String Label) {
        this.buttonName = buttonName;
        this.Label = Label;
    }

    private void initializeButton() {
        this.setText(this.Label);

        customButtonStyle();

        this.setOnAction(e -> handleAction());

    }

    protected void customButtonStyle(){
        this.setStyle("-fx-background-color: #ffffff; /* Couleur de fond */\n" +
                "        -fx-text-fill: black; /* Couleur du texte */\n" +
                "        -fx-font-size: 14px; /* Taille de la police */\n" +
                "        -fx-padding: 8px 16px; /* Espacement intérieur du bouton */\n" +
                "        -fx-border-radius: 5px; /* Rayon des coins */\n" +
                "        -fx-cursor: hand; /* Curseur au survol du bouton */");
}


    protected abstract void handleAction();


}
