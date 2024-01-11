package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SearchBar extends HBox {

    private TextField searchField;

    public SearchBar() {


        IconButton logoButton = new IconButton("logoButton", "" ,"/eu/telecomnancy/codinglate/icon/logo_hands.png");
        logoButton.initializeButton();
        getChildren().add(logoButton);
        logoButton.setOnMouseClicked(e -> {
            SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            Scene scene = sceneManager.createSceneDisplayProduct();
            sceneManager.switchScene(scene);
        });


        searchField = new TextField();

        searchField.setPromptText("Entrer votre recherche");
        searchField.setPrefWidth(200);
        searchField.setPrefHeight(40);
        searchField.setMinHeight(40);
        searchField.setMinWidth(200);
        searchField.setMaxHeight(30);
        searchField.setMaxWidth(200);
        searchField.getStyleClass().add("search-field");

        searchField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20;  -fx-font-size: 16px;");

        getChildren().add(searchField);
        searchField.setOnMouseClicked(e -> {
            SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            Scene scene = sceneManager.createSceneSearch();
            sceneManager.switchScene(scene);
            return ;
        });



        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, javafx.scene.layout.Priority.ALWAYS);
        getChildren().add(spacer2);

        if (PersonController.getInstance().getCurrentUser() == null) {
            SearchBarButton searchButton = new SearchBarButton("searchButton", "Se connecter");
            searchButton.initializeButton();
            getChildren().add(searchButton);
            searchButton.setOnMouseClicked(e -> {

                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createSceneConnexion();
                sceneManager.switchScene(scene);

            });
        }




        if (PersonController.getInstance().getCurrentUser() != null) {
            SearchBarButton searchButton = new SearchBarButton("searchButton", "Ajouter une annonce");
            searchButton.initializeButton();
            getChildren().add(searchButton);
            ContextMenu contextMenuAnnouncement = new ContextMenu();
            MenuItem produit = new MenuItem("Produit");
            MenuItem service = new MenuItem("Service");
            produit.setOnAction(e -> {
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createSceneProductCreator();
                sceneManager.switchScene(scene);
            });

            service.setOnAction(e -> {
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createSceneServiceCreator();
                sceneManager.switchScene(scene);
            });


            contextMenuAnnouncement.getItems().addAll(produit, service);

            searchButton.setOnMouseClicked(e -> {
                contextMenuAnnouncement.show(searchButton, e.getScreenX() - searchButton.getHeight(), e.getScreenY() + searchButton.getHeight());
            });

            IconButton userButton = new IconButton("userButton", "", "/eu/telecomnancy/codinglate/icon/user.png");
            userButton.initializeButton();
            getChildren().add(userButton);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem profil = new MenuItem("Profil");
            profil.setOnAction(e -> {
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createSceneProfil();
                sceneManager.switchScene(scene);
            });

            MenuItem booking = new MenuItem("Mes réservations");
            booking.setOnAction(e -> {
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createSceneMyBookings();
                sceneManager.switchScene(scene);
            });

            MenuItem message = new MenuItem("Messagerie");
            message.setOnAction(e -> {
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createMessageScene();
                sceneManager.switchScene(scene);
            });

            MenuItem deconnexion = new MenuItem("Déconnexion");
            deconnexion.setOnAction(e -> {
                PersonController.getInstance().setCurrentUser(null);
                SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                Scene scene = sceneManager.createScenePresentation();
                sceneManager.switchScene(scene);
            });

            contextMenu.getItems().addAll(profil, booking,message, deconnexion);

            userButton.setOnMouseClicked(e -> {
                contextMenu.show(userButton, e.getScreenX() - userButton.getHeight(), e.getScreenY() + userButton.getHeight());
            });
        }


        getStyleClass().add("search-bar");
        setSpacing(10);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);


    }


}
