package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class IconButton extends AbstractButton{
    public IconButton(String buttonName, String Label, String iconPath) {
        super(buttonName, Label, iconPath);

        // Attribuer le ContextMenu au bouton
    }

    @Override
    protected void customButtonStyle(){
        this.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 5 5;"  +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;");
    }

    @Override
    protected void handleAction() {
    }
}
