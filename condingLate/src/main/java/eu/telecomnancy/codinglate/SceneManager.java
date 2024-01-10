package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.CustomListCell;
import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.SearchBar;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene createScenePresentation() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER_LEFT);
        imageBox.setSpacing(150);
        imageBox.setPadding(new Insets(0, 0, 0, 50));
        Image image = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/picture/sharingeconomy.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(400);
        imageView.minWidth(400);


        Text text = new Text("CodingLate");
        text.setStyle("-fx-font: 24 arial;");
        text.setTranslateY(-10);
        text.setTranslateX(150);

        imageBox.getChildren().addAll(imageView, text);
        root.setCenter(imageBox);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newWidth = newValue.doubleValue();
                imageView.setFitWidth(newWidth * 0.4); // Ajustez le facteur de taille selon vos besoins
            }
        });
        return scene;
    }


    public Scene createSceneDisplayProduct() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        TilePane filtersPane = new TilePane();
        filtersPane.setPadding(new Insets(10, 0, 10, 0));
        filtersPane.setHgap(10);
        filtersPane.setVgap(10);

        // Ajouter ComboBox pour la sélection du produit
        ComboBox<String> productComboBox = new ComboBox<>();
        productComboBox.getItems().addAll("Produit", "Service");



        // Initialiser les ComboBox spécifiques au produit
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Auto","Jardin","Maison","Multimedia","Sport","Autre");
        CustomTextField brandComboBox = new CustomTextField("Marque");
        CustomTextField modelComboBox = new CustomTextField("Modèle");
        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("Neuf", "Bon","Reconditionné","Utilisé");
        CustomTextField yearComboBox = new CustomTextField("Année");



        // Ajouter des labels pour chaque ComboBox
        filtersPane.getChildren().addAll(
                new Label("Type de produit:"), productComboBox,
                new Label("Catégorie:"), categoryComboBox,
                brandComboBox, modelComboBox,
                new Label("Condition:"), conditionComboBox,
                 yearComboBox
        );

        layout.getChildren().add(filtersPane);

        productComboBox.setOnAction(event -> {
            handleProductSelection(productComboBox.getValue(),categoryComboBox,brandComboBox,modelComboBox,conditionComboBox,yearComboBox);
        });
        root.setTop(layout);

        TilePane tilePane = new TilePane();
        tilePane.setFocusTraversable(true);
        tilePane.setPadding(new Insets(10));
        tilePane.setHgap(10);
        tilePane.setVgap(10);



        // Créer un ScrollPane et y ajouter la TilePane
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
        // Ajoutez vos stylesheets ici
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());


        // Retourner les ComboBox dans un objet Filtres
        return scene;
    }

    private ImageView createArticleTile() {

        Image image = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/picture/sharingeconomy.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(200);
        imageView.minWidth(200);
        return imageView;
    }

    private void handleProductSelection(String selectedProduct, ComboBox<String> categoryComboBox, CustomTextField brandComboBox,
                                        CustomTextField modelComboBox, ComboBox<String> conditionComboBox, CustomTextField yearComboBox) {
        // Logique pour gérer la sélection du type de produit (produit ou service)
        if ("Produit".equals(selectedProduct)) {
            // Afficher les ComboBox et labels spécifiques au produit
            categoryComboBox.setVisible(true);
            brandComboBox.setVisible(true);
            modelComboBox.setVisible(true);
            conditionComboBox.setVisible(true);
            yearComboBox.setVisible(true);
        } else {
            // Cacher les ComboBox et labels spécifiques au produit
            categoryComboBox.setVisible(false);
            brandComboBox.setVisible(false);
            modelComboBox.setVisible(false);
            conditionComboBox.setVisible(false);
            yearComboBox.setVisible(false);
        }
        // Vous devrez ajouter une logique similaire pour gérer la sélection d'autres ComboBox
    }




    public Scene createSceneProfil() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Person person = PersonController.getInstance().getCurrentUser();
        if (person != null) {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10, 40, 10, 40));
            gridPane.setStyle("-fx-font-size: 20px;");
            gridPane.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(new Text("Nom : " + person.getLastname()), 0, 0);
            gridPane.add(new Text("Prénom : " + person.getFirstname()), 0, 1);
            gridPane.add(new Text("Email : " + person.getEmail()), 0, 2);
            gridPane.add(new Text("Téléphone : " + person.getPhone()), 0, 3);
            //gridPane.add(new Text("Adresse : " + person.getAddress().getAddress() ),  0, 4);
            gridPane.add(new Text("Solde : " + person.getBalance()), 0, 5);

            root.setCenter(gridPane);
        }

        Image image = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/user.png"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(200);
        imageView.minWidth(200);


        root.setRight(imageView);


        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }


    public Scene createMessageScene() {

        //POur avoir un exemple de listView
        ListView<Message> listView = new ListView<>();

        Message message1 = new Message(0, null, null, "Bonjour", null);
        Message message2 = new Message(0, null, null, "Bonjourno", null);

        listView.getItems().add(message1);
        listView.getItems().add(message2);
        listView.setCellFactory(param -> new CustomListCell());

        //POur avoir un exemple de listMessage
        List<Message> MessageUserWithSelectedUserFromUserList = new ArrayList<>();
        Message message3 = new Message(0, null, null, "Bonjour", LocalDate.of(2021, 1, 1));
        Message message4 = new Message(0, null, null, "Bonjourno", LocalDate.of(2021, 1, 2));

        MessageUserWithSelectedUserFromUserList.add(message3);
        MessageUserWithSelectedUserFromUserList.add(message4);

        SearchBar searchBar = new SearchBar();


        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        MessagingList messagingClass = new MessagingList(listView, MessageUserWithSelectedUserFromUserList);
        BorderPane borderPane = messagingClass.getBorderPane();

        root.setCenter(borderPane);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }

    public Scene createSceneConnexion() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        Stage stage = (Stage) this.primaryStage;
        System.out.println(stage);

        // Créez une nouvelle instance de LoginCreator
        LoginCreator loginCreator = new LoginCreator(stage);
        VBox gridPane = loginCreator.getVbox();

        // Ajoutez le formulaire à la scène
        root.setCenter(gridPane);


        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;
    }

    public Scene createSceneCompteCreator() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);


        // Créez une nouvelle instance de CompteCreator
        CompteCreator compteCreator = new CompteCreator();
        VBox gridPane = compteCreator.getVbox();

        // Ajoutez le formulaire à la scène

        root.setCenter(gridPane);
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());

        return scene;
    }


    public Scene createSceneProductCreator() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        ProductCreator compteCreator = new ProductCreator();
        VBox gridPane = compteCreator.getVbox();

        // Ajoutez le formulaire à la scène

        root.setCenter(gridPane);
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }

    public Scene createSceneServiceCreator() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        ServiceCreator serviceCreator = new ServiceCreator();
        VBox gridPane = serviceCreator.getVbox();

        // Ajoutez le formulaire à la scène

        root.setCenter(gridPane);
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }
}


