package eu.telecomnancy.condinglate;

import eu.telecomnancy.condinglate.UI.SearchBar;
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
        launch();
    }
}