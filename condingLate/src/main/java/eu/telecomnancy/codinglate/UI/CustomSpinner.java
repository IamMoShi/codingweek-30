package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.Spinner;

public class CustomSpinner extends Spinner<Integer> {

    public CustomSpinner(int min, int max, int initial, int step) {
        super(min, max, initial, step);
        this.setStyle("-fx-text-fill: black; -fx-font-size: 16px; ");
        this.setEditable(true);
        this.getEditor().setPrefWidth(400);
    }
}
