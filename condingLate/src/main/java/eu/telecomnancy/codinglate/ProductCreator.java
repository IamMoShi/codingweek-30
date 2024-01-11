package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

public class ProductCreator {

    private VBox vbox;

    private ArrayList<String> urlTempsImages = new ArrayList<>();

    private Stage stage;

    public VBox getVbox() {
        return this.vbox;
    }

    public ProductCreator(Stage stage) {
        this.vbox = createFormPane();
        this.stage = stage;
        addUIControls((GridPane) vbox.getChildren().get(0));
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
        gridPane.add(TitleField, 1, 0);

        CustomTextField DescriptionField = new CustomTextField("Description");
        gridPane.add(DescriptionField, 1, 1);

        CustomTextField priceField = new CustomTextField("Prix");
        gridPane.add(priceField, 1, 2);


        CustomChoiceBox TypePriceBox = new CustomChoiceBox(
                FXCollections.observableArrayList("EURO_PER_HOUR", "EURO_PER_DAY", "EURO_PER_WEEK", "EURO_PER_MONTH")
        );
        TypePriceBox.getItems().add("Type de prix");
        TypePriceBox.setValue("Type de prix");

        gridPane.add(TypePriceBox, 1, 3);


        CustomChoiceBox ProductCategoryBox = new CustomChoiceBox(
                FXCollections.observableArrayList("AUTO", "GARDEN", "HOME", "MULTIMEDIA", "SPORT", "OTHER")
        );
        ProductCategoryBox.getItems().add("Type de produit");
        ProductCategoryBox.setValue("Type de produit");


        gridPane.add(ProductCategoryBox, 1, 4);


        CustomDatePicker startDatePicker = new CustomDatePicker("Début de l'offre:");
        gridPane.add(startDatePicker, 1, 5);

        CustomSpinner startSpinnerhour = new CustomSpinner(0, 24, 0, 1);
        CustomSpinner startSpinnerMinutes = new CustomSpinner(0, 60, 0, 1);

        gridPane.add(startSpinnerhour, 1, 6);
        gridPane.add(startSpinnerMinutes, 1, 7);

        CustomDatePicker endDatePicker = new CustomDatePicker("Fin de l'offre:");
        gridPane.add(endDatePicker, 1, 8);

        CustomSpinner endSpinnerhour = new CustomSpinner(0, 24, 0, 1);
        CustomSpinner endSpinnerMinutes = new CustomSpinner(0, 60, 0, 1);

        gridPane.add(endSpinnerhour, 1, 9);
        gridPane.add(endSpinnerMinutes, 1, 10);

        CustomTextField brandField = new CustomTextField("Marque");
        gridPane.add(brandField, 1, 11);

        CustomTextField ModelField = new CustomTextField("Modèle");
        gridPane.add(ModelField, 1, 12);

        CustomTextField yearField = new CustomTextField("Année");
        gridPane.add(yearField, 1, 13);

        CustomChoiceBox ConditionBox = new CustomChoiceBox(
                FXCollections.observableArrayList("NEW", "GOOD", "USED", "REFURBISHED")
        );

        ConditionBox.getItems().add("état du produit");
        ConditionBox.setValue("état du produit");

        gridPane.add(ConditionBox, 1, 14);


        SearchBarButton chooseImage = new SearchBarButton("chooseImage","Choisir une image");
        chooseImage.initializeButton();
        chooseImage.setPrefWidth(400);
        gridPane.add(chooseImage,0,10);

        chooseImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");

            // Filtrez les fichiers pour ne montrer que les images
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            // Affichez la boîte de dialogue de choix de fichier
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {

                try {
                    String localImagePath = selectedFile.getAbsolutePath();

                    // Définissez le répertoire de stockage
                    String storageDirectory = "OfferImage/tmps"; // Changez le chemin selon vos besoins

                    // Créez le chemin complet pour le répertoire de stockage
                    String fullPath = storageDirectory + "/" + "image_user_n_" + PersonController.getInstance().getCurrentUser().getId() + "uuid=" + UUID.randomUUID() + ".jpg" ;

                    // Copiez le fichier dans le répertoire de stockage
                    Files.copy(selectedFile.toPath(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);

                    // Ajoutez le chemin d'accès local de l'image à la liste des images temporaires
                    urlTempsImages.add(fullPath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        FormButton submitButton = new FormButton("submitButton", "Mettre l'Offre en ligne");
        submitButton.initializeButton();
        submitButton.setPrefWidth(240);


        // Event handling for the submit button
        submitButton.setOnAction(e -> {


            String title = TitleField.getText();
            String description = DescriptionField.getText();
            Double price = Double.valueOf(priceField.getText());
            String priceTypestr = TypePriceBox.getValue();
            PriceType priceType = setPriceType(priceTypestr);
            try {
                String brand = brandField.getText();
                String model = ModelField.getText();

                LocalDateTime StartDate = null;
                if (startDatePicker.getValue() != null) {
                    int year = startDatePicker.getValue().getYear();
                    int month = startDatePicker.getValue().getMonthValue();
                    int day = startDatePicker.getValue().getDayOfMonth();
                    int hour = startSpinnerhour.getValue();
                    int minute = startSpinnerMinutes.getValue();

                    StartDate = LocalDateTime.of(year, month, day, hour, minute);
                }

                LocalDateTime EndDate = null;
                if (endDatePicker.getValue() != null) {
                    int year = endDatePicker.getValue().getYear();
                    int month = endDatePicker.getValue().getMonthValue();
                    int day = endDatePicker.getValue().getDayOfMonth();
                    int hour = endSpinnerhour.getValue();
                    int minute = endSpinnerMinutes.getValue();

                    EndDate = LocalDateTime.of(year, month, day, hour, minute);
                }


            if (yearField.getText().isBlank()) {
                yearField.setText("0");
            }
            int year = Integer.parseInt(yearField.getText());
            System.out.println(year);


            //if nothing is filled
            if (title.isBlank() || price.isNaN()) {

                addNewLabel(gridPane, "Informations Manquantes!");

            } else {
                System.out.println("title: " + title);
                PersonController personcontroller = PersonController.getInstance();
                Person currentUser = personcontroller.getCurrentUser();

                //verify that user is not administrator
                if (currentUser instanceof User) {

                    User user = (User) currentUser;
                    Product product = new Product(user, title, price, priceType);

                    if (!description.isEmpty()) {
                        product.setDescription(description);
                    }


                    //add start date to offer if filled
                    if (StartDate != null && !StartDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0))) {
                        product.setStartingDate(StartDate);
                    }

                    //add end date to offer if filled and start date is already defined
                    if (EndDate != null && !EndDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0)) && !StartDate.isEqual(LocalDateTime.of(1, 1, 1, 0, 0))) {
                        product.setEndingDate(EndDate);
                    }

                    if (!ProductCategoryBox.getValue().isBlank()) {
                        ProductCategory productCategory = setProductCategry(ProductCategoryBox.getValue());
                        product.setCategory(productCategory);
                    }

                    if (!ConditionBox.getValue().isBlank()) {
                        ProductCondition condition = setProductCondition(ConditionBox.getValue());
                        product.setCondition(condition);
                    }
                    product.setBrand(brand);
                    product.setModel(model);
                    product.setYear(year);


                    OfferController offercontroller = new OfferController();
                    offercontroller.insert(product);
                    SceneManager sceneManager = new SceneManager(stage);
                    Scene scene = sceneManager.createSceneDisplayProduct();
                    sceneManager.switchScene(scene);
                }
            }
            }catch (NumberFormatException exception){
                    addNewLabel(gridPane,"Format de prix invalide !");
                }



        });

        gridPane.add(submitButton, 1, 16, 2, 6);


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

    public ProductCondition setProductCondition(String productconditionstr) {


        if (Objects.equals(productconditionstr, "NEW")) {
            return ProductCondition.NEW;
        }

        if (Objects.equals(productconditionstr, "GOOD")) {
            return ProductCondition.GOOD;
        }

        if (Objects.equals(productconditionstr, "USED")) {
            return ProductCondition.USED;
        }

        if (Objects.equals(productconditionstr, "REFURBISHED")) {
            return ProductCondition.REFURBISHED;
        }

        return ProductCondition.NEW;
    }

    public ProductCategory setProductCategry(String productcategorystr) {


        if (Objects.equals(productcategorystr, "AUTO")) {
            return ProductCategory.AUTO;
        }

        if (Objects.equals(productcategorystr, "GARDEN")) {
            return ProductCategory.GARDEN;
        }

        if (Objects.equals(productcategorystr, "HOME")) {
            return ProductCategory.HOME;
        }

        if (Objects.equals(productcategorystr, "MULTIMEDIA")) {
            return ProductCategory.MULTIMEDIA;
        }

        if (Objects.equals(productcategorystr, "SPORT")) {
            return ProductCategory.SPORT;
        }

        if (Objects.equals(productcategorystr, "OTHER")) {
            return ProductCategory.OTHER;
        }

        return ProductCategory.OTHER;
    }


}
