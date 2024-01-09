package eu.telecomnancy.codinglate;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private SceneManager sceneManager;
    @Override
    public void start(Stage stage) throws IOException {
        Image appIcon = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/logo_hands.png"));
        stage.getIcons().add(appIcon);

        this.sceneManager = new SceneManager(stage);

        Scene scene = sceneManager.createSceneProductCreator();
        sceneManager.switchScene(scene);
    }

    public static void main(String[] args) {


        launch();
    }
}