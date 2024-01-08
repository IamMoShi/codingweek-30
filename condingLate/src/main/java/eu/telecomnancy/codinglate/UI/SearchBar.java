package eu.telecomnancy.codinglate.UI;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SearchBar extends HBox {

    private TextField searchField;

    public SearchBar() {
        Image logo = new Image("eu/telecomnancy/condinglate/icon/logo_hands.png");
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

        Image searchIcon = new Image("eu/telecomnancy/condinglate/icon/search.png");
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

        Image userIcon = new Image("eu/telecomnancy/condinglate/icon/user.png");
        ImageView userIconView = new ImageView(userIcon);
        userIconView.setFitHeight(30);
        userIconView.setFitWidth(30);
        getChildren().add(userIconView);

        getStyleClass().add("search-bar");
        setSpacing(10);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);


    }


}
