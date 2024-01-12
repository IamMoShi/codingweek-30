package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
import eu.telecomnancy.codinglate.calendar.ReservationCalendarView;
import eu.telecomnancy.codinglate.database.dataController.MessageDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.ImageOfferDAO;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
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
        if (scene == null) {
            scene = createScenePresentation();
        }
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
            try {
                Image image = new Image(getClass().getResourceAsStream("/" + imageurl));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(240); // Largeur maximale de la VBox
                imageView.setFitHeight(220); // Hauteur de la bande pour les informations
                imageView.setPreserveRatio(true);
                tile.getChildren().add(imageView);
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération de l'image : " + e.getMessage());
            }

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


        User user = (User) PersonController.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getAddress() != null) {
                String userAddress = user.getAddress().getAddress();
                System.out.println(userAddress);
                Coordinates userCoordinates = Geolocation.getCoordinatesFromAddress(userAddress);
                System.out.println(offer.getUser().getAddress());
                if (offer.getUser().getAddress().getAddress() != null) {
                    System.out.println(offer.getUser().getAddress());
                    String offerAddress = offer.getUser().getAddress().getAddress();
                    Coordinates offerCoordinates = Geolocation.getCoordinatesFromAddress(offerAddress);
                    double distance = 0;
                    try {
                        distance = userCoordinates.distance(offerCoordinates);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du calcul de la distance : " + e.getMessage());
                    }

                    System.out.println(distance);

                    HBox distanceBox = new HBox(3);
                    distanceBox.setPadding(new Insets(3, 0, 0, 10));
                    Image distanceImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/maps.png"));
                    ImageView distanceImageView = new ImageView(distanceImage);
                    distanceImageView.setFitWidth(30);
                    distanceImageView.setFitHeight(30);
                    distanceImageView.setPreserveRatio(true);
                    distanceBox.getChildren().add(distanceImageView);

                    Label distanceLabel = new Label("Distance : " + Math.round(distance) + " km");
                    distanceBox.getChildren().add(distanceLabel);
                    detailsLayout.getChildren().add(distanceBox);
                }


            }
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

        HBox contentBox = new HBox(20);
        contentBox.setAlignment(Pos.CENTER);

        Person person = PersonDAO.getInstance().getCurrentUser();
        if (person != null) {
            VBox profileInfo = new VBox(10);
            profileInfo.setAlignment(Pos.CENTER_LEFT);
            profileInfo.setStyle("-fx-font-size: 20px;");

            profileInfo.getChildren().addAll(
                    new Text("Nom : " + person.getLastname()),
                    new Text("Prénom : " + person.getFirstname()),
                    new Text("Email : " + person.getEmail()),
                    new Text("Téléphone : " + person.getPhone()),
                    // new Text("Adresse : " + person.getAddress().getAddress()), // Ajoutez cette ligne si besoin
                    new Text("Solde : " + person.getBalance())
            );

            contentBox.getChildren().addAll(profileInfo);

            // Ajout de l'image du profil à droite des informations
            Image image = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/user.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(200);

            contentBox.getChildren().add(imageView);
        }

        root.setCenter(contentBox);

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
        Person currentuser = PersonDAO.getInstance().getCurrentUser();


        //faire la liste de toutes les conversations de l'utilisateur courant
        MessageDAO messageController = new MessageDAO();
        List<Person> UserYouHadAConversationWith = messageController.getConversationList(currentuser);
        ListView<Person> MessageUserWithSelectedUserFromUserList = new ListView<>();
        MessageUserWithSelectedUserFromUserList.setCellFactory(param -> new CustomListCell(root));

        MessageUserWithSelectedUserFromUserList.getItems().addAll(UserYouHadAConversationWith);
        root.setLeft(MessageUserWithSelectedUserFromUserList);


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 0, 10, 0));


        FormButton newConv = new FormButton("NewConvo", "Nouvelle Conversation");
        newConv.initializeButton();
        hBox.getChildren().add(newConv);
        root.setCenter(hBox);


        newConv.setOnAction(event -> {
                    Stage stage = (Stage) this.primaryStage;
                    Scene scene = createSceneNewMessage(stage, "");
                    SceneManager sceneManager = new SceneManager(stage);
                    sceneManager.switchScene(scene);
                }
        );

        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;
    }

    public Scene createSceneNewMessage(Stage stage, String email) {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);


        // Créez une nouvelle instance de CompteCreator
        MessageCreator messageCreator = new MessageCreator(stage, email);
        VBox gridPane = messageCreator.getVbox();

        FormButton returnButton = new FormButton("return", "Retour");
        returnButton.initializeButton();
        returnButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager((Stage) gridPane.getScene().getWindow());
            Scene scene = sceneManager.createMessageScene();
            sceneManager.switchScene(scene);
        });
        gridPane.getChildren().add(returnButton);


        // Ajoutez le formulaire à la scène

        root.setCenter(gridPane);
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
        LoginCreator loginCreator = new LoginCreator(stage, previousScene);
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
        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
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
        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneWidth());
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
        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
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


        Label userLabel = new Label(offer.getUser().getFirstname() + " " + offer.getUser().getLastname());
        userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-padding: 5 10 0 10; -fx-text-alignment: center; -fx-text-fill: #171616; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-weight: 400; -fx-line-spacing: 1.5; -fx-letter-spacing: 0.5;");
        userLabel.setPadding(new Insets(0, 0, 0, 10));

        userPictureBox.getChildren().add(userLabel);

        SearchBarButton messageButton = new SearchBarButton("Envoyer un message", "Envoyer un message");
        messageButton.initializeButton();

        messageButton.setOnAction(event -> {
            boolean founded = false;
            SceneManager sceneManager = new SceneManager((Stage) productBox.getScene().getWindow());
            Person currentuser = PersonController.getInstance().getCurrentUser();
            MessageController messageController = new MessageController();
            List<Person> UserYouHadAConversationWith = messageController.getConversationList(currentuser);
            for (Person person : UserYouHadAConversationWith) {
                if (person.getId() == offer.getUser().getId()) {
                    Scene scene = sceneManager.createMessageScene();
                    sceneManager.switchScene(scene);
                    founded = true;
                    return;
                }

            }
            if (!founded) {
                Scene scene = sceneManager.createSceneNewMessage((Stage) productBox.getScene().getWindow(), offer.getUser().getEmail());
                sceneManager.switchScene(scene);
            }

        });


        // Ajouter d'autres détails de l'utilisateur si nécessaire
        userBox.getChildren().addAll(userPictureBox);
        productBox.getChildren().add(userBox);
        userBox.getChildren().add(messageButton);


        // Afficher les détails du produit au centre
        VBox productDetailsBox = new VBox(10);


        if (!offer.getImages().isEmpty()) {
            try {
                ImageView imageView = new ImageView(offer.getImages().get(0)); // Utilisez la première image comme exemple
                imageView.setFitHeight(300); // Hauteur de la bande pour les information
                imageView.setPreserveRatio(true);
                productDetailsBox.getChildren().add(imageView);
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération de l'image : " + e.getMessage());
            }

        }

        Label titleLabel = new Label(offer.getTitle());
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


        if (offer instanceof Product) {
            Product product = (Product) offer;
            if (product.getBrand() != null) {
                Label brandLabel = new Label("Marque : " + product.getBrand());
                productDetailsBox.getChildren().add(brandLabel);
            }
            if (product.getModel() != null) {
                Label modelLabel = new Label("Modèle : " + product.getModel());
                productDetailsBox.getChildren().add(modelLabel);
            }
            if (product.getCondition() != null) {
                Label conditionLabel = new Label("Condition : " + product.getCondition());
                productDetailsBox.getChildren().add(conditionLabel);
            }
            if (product.getYear() != 0){
                Label yearLabel = new Label("Année : " + product.getYear());
                productDetailsBox.getChildren().add( yearLabel);
            }
        }

        User user = (User) PersonDAO.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getAddress() != null) {
                String userAddress = user.getAddress().getAddress();
                System.out.println(userAddress);
                Coordinates userCoordinates = Geolocation.getCoordinatesFromAddress(userAddress);
                System.out.println(offer.getUser().getAddress());
                if (offer.getUser().getAddress().getAddress() != null) {
                    System.out.println(offer.getUser().getAddress());
                    String offerAddress = offer.getUser().getAddress().getAddress();
                    Coordinates offerCoordinates = Geolocation.getCoordinatesFromAddress(offerAddress);
                    double distance = 0;
                    try {
                        distance = userCoordinates.distance(offerCoordinates);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du calcul de la distance : " + e.getMessage());
                    }

                    System.out.println(distance);

                    HBox distanceBox = new HBox(3);
                    distanceBox.setPadding(new Insets(3, 0, 0, 10));
                    Image distanceImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/codinglate/icon/maps.png"));
                    ImageView distanceImageView = new ImageView(distanceImage);
                    distanceImageView.setFitWidth(30);
                    distanceImageView.setFitHeight(30);
                    distanceImageView.setPreserveRatio(true);
                    distanceBox.getChildren().add(distanceImageView);

                    Label distanceLabel = new Label("Distance : " + Math.round(distance) + " km");
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

        if (PersonDAO.getInstance().getCurrentUser() != null) {
            User user = (User) PersonDAO.getInstance().getCurrentUser();
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
            ListView<Booking> bookingsListView = new ListView<>();


            bookingsListView.setPrefHeight(200);

            // Ajouter les réservations à la liste
            for (Booking booking : bookingsByUser) {
                bookingsListView.setCellFactory(param -> new CustomCellViewBooking(booking));
                bookingsListView.getItems().add(booking);
            }


            VBox.setMargin(bookingsListView, new Insets(10, 10, 0, 10));
            layout.getChildren().add(bookingsListView);

            Label titleMyreservation = new Label("Réservations sur mes offres");
            titleMyreservation.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            VBox.setMargin(titleMyreservation, new Insets(20, 0, 0, 30));
            layout.getChildren().add(titleMyreservation);


            // Créer une liste de réservations
            ListView<Booking> bookingsOnmyOfferListView2 = new ListView<>();


            bookingsOnmyOfferListView2.setPrefHeight(200);


            // Ajouter les réservations à la liste
            for (Booking booking : offersByUser) {
                bookingsOnmyOfferListView2.setCellFactory(param -> new CustomCellViewOurBooking());
                bookingsOnmyOfferListView2.getItems().add(booking);
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

        if (PersonDAO.getInstance().getCurrentUser() != null) {
            User currentUser = (User) PersonDAO.getInstance().getCurrentUser();
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


    public Scene createSceneRateOffer(Offer offer) {
        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);

        RatingForm ratingForm = new RatingForm((Stage) this.primaryStage, offer);
        VBox gridPane = ratingForm.getVbox();

        FormButton returnDisplayButton = new FormButton("returndisplay", "Retour");
        returnDisplayButton.initializeButton();

        returnDisplayButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager(primaryStage);
            Scene scene = sceneManager.createSceneMyBookings();
            sceneManager.switchScene(scene);
        });

        gridPane.getChildren().add(returnDisplayButton);


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

        OfferController offerController = new OfferController();
        TilePane tilePane = new TilePane();

        ArrayList<Offer> offersByUser = offerController.getOffersByUser(PersonController.getInstance().getCurrentUser());

        updateTilePane(tilePane, offersByUser);

        root.setCenter(tilePane);


        Scene scene = new Scene(root, getCurrentSceneWidth(), getCurrentSceneHeight());
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;


    }


}



