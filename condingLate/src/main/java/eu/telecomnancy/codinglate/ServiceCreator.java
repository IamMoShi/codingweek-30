package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonDAO;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ServiceCreator {

    private ArrayList<String> urlTempsImages = new ArrayList<>();

    private FlowPane imagePane;

    private VBox vbox;

    private Stage stage;

    public VBox getVbox() {
        return this.vbox;
    }


    public ServiceCreator(Stage stage) {
        this.vbox = createFormPane();
        this.imagePane = new FlowPane();
        this.imagePane.setHgap(10);
        this.imagePane.setVgap(10);
        this.imagePane.setPrefWrapLength(400);
        this.stage=stage;


        addUIControls((GridPane) vbox.getChildren().get(0));
        ((GridPane) vbox.getChildren().get(0)).add(imagePane, 0, 17);


    }


    private VBox createFormPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);


        vbox.getChildren().add(gridPane);

        return vbox;
    }

    private void addUIControls(GridPane gridPane) {
        // Add controls to the gridPane

        CustomTextField TitleField = new CustomTextField("Titre");
        TitleField.getStyleClass().add("necessary");
        gridPane.add(TitleField, 0, 0);

        CustomTextField DescriptionField = new CustomTextField("Description");
        gridPane.add(DescriptionField, 0, 1);

        CustomTextField priceField = new CustomTextField("Prix");
        priceField.getStyleClass().add("necessary");
        gridPane.add(priceField, 0, 2);


        CustomChoiceBox TypePriceBox = new CustomChoiceBox(
                FXCollections.observableArrayList("EURO_PER_HOUR", "EURO_PER_DAY", "EURO_PER_WEEK", "EURO_PER_MONTH")
        );
        TypePriceBox.getStyleClass().add("necessary");
        gridPane.add(TypePriceBox, 0, 3);


        CustomDatePicker startDatePicker = new CustomDatePicker("Début de l'offre");

        gridPane.add(startDatePicker, 0, 4);

        CustomSpinner starthourspinner = new CustomSpinner(0, 24, 0, 1);
        gridPane.add(starthourspinner, 0, 5);
        CustomSpinner minutespinner = new CustomSpinner(0, 60, 0, 1);
        gridPane.add(minutespinner, 0, 6);


        CustomDatePicker endDatePicker = new CustomDatePicker("Fin de l'offre");
        gridPane.add(endDatePicker, 0, 7);

        CustomSpinner endhourspinner = new CustomSpinner(0, 24, 0, 1);
        gridPane.add(endhourspinner, 0, 8);
        CustomSpinner endminutespinner = new CustomSpinner(0, 60, 0, 1);
        gridPane.add(endminutespinner, 0, 9);


        SearchBarButton chooseImage = new SearchBarButton("chooseImage", "Choisir une image");
        chooseImage.initializeButton();
        chooseImage.setPrefWidth(400);
        gridPane.add(chooseImage, 0, 10);

        chooseImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");

            // Filtrez les fichiers pour ne montrer que les images
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            // Affichez la boîte de dialogue de choix de fichier
            File selectedFile = fileChooser.showOpenDialog(null);


            if (selectedFile != null) {
                String fullPath = null;
                try {
                    String localImagePath = selectedFile.getAbsolutePath();

                    // Définissez le répertoire de stockage
                    String storageDirectory = "OfferImage/tmps"; // Changez le chemin selon vos besoins

                    // Créez le chemin complet pour le répertoire de stockage
                    fullPath = storageDirectory + "/" + "image_user_n_" + PersonDAO.getInstance().getCurrentUser().getId() + "uuid=" + UUID.randomUUID() + ".jpg";

                    // Copiez le fichier dans le répertoire de stockage
                    Files.copy(selectedFile.toPath(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);

                    // Ajoutez le chemin d'accès local de l'image à la liste des images temporaires
                    urlTempsImages.add(fullPath);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                addImageBox(selectedFile.getAbsolutePath(), fullPath);
            }


        });


        FormButton submitButton = new FormButton("submitButton", "Mettre l'Offre en ligne");
        submitButton.initializeButton();

        submitButton.setPrefWidth(240);

        // Event handling for the submit button
        submitButton.setOnAction(e -> {

            String title = TitleField.getText();
            String description = DescriptionField.getText();
            String priceText = priceField.getText();
            if (priceText.isBlank()) {
                addNewLabel(gridPane, "Le champs du prix est vide");
                return;
            }
            try {
                Double price = Double.valueOf(priceText);
                String priceTypestr = TypePriceBox.getValue();
                PriceType priceType = setPriceType(priceTypestr);

                LocalDateTime StartDate = null;
                if (startDatePicker.getValue() != null) {
                    int year = startDatePicker.getValue().getYear();
                    int month = startDatePicker.getValue().getMonthValue();
                    int day = startDatePicker.getValue().getDayOfMonth();
                    int hour = starthourspinner.getValue();
                    int minute = minutespinner.getValue();

                    StartDate = LocalDateTime.of(year, month, day, hour, minute);
                }

                LocalDateTime EndDate = null;
                if (endDatePicker.getValue() != null) {
                    int year = endDatePicker.getValue().getYear();
                    int month = endDatePicker.getValue().getMonthValue();
                    int day = endDatePicker.getValue().getDayOfMonth();
                    int hour = endhourspinner.getValue();
                    int minute = endminutespinner.getValue();

                    EndDate = LocalDateTime.of(year, month, day, hour, minute);
                }

                //if nothing is filled
                if (title.isBlank() || price.isNaN()) {
                    addNewLabel(gridPane, "Informations Manquantes!");
                } else {

                    PersonDAO personcontroller = PersonDAO.getInstance();
                    Person currentUser = personcontroller.getCurrentUser();
                    if (currentUser instanceof User) {
                        User user = (User) currentUser;
                        Service service = new Service(user, title, price, priceType);
                        if (!description.isEmpty()) {
                            service.setDescription(description);
                        }
                        //add start date to offer if filled
                        if (StartDate != null && !StartDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0))) {
                            service.setStartingDate(StartDate);
                        }

                        //add end date to offer if filled and start date is already defined
                        if (EndDate != null && !EndDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0)) && !StartDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0))) {
                            service.setEndingDate(EndDate);
                        }

                        ArrayList<String> imageUrls = saveImages();
                        service.setImages(imageUrls);

                        OfferController offercontroller = new OfferController();
                        offercontroller.insert(service);
                    }

                    SceneManager sceneManager = new SceneManager(stage);
                    Scene scene = sceneManager.createSceneDisplayProduct();
                    sceneManager.switchScene(scene);

                }


            } catch (NumberFormatException exception) {
                addNewLabel(gridPane, "Format de prix invalide !");
            }

        });

        gridPane.add(submitButton, 0, 11, 2, 6);


    }

    private ArrayList<String> saveImages() {
        ArrayList<String> imageUrls = new ArrayList<>();
        for (String tempImagePath : urlTempsImages) {
            try {
                // Téléchargez l'image à partir de l'URL
                File file = new File(tempImagePath);
                String newPath = "OfferImage/" + file.getName();

                // Enregistrez l'image localement
                Files.copy(file.toPath(), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
                imageUrls.add(file.getName());
                deleteTempImage(tempImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageUrls;
    }

    private void deleteTempImage(String tempImagePath) {
        try {
            Files.deleteIfExists(Paths.get(tempImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAscii(String str) {
        return str.matches("\\A\\p{ASCII}*\\z");
    }

    private void addNewLabel(GridPane root, String str) {

        Label Label = new Label(str);

        root.add(Label, 1, 8);
    }


    public PriceType setPriceType(String pricetypestr) {


        if (Objects.equals(pricetypestr, "EURO_PER_HOUR")) {
            return PriceType.EURO_PER_HOUR;
        }

        if (Objects.equals(pricetypestr, "EURO_PER_DAY")) {
            return PriceType.EURO_PER_DAY;
        }

        if (Objects.equals(pricetypestr, "EURO_PER_WEEK")) {
            return PriceType.EURO_PER_HOUR;
        }

        if (Objects.equals(pricetypestr, "EURO_PER_MONTH")) {
            return PriceType.EURO_PER_MONTH;
        }

        return PriceType.EURO_PER_DAY;
    }

    private void addImageBox(String imagePath, String imageName) {
        HBox imageBox = new HBox();
        Label imageNameLabel = new Label(new File(imagePath).getName());
        Button deleteImageButton = new Button( "Supprimer");
        deleteImageButton.setStyle("-fx-background-color: #ffffff; /* Couleur de fond */\n" +
                "        -fx-text-fill: black; /* Couleur du texte */\n" +
                "        -fx-font-size: 14px; /* Taille de la police */\n" +
                "        -fx-padding: 8px 16px; /* Espacement intérieur du bouton */\n" +
                "        -fx-border-radius: 5px; /* Rayon des coins */\n" +
                "        -fx-cursor: hand; /* Curseur au survol du bouton */");
        deleteImageButton.setPrefWidth(100);



        deleteImageButton.setOnAction(event -> {
            urlTempsImages.remove(imageName);
            deleteTempImage(imageName);
            imagePane.getChildren().remove(imageBox);
        });
        imageBox.getChildren().addAll(imageNameLabel, deleteImageButton);
        imagePane.getChildren().add(imageBox);
    }


}
