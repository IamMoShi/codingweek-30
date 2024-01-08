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
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private SceneManager sceneManager;
    @Override
    public void start(Stage stage) throws IOException {
        Image appIcon = new Image("eu/telecomnancy/condinglate/icon/logo_hands.png");
        stage.getIcons().add(appIcon);

        this.sceneManager = new SceneManager(stage);

        Scene scene = sceneManager.createScenePresentation();
        sceneManager.switchScene(scene);
    }

    public static void main(String[] args) {
        DbConnection.connect();
        Address address = new Address();
        User user = new User(-1, "john", "doe", "john.doe@telecomnancy.eu", "password1", "0011223344", 10, address);
        PersonController.getInstance().insert(user);

        launch();
    }
}