package eu.telecomnancy.codinglate;


import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.user.Address;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
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

        PersonController userController = new PersonController();
        // Test s'il y a un utilisateur dans la base de données avec l'email john.doe@example.com
        User user = (User) userController.getPersonByEmail("leo.fornoff@telecomnancy.eu");
        if (user != null) {
            userController.delete(user);
        } else {
            user = new User("Leo", "Fornoff", "leo.fornoff@telecomnancy.eu", "password", new Address("1 rue de la paix"));
        }

        userController.insert(user);

        PersonController.getInstance().setCurrentUser(user);

    }

    public static void main(String[] args) {


        launch();
    }
}