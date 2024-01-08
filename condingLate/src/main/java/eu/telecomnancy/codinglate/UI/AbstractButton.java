package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractButton extends Button {
    private String buttonName;
    private String Label;
    private String iconPath;


    public AbstractButton(String buttonName, String Label, String iconPath) {
        this.iconPath = iconPath;
        this.buttonName = buttonName;
        this.Label = Label;
    }

    public void initializeButton() {
        this.setText(this.Label);
        if (this.iconPath != null) {
            ImageView icon = new ImageView(new Image(this.iconPath));
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            this.setGraphic(icon);
        }


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
