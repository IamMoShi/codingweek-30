package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
import eu.telecomnancy.codinglate.database.dataController.MessageController;
import eu.telecomnancy.codinglate.database.dataController.offer.BookingDAO;
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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(Scene scene) {
        int minWidth = 1200;
        int minHeight = 800;
        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();
        primaryStage.setScene(scene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);
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
        productComboBox.getItems().add("");
        productComboBox.setValue("Type de produit");


        // Initialiser les ComboBox spécifiques au produit
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Auto", "Jardin", "Maison", "Multimedia", "Sport", "Autre");
        CustomTextField brandComboBox = new CustomTextField("Marque");
        CustomTextField modelComboBox = new CustomTextField("Modèle");
        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("Neuf", "Bon", "Reconditionné", "Utilisé");
        CustomTextField yearComboBox = new CustomTextField("Année");


        // Ajouter des labels pour chaque ComboBox
        filtersPane.getChildren().addAll(
                new Label("Type de produit:"), productComboBox,
                new Label("Catégorie:"), categoryComboBox,
                brandComboBox, modelComboBox,
                new Label("Condition:"), conditionComboBox,
                yearComboBox
        );


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

        productComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });

        categoryComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });

        conditionComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });

        brandComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });

        modelComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });

        yearComboBox.setOnAction(event -> {
            handleProductSelection(root, tilePane, productComboBox.getValue(), categoryComboBox, brandComboBox, modelComboBox, conditionComboBox, yearComboBox);
        });


        root.setTop(layout);


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

    private VBox createOfferTile(Offer offer, TilePane tilePane) {
        Label titleLabel = new Label(offer.getTitle());
        Label descriptionLabel = new Label(offer.getDescription());
        Label priceLabel = new Label("Prix : " + offer.getPrice() + " " + offer.getPriceType());
        Label dateLabel = new Label("Date de début : " + offer.getStartingDate() + " / Date de fin : " + offer.getEndingDate());

        VBox tileLayout = new VBox(5);
        tileLayout.getChildren().addAll(titleLabel, descriptionLabel, priceLabel, dateLabel);

        VBox tile = new VBox(5);
        tile.getChildren().add(tileLayout);

        // Ajouter un gestionnaire d'événements pour le clic sur la tuile
        tile.setOnMouseClicked(event -> handleTileClick(offer, tilePane));

        return tile;
    }

    private void handleTileClick(Offer offer, TilePane tilePane) {
        // Action à effectuer lorsqu'une tuile est cliquée
        System.out.println("Tuile cliquée : " + offer.getTitle());
        SceneManager sceneManager = new SceneManager((Stage) tilePane.getScene().getWindow());
        Scene scene = sceneManager.createSceneProduct(offer);
        sceneManager.switchScene(scene);
    }


    private void updateTilePane(TilePane tilePane, ArrayList<Offer> offers) {
        tilePane.getChildren().clear();
        for (Offer offer : offers) {
            tilePane.getChildren().add(createOfferTile(offer, tilePane));
        }
    }

    private void handleProductSelection(BorderPane root, TilePane tilePane, String selectedProduct, ComboBox<String> categoryComboBox, CustomTextField brandComboBox,
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


        } else {
            // Cacher les ComboBox et labels spécifiques au produit
            categoryComboBox.setVisible(false);
            brandComboBox.setVisible(false);
            modelComboBox.setVisible(false);
            conditionComboBox.setVisible(false);
            yearComboBox.setVisible(false);

            OfferController offerController = new OfferController();
            ArrayList<Offer> listoffres = offerController.getOfferByParameters(false, null, "", "", null, 0);
            updateTilePane(tilePane, listoffres);
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


        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/eu/telecomnancy/codinglate/css/ui/searchBar.css").toString());
        return scene;

    }


    public Scene createMessageScene() {

        //récupérer l'utilisateur courant
        Person currentuser = PersonController.getInstance().getCurrentUser();

        //faire la liste de toutes les conversations de l'utilisateur courant
        MessageController messageController = new MessageController();
        List<List<Message>> MessageUserWithSelectedUserFromUserList = new ArrayList<>();

        ListView<String> UserYouHadAConversationWith = new ListView<>();
        UserYouHadAConversationWith = messageController.getListofFriends();
        UserYouHadAConversationWith.setCellFactory(param -> new CustomListCell());

        //récupérer toutes les personnes ayant une conversation avec l'utilisateur courant
        List<Person> persons = new ArrayList<>();
        persons = messageController.getFriends();


        for(Person person : persons){

            List<Message> conversation = new ArrayList<>();
            conversation = messageController.getConversation(currentuser.getEmail(),person.getEmail());

            MessageUserWithSelectedUserFromUserList.add(conversation);

        }



        SearchBar searchBar = new SearchBar();


        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);



        FormButton newConv = new FormButton("NewConvo","Nouvelle Conversation");
        newConv.initializeButton();
        root.setRight(newConv);

        newConv.setOnAction(event -> {
                    Stage stage = (Stage) this.primaryStage;
                    MessageCreator messageCreator= new MessageCreator(stage);
                    VBox gridPane = messageCreator.getVbox();
                    root.setCenter(gridPane);
                }
        );



        MessagingList messagingClass = new MessagingList(MessageUserWithSelectedUserFromUserList, UserYouHadAConversationWith);
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

    public Scene createSceneSearch() {

        SearchBar searchBar = new SearchBar();

        BorderPane root = new BorderPane();
        // Mise en page de la scène
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0));
        layout.getChildren().add(searchBar);

        root.setTop(layout);
        root.setCenter(new SearchContent());
        Scene scene = new Scene(root, 1000, 600);
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
        Label userLabel = new Label("Utilisateur : " + offer.getUser().getFirstname() + " " + offer.getUser().getLastname());
        SearchBarButton messageButton = new SearchBarButton("Envoyer un message", "Envoyer un message");
        messageButton.initializeButton();

        messageButton.setOnAction(event -> {
            SceneManager sceneManager = new SceneManager((Stage) productBox.getScene().getWindow());
            Scene scene = sceneManager.createMessageScene();
            sceneManager.switchScene(scene);
        });


        // Ajouter d'autres détails de l'utilisateur si nécessaire
        userBox.getChildren().addAll(userLabel);
        productBox.getChildren().add(userBox);
        userBox.getChildren().add(messageButton);

        // Afficher les détails du produit au centre
        VBox productDetailsBox = new VBox(10);
        Label titleLabel = new Label("Titre : " + offer.getTitle());
        Label descriptionLabel = new Label("Description : " + offer.getDescription());
        Label priceLabel = new Label("Prix : " + offer.getPrice() + " " + offer.getPriceType());
        if (offer.getStartingDate() != null && offer.getEndingDate() != null) {
            priceLabel = new Label("Prix : " + offer.getPrice() + " " + offer.getPriceType() + " / Date de début : " + offer.getStartingDate() + " / Date de fin : " + offer.getEndingDate());
            productDetailsBox.getChildren().add(priceLabel);
        }

        // Vous pouvez ajouter d'autres informations du produit ici
        FormButton submitButton = new FormButton("submit","Reserver cette offre");
        submitButton.initializeButton();


        submitButton.setOnAction(event -> {
            Booking booking = new Booking(offer, (User)PersonController.getInstance().getCurrentUser(), LocalDateTime.now(), LocalDateTime.now());
            BookingDAO bookingDAO = new BookingDAO();
            bookingDAO.insert(booking);

        });



        // Ajouter une image si disponible
        if (!offer.getImages().isEmpty()) {
            ImageView imageView = new ImageView(offer.getImages().get(0)); // Utilisez la première image comme exemple
            imageView.setFitHeight(100); // Ajustez la hauteur de l'image selon vos besoins
            imageView.setPreserveRatio(true);
            productDetailsBox.getChildren().add(imageView);
        }

        // Ajouter les labels au conteneur des détails du produit
        productDetailsBox.getChildren().addAll(titleLabel, descriptionLabel, priceLabel,submitButton);
        productBox.getChildren().add(productDetailsBox);

        // Ajouter la boîte du produit à la mise en page principale
        layout.getChildren().add(productBox);









        root.setTop(layout);

        Scene scene = new Scene(root, 1000, 600);
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
        Scene scene = new Scene(root, 1000, 600);
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
}



