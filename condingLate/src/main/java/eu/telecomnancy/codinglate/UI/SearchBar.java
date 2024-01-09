package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.SceneManager;
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

        Image logo = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/logo_hands.png")    );
        assert logo != null;
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(40);
        logoView.setFitWidth(40);
        getChildren().add(logoView);


        searchField = new TextField();
        searchField.setPromptText("Entrer votre recherche");
        searchField.setPrefWidth(200);
        searchField.setPrefHeight(30);
        searchField.setMinHeight(30);
        searchField.setMinWidth(200);
        searchField.setMaxHeight(30);
        searchField.setMaxWidth(200);
        searchField.getStyleClass().add("search-field");
        getChildren().add(searchField);

        Image searchIcon = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/search.png")    );
        assert searchIcon != null;
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitHeight(30);
        searchIconView.setFitWidth(30);
        getChildren().add(searchIconView);


        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, javafx.scene.layout.Priority.ALWAYS);
        getChildren().add(spacer2);


        SearchBarButton searchButton = new SearchBarButton("searchButton", "Se connecter");
        searchButton.initializeButton();
        getChildren().add(searchButton);
        searchButton.setOnMouseClicked(e -> {

            SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
            Scene scene = sceneManager.createSceneCompteCreator();
            sceneManager.switchScene(scene);

        });

        IconButton userButton = new IconButton("userButton", "", "/eu/telecomnancy/codinglate/icon/user.png");
        userButton.initializeButton();
        getChildren().add(userButton);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Profil");
        MenuItem item3 = new MenuItem("Messagerie");
        MenuItem item2 = new MenuItem("Déconnexion");

        contextMenu.getItems().addAll(item1, item2);
        userButton.setOnMouseClicked(e -> {
            contextMenu.show(userButton, e.getScreenX() - userButton.getHeight(), e.getScreenY() + userButton.getHeight());
        });


        getStyleClass().add("search-bar");
        setSpacing(10);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);


    }


}
