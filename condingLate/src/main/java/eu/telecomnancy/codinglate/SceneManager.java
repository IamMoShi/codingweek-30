package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
import eu.telecomnancy.codinglate.calendar.ReservationCalendarView;
import eu.telecomnancy.codinglate.database.dataController.MessageController;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.ImageOfferDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.message.Message;
import eu.telecomnancy.codinglate.database.dataObject.offer.Booking;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.UI.SearchContent;
import eu.telecomnancy.codinglate.geolocation.Coordinates;
import eu.telecomnancy.codinglate.geolocation.Geolocation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneManager {
    private Stage primaryStage;
    private Scene previousScene;
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(Scene scene) {
        int minWidth = 1200;
        int minHeight = 800;
        double width = getCurrentSceneWidth();
        double height = getCurrentSceneHeight();
        primaryStage.setScene(scene);
        primaryStage.show();
        previousScene = scene;
    }

    private int getCurrentSceneHeight() {
        if (primaryStage.getScene() != null) {
            return (int) primaryStage.getScene().getHeight();
        } else {
            return 800;
        }
    }

    private int getCurrentSceneWidth() {
        if (primaryStage.getScene() != null) {
            return (int) primaryStage.getScene().getWidth();
        } else {
            return 1300;
        }
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

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
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


    public Scene createSceneResearchBar(ArrayList<Offer> listOffres) {


        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 0, 10, 0));
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        //Séléction des articles
        handleProductSearch(tilePane, listOffres);

        root.setCenter(tilePane);


        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        // Ajoutez vos stylesheets ici
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;


    }

    public void handleProductSearch(TilePane tilePane, ArrayList<Offer> listOffres) {
        OfferController offerController = new OfferController();
        updateTilePane(tilePane, listOffres);
        // Ajouter ComboBox pour la sélection du produit

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


        //Séléction des articles
        // Ajouter ComboBox pour la sélection du produit
        CustomComboBox productComboBox = new CustomComboBox(FXCollections.observableArrayList("Produit", "Service"));
        productComboBox.getItems().add("");
        productComboBox.setValue("Type de produit");


        // Initialiser les ComboBox spécifiques au produit
        CustomComboBox categoryComboBox = new CustomComboBox(FXCollections.observableArrayList("Auto", "Jardin", "Maison", "Multimedia", "Sport", "Autre"));
        categoryComboBox.getItems().add("");
        categoryComboBox.setValue("Catégorie");
        CustomTextField brandComboBox = new CustomTextField("Marque");
        CustomTextField modelComboBox = new CustomTextField("Modèle");

        CustomComboBox conditionComboBox = new CustomComboBox(FXCollections.observableArrayList("Neuf", "Bon", "Reconditionné", "Utilisé"));
        conditionComboBox.getItems().add("");
        conditionComboBox.setValue("Condition");

        CustomTextField yearComboBox = new CustomTextField("Année");


        // Ajouter des labels pour chaque ComboBox
        if (productComboBox.getValue().equals("Service")) {
            filtersPane.getChildren().addAll(new Label("Type de produit:"), productComboBox);
        }

        filtersPane.getChildren().addAll(
                productComboBox,
                categoryComboBox,
                brandComboBox, modelComboBox,
                conditionComboBox,
                yearComboBox
        );

        filtersPane.setPadding(new Insets(10, 20, 10, 20));


        categoryComboBox.setVisible(false);
        brandComboBox.setVisible(false);
        modelComboBox.setVisible(false);
        conditionComboBox.setVisible(false);
        yearComboBox.setVisible(false);

        layout.getChildren().add(filtersPane);

        TilePane tilePane = new TilePane();
        tilePane.setFocusTraversable(true);
        tilePane.setPadding(new Insets(10));
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);


        productComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        categoryComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        conditionComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        brandComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        modelComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        yearComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, (String) productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);

        });

        root.setTop(layout);

        // Créer un ScrollPane et y ajouter la TilePane
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        // Ajoutez vos stylesheets ici
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());


        // Retourner les ComboBox dans un objet Filtres
        return scene;
    }

    private VBox createOfferTile(Offer offer, TilePane tilePane) {
        VBox tile = new VBox(1);
        tile.setMaxWidth(250);
        tile.setStyle("-fx-border-color: #171616; -fx-border-radius: 5; -fx-border-width: 1; -fx-padding: 5;");

        ImageOfferDAO imageOfferDAO = new ImageOfferDAO();
        ArrayList<String> images = imageOfferDAO.getImages(offer);

        if (!images.isEmpty()) {
            String imageurl = images.get(0);

            Image image = new Image(getClass().getResourceAsStream("/" + imageurl));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(240); // Largeur maximale de la VBox
            imageView.setFitHeight(220); // Hauteur de la bande pour les informations
            imageView.setPreserveRatio(true);
            tile.getChildren().add(imageView);
        }

        Label titleLabel = new Label(offer.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-padding: 5 10 0 10; -fx-text-alignment: center; -fx-text-fill: #171616; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-weight: 400; -fx-line-spacing: 1.5; -fx-letter-spacing: 0.5;");

        Label descriptionLabel = new Label(offer.getDescription());
        HBox priceBox = new HBox(0);
        priceBox.setPadding(new Insets(3, 0, 0, 10));
        Image image = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/money.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        Double price = offer.getPrice();
        Integer priceInt = price.intValue();

        Label priceLabel = new Label(priceInt + " " + offer.getPriceType());
        priceLabel.setPadding(new Insets(3, 0, 0, 0));
        priceBox.getChildren().add(imageView);
        priceBox.getChildren().add(priceLabel);





        VBox detailsLayout = new VBox(5);
        detailsLayout.getChildren().addAll(titleLabel, descriptionLabel, priceBox);

        if (offer.getStartingDate() != null && offer.getEndingDate() != null) {

            HBox dateBox = new HBox(3);
            dateBox.setPadding(new Insets(3, 0, 0, 10));
            Image calendarImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/calendar.png"));
            ImageView calendarImageView = new ImageView(calendarImage);
            calendarImageView.setFitWidth(30);
            calendarImageView.setFitHeight(30);
            calendarImageView.setPreserveRatio(true);

            dateBox.getChildren().add(calendarImageView);

            LocalDateTime startingDate = offer.getStartingDate();
            LocalDateTime endingDate = offer.getEndingDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Label dateLabel = new Label(startingDate.format(formatter) + " - " + endingDate.format(formatter));


            dateLabel.setPadding(new Insets(7, 0, 0, 2));



            dateBox.getChildren().add(dateLabel);
            detailsLayout.getChildren().add(dateBox);


        }
        tile.getChildren().add(detailsLayout);

        // Ajouter un gestionnaire d'événements pour le clic sur la tuile
        tile.setOnMouseClicked(event -> handleTileClick(offer, tilePane));

        return tile;
    }




    private void handleTileClick(Offer offer, TilePane tilePane) {
        SceneManager sceneManager = new SceneManager((Stage) tilePane.getScene().getWindow());
        Scene scene = sceneManager.createSceneProduct(offer);
        sceneManager.switchScene(scene);
    }


    private void updateTilePane(TilePane tilePane, ArrayList<Offer> offers) {
        tilePane.getChildren().clear();
        for (Offer offer : offers) {
            tilePane.getChildren().add(createOfferTile(offer, tilePane));
        }
        tilePane.requestLayout();
    }

    private ArrayList<Offer> handleProductSelection(BorderPane root, TilePane tilePane, String selectedProduct, ComboBox<String> categoryComboBox, CustomTextField brandComboBox,
                                                    CustomTextField modelComboBox, ComboBox<String> conditionComboBox, CustomTextField yearComboBox) {
        // Logique pour gérer la sélection du type de produit (produit ou service)
        if ("Produit".equals(selectedProduct)) {
            // Afficher les ComboBox et labels spécifiques au produit
            categoryComboBox.setVisible(true);
            brandComboBox.setVisible(true);
            modelComboBox.setVisible(true);
            conditionComboBox.setVisible(true);
            yearComboBox.setVisible(true);

            String categorieString = categoryComboBox.getValue();
            EnumConverter enumConverter = new EnumConverter();

            ProductCategory categorie = EnumConverter.convertCategoryToInt(categorieString);
            String brand = brandComboBox.getText();
            String model = modelComboBox.getText();
            String conditionString = conditionComboBox.getValue();
            ProductCondition condition = EnumConverter.convertConditionToInt(conditionString);


            String year = yearComboBox.getText();
            if (year == null) {
                year = "0";
            }


            OfferController offerController = new OfferController();
            ArrayList<Offer> listOffres = offerController.getOfferByParameters(true, categorie, brand, model, condition, parseYear(year));
            updateTilePane(tilePane, listOffres);
            return listOffres;


        } else {
            // Cacher les ComboBox et labels spécifiques au produit

            categoryComboBox.setVisible(false);
            brandComboBox.setVisible(false);
            modelComboBox.setVisible(false);
            conditionComboBox.setVisible(false);
            yearComboBox.setVisible(false);

            OfferController offerController = new OfferController();
            ArrayList<Offer> listoffres = offerController.getOfferByParameters(false, null, "", "", null, 0);
            System.out.println(listoffres);
            updateTilePane(tilePane, listoffres);
            return listoffres;
        }
        // Vous devrez ajouter une logique similaire pour gérer la sélection d'autres ComboBox
    }

    private int parseYear(String year) {
        try {
            return year.isEmpty() ? 0 : Integer.parseInt(year);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion de l'année en entier : " + e.getMessage());
            return 0;
        }
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


        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }


    public Scene createMessageScene() {

        SearchBar searchBar = new SearchBar();
        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);


        //récupérer l'utilisateur courant
        Person currentuser = PersonController.getInstance().getCurrentUser();

        System.out.println(currentuser);

        //faire la liste de toutes les conversations de l'utilisateur courant
        MessageController messageController = new MessageController();
        List<Person> UserYouHadAConversationWith = messageController.getConversationList(currentuser);
        ListView<Person> MessageUserWithSelectedUserFromUserList = new ListView<>();
        MessageUserWithSelectedUserFromUserList.setCellFactory(param -> new CustomListCell(root));

        MessageUserWithSelectedUserFromUserList.getItems().addAll(UserYouHadAConversationWith);
        root.setLeft(MessageUserWithSelectedUserFromUserList);

        FormButton newConv = new FormButton("NewConvo", "Nouvelle Conversation");
        newConv.initializeButton();
        root.setRight(newConv);


        newConv.setOnAction(event -> {
                    Stage stage = (Stage) this.primaryStage;
                    MessageCreator messageCreator = new MessageCreator(stage);
                    VBox gridPane = messageCreator.getVbox();
                    root.setCenter(gridPane);
                }
        );

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
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


        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
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
        CompteCreator compteCreator = new CompteCreator((Stage) this.primaryStage);
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

        ProductCreator compteCreator = new ProductCreator(primaryStage);
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

        ServiceCreator serviceCreator = new ServiceCreator(primaryStage);
        VBox gridPane = serviceCreator.getVbox();

        // Ajoutez le formulaire à la scène

        root.setCenter(gridPane);
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }

    public Scene createSceneSearch() {

        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);
        root.setCenter(new SearchContent());
        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }


    // ... (autres méthodes existantes)

    public Scene createSceneProduct(Offer offer) {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        // Créer une boîte horizontale pour afficher l'utilisateur à gauche et les détails du produit au centre
        HBox productBox = new HBox(10);
        productBox.setPadding(new Insets(10));

        // Afficher les informations de l'utilisateur à gauche
        VBox userBox = new VBox(5);
        HBox userPictureBox = new HBox(0);
        Image userPicture = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/user.png"));
        ImageView userView = new ImageView(userPicture);
        userView.setFitWidth(35);
        userView.setFitHeight(35);

        userPictureBox.getChildren().add(userView);


        Label userLabel = new Label( offer.getUser().getFirstname() + " " + offer.getUser().getLastname());
        userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-padding: 5 10 0 10; -fx-text-alignment: center; -fx-text-fill: #171616; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-weight: 400; -fx-line-spacing: 1.5; -fx-letter-spacing: 0.5;");
        userLabel.setPadding(new Insets(0, 0, 0, 10));

        userPictureBox.getChildren().add(userLabel);

        SearchBarButton messageButton = new SearchBarButton("Envoyer un message", "Envoyer un message");
        messageButton.initializeButton();

        messageButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager((Stage) productBox.getScene().getWindow());
            Scene scene = sceneManager.createMessageScene();
            sceneManager.switchScene(scene);
        });


        // Ajouter d'autres détails de l'utilisateur si nécessaire
        userBox.getChildren().addAll(userPictureBox);
        productBox.getChildren().add(userBox);
        userBox.getChildren().add(messageButton);


        // Afficher les détails du produit au centre
        VBox productDetailsBox = new VBox(10);


        if (!offer.getImages().isEmpty()) {
            ImageView imageView = new ImageView(offer.getImages().get(0)); // Utilisez la première image comme exemple
            imageView.setFitHeight(300); // Hauteur de la bande pour les information
            imageView.setPreserveRatio(true);
            productDetailsBox.getChildren().add(imageView);
        }

        Label titleLabel = new Label( offer.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-padding: 5 10 0 10; -fx-text-alignment: center; -fx-text-fill: #171616; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-weight: 400; -fx-line-spacing: 1.5; -fx-letter-spacing: 0.5;");
        productDetailsBox.getChildren().add(titleLabel);
        String description = offer.getDescription();
        if (!Objects.equals(description, "")) {
            Label descriptionLabel = new Label(offer.getDescription());
            productDetailsBox.getChildren().add(descriptionLabel);
        }


        Image moneyImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/money.png"));
        ImageView moneyImageView = new ImageView(moneyImage);
        moneyImageView.setFitWidth(30);
        moneyImageView.setFitHeight(30);
        moneyImageView.setPreserveRatio(true);
        HBox priceBox = new HBox(3);
        priceBox.setPadding(new Insets(3, 0, 0, 10));
        priceBox.getChildren().add(moneyImageView);
        Double price = offer.getPrice();
        Integer priceInt = price.intValue();
        Label priceLabel = new Label(priceInt + " " + offer.getPriceType());
        priceLabel.setPadding(new Insets(6, 0, 0, 0));
        priceBox.getChildren().add(priceLabel);

        productDetailsBox.getChildren().add(priceBox);
        if (offer.getStartingDate() != null && offer.getEndingDate() != null) {
            Image calendarImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/calendar.png"));
            ImageView calendarImageView = new ImageView(calendarImage);
            calendarImageView.setFitWidth(30);
            calendarImageView.setFitHeight(30);
            calendarImageView.setPreserveRatio(true);
            HBox dateBox = new HBox(3);
            dateBox.setPadding(new Insets(3, 0, 0, 10));
            dateBox.getChildren().add(calendarImageView);
            LocalDateTime startingDate = offer.getStartingDate();
            LocalDateTime endingDate = offer.getEndingDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Label dateLabel = new Label(startingDate.format(formatter) + " - " + endingDate.format(formatter));

            dateBox.getChildren().add(dateLabel);
            productDetailsBox.getChildren().add(dateBox);


        }


        if ( offer instanceof Product) {
            Product product = (Product) offer;
            Label brandLabel = new Label("Marque : " + product.getBrand());
            Label modelLabel = new Label("Modèle : " + product.getModel());
            Label conditionLabel = new Label("Condition : " + product.getCondition());
            Label yearLabel = new Label("Année : " + product.getYear());
            productDetailsBox.getChildren().addAll(brandLabel, modelLabel, conditionLabel, yearLabel);
        }

        User user = (User) PersonController.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getAddress() != null) {
                String userAddress = user.getAddress().getAddress();
                Coordinates userCoordinates = Geolocation.getCoordinatesFromAddress(userAddress);
                if (offer.getUser().getAddress() != null) {
                    String offerAddress = offer.getUser().getAddress().getAddress();
                    Coordinates offerCoordinates = Geolocation.getCoordinatesFromAddress(offerAddress);
                    double distance = userCoordinates.distance(offerCoordinates);
                    HBox distanceBox = new HBox(3);
                    distanceBox.setPadding(new Insets(3, 0, 0, 10));
                    Image distanceImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/maps.png"));
                    ImageView distanceImageView = new ImageView(distanceImage);
                    distanceImageView.setFitWidth(30);
                    distanceImageView.setFitHeight(30);
                    distanceImageView.setPreserveRatio(true);
                    distanceBox.getChildren().add(distanceImageView);
                    Label distanceLabel = new Label("Distance : " + distance + " km");
                    distanceBox.getChildren().add(distanceLabel);
                    productDetailsBox.getChildren().add(distanceBox);
                }


            }
        }





        HBox bottomBox = new HBox(10);
        // Vous pouvez ajouter d'autres informations du produit ici
        FormButton submitButton = new FormButton("submit", "Reserver cette offre");
        submitButton.initializeButton();

        bottomBox.getChildren().add(submitButton);


        submitButton.setOnAction(event -> {
            switchScene(createSceneCalendar(offer));
        });

        FormButton returnDisplayButton = new FormButton("returndisplay", "Retour");
        returnDisplayButton.initializeButton();

        bottomBox.getChildren().add(returnDisplayButton);


        returnDisplayButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager(primaryStage);
            Scene scene = sceneManager.createSceneDisplayProduct();
            sceneManager.switchScene(scene);
        });


        // Ajouter une image si disponible


        // Ajouter les labels au conteneur des détails du produit
        productDetailsBox.getChildren().add(bottomBox);
        productBox.getChildren().add(productDetailsBox);

        // Ajouter la boîte du produit à la mise en page principale
        layout.getChildren().add(productBox);


        root.setTop(layout);

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        // Ajouter vos stylesheets ici si nécessaire
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());

        return scene;
    }

    public Scene createSceneMyBookings() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        if (PersonController.getInstance().getCurrentUser() != null) {
            User user = (User) PersonController.getInstance().getCurrentUser();
            int userId = user.getId();
            BookingDAO bookingDAO = new BookingDAO();
            ArrayList<Booking> bookingsByUser = bookingDAO.getBookingsByUser(userId);
            ArrayList<Booking> offersByUser = getBookingsForCurrentUser();


            // Ajouter un label pour indiquer que c'est la section des réservations de l'utilisateur
            Label titleLabel = new Label("Mes Réservations");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            VBox.setMargin(titleLabel, new Insets(20, 0, 0, 30));
            layout.getChildren().add(titleLabel);

            // Créer une liste de réservations
            ListView<String> bookingsListView = new ListView<>();
            bookingsListView.setPrefHeight(200);

            // Ajouter les réservations à la liste
            for (Booking booking : bookingsByUser) {
                String bookingInfo = "Offer: " + booking.getOffer().getTitle() +
                        ", Starting Date: " + booking.getStartingDate() +
                        ", Ending Date: " + booking.getEndingDate() +
                        ", Status: " + booking.getStatus();
                bookingsListView.getItems().add(bookingInfo);
            }

            VBox.setMargin(bookingsListView, new Insets(10, 10, 0, 10));
            layout.getChildren().add(bookingsListView);

            Label titleMyreservation = new Label("Réservations sur mes offres");
            titleMyreservation.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            VBox.setMargin(titleMyreservation, new Insets(20, 0, 0, 30));
            layout.getChildren().add(titleMyreservation);


            // Créer une liste de réservations
            ListView<String> bookingsOnmyOfferListView2 = new ListView<>();
            bookingsOnmyOfferListView2.setPrefHeight(200);


            // Ajouter les réservations à la liste
            for (Booking booking : offersByUser) {
                String bookingInfo = "Offer: " + booking.getOffer().getTitle() +
                        ", Starting Date: " + booking.getStartingDate() +
                        ", Ending Date: " + booking.getEndingDate() +
                        ", Status: " + booking.getStatus();
                bookingsOnmyOfferListView2.getItems().add(bookingInfo);
            }
            VBox.setMargin(bookingsOnmyOfferListView2, new Insets(10, 10, 0, 10));
            layout.getChildren().add(bookingsOnmyOfferListView2);
            // Ajouter la liste de réservations à la mise en page

        }

        root.setTop(layout);
        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;
    }

    public ArrayList<Booking> getBookingsForCurrentUser() {
        BookingDAO bookingDAO = new BookingDAO();
        ArrayList<Booking> allBookings = bookingDAO.getAllBooking(); // Supposons que vous ayez une méthode getBookings qui renvoie toutes les réservations
        ArrayList<Booking> userBookings = new ArrayList<>();

        if (PersonController.getInstance().getCurrentUser() != null) {
            User currentUser = (User) PersonController.getInstance().getCurrentUser();
            int currentUserId = currentUser.getId();

            for (Booking booking : allBookings) {
                // Vérifier si l'offre associée à la réservation a un utilisateur d'ID correspondant à l'utilisateur connecté
                if (booking.getOffer().getUser().getId() == currentUserId) {
                    userBookings.add(booking);
                }
            }
        }

        return userBookings;

    }


    public Scene createSceneCalendar(Offer offer) {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        ReservationCalendarView calendar = new ReservationCalendarView(offer);

        root.setTop(layout);
        root.setCenter(calendar);
        root.setBottom(new BookButton(previousScene, calendar));

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }


    public Scene createSceneEvaluations(Offer offer) {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        RatingForm ratingForm = new RatingForm((Stage) this.primaryStage, offer);
        VBox gridPane = ratingForm.getVbox();

        root.setCenter(gridPane);

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;
    }

    public Scene createSceneMyOffers() {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);


        root.setTop(layout);

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;


    }

}



