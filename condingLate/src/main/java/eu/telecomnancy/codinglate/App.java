package eu.telecomnancy.codinglate;


import eu.telecomnancy.codinglate.UI.SearchBar;
import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        SearchBar searchBar = new SearchBar();

        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().add(searchBar);

        // Création de la scène
        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add("eu/telecomnancy/condinglate/css/ui/searchBar.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DbConnection.connect();
        Address address = new Address();
        User user = new User(-1, "john", "doe", "john.doe@telecomnancy.eu", "password1", "0011223344", 10, address);
        PersonController.getInstance().insert(user);

        launch();
    }
}