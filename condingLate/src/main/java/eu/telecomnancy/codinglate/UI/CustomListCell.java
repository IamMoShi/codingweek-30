package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.w3c.dom.Text;

public class CustomListCell extends ListCell<Message>{
    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        setPrefWidth(400);
        setPrefHeight(45);
        if(item==null || empty){
            setText(null);
            HBox customLayout = new HBox();
            setGraphic(customLayout);
            setPrefHeight(100);
    }
        else{
            HBox customLayout = new HBox();

            // Par exemple, afficher l'auteur et le contenu du message);
            Label messageText = new Label(item.getMessage());


            customLayout.getChildren().add(messageText);

            // Utiliser cette mise en page personnalisée comme élément graphique dans la cellule
            setGraphic(customLayout);
            setPrefHeight(100);
        }
    }
}
