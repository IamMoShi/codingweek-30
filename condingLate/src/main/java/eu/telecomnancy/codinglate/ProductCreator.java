package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.CustomChoiceBox;
import eu.telecomnancy.codinglate.UI.CustomDatePicker;
import eu.telecomnancy.codinglate.UI.CustomTextField;
import eu.telecomnancy.codinglate.UI.FormButton;
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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Objects;

public class ProductCreator {

    private VBox vbox;

    public VBox getVbox() {
        return this.vbox;
    }

    public ProductCreator() {
        this.vbox = createFormPane();
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
                FXCollections.observableArrayList("EURO_PER_HOUR","EURO_PER_DAY","EURO_PER_WEEK", "EURO_PER_MONTH")
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

        CustomDatePicker endDatePicker = new CustomDatePicker("Fin de l'offre:");
        gridPane.add(endDatePicker, 1, 6);

        CustomTextField brandField = new CustomTextField("Marque");
        gridPane.add(brandField, 1, 7);

        CustomTextField ModelField = new CustomTextField("Modèle");
        gridPane.add(ModelField, 1, 8);

        CustomTextField yearField = new CustomTextField("Année");
        gridPane.add(yearField, 1, 9);

        CustomChoiceBox ConditionBox = new CustomChoiceBox(
                FXCollections.observableArrayList("NEW", "GOOD", "USED", "REFURBISHED")
        );

        ConditionBox.getItems().add("état du produit");
        ConditionBox.setValue("état du produit");

        gridPane.add(ConditionBox, 1, 10);





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
            LocalDate StartDate = startDatePicker.getValue();
            LocalDate EndDate = endDatePicker.getValue();
            String brand = brandField.getText();
            String model = ModelField.getText();
            //int year = Integer.parseInt(yearField.getText());

            if(ConditionBox.getValue() != null) {
                ProductCondition condition = setProductCondition(ProductCategoryBox.getValue());
            }

            //if nothing is filled
            if(title.isBlank() || price.isNaN() ){

                addNewLabel(gridPane, "Informations Manquantes!");

            }

            else{
                System.out.println("title: " + title);
                PersonController personcontroller = PersonController.getInstance();
                Person currentUser = personcontroller.getCurrentUser();

                //verify that user is not administrator
                if(currentUser instanceof User){

                    User user = (User) currentUser;
                    Product product = new Product(user,title,price,priceType);

                    if(!description.isEmpty()){
                        product.setDescription(description);
                    }


                    //add start date to offer if filled
                    if(!StartDate.isEqual(LocalDate.of(1, 1, 1))){
                        product.setStartingDate(StartDate);
                    }

                    //add end date to offer if filled and start date is already defined
                    if(!EndDate.isEqual(LocalDate.of(1, 1, 1)) && !StartDate.isEqual(LocalDate.of(1, 1, 1))){
                        product.setEndingDate(EndDate);
                    }

                    if(!ProductCategoryBox.getValue().isBlank()) {
                        ProductCategory productCategory = setProductCategry(ProductCategoryBox.getValue());
                        product.setCategory(productCategory);
                    }

                    if(!ConditionBox.getValue().isBlank()) {
                        ProductCondition condition = setProductCondition(ProductCategoryBox.getValue());
                        product.setCondition(condition);
                    }


                    OfferController offercontroller = new OfferController();
                    offercontroller.insert(product);
                }


            }
        });

        gridPane.add(submitButton, 1, 11, 2, 6);


    }

    private boolean isAscii(String str) {
        return str.matches("\\A\\p{ASCII}*\\z");
    }

    private void addNewLabel(GridPane root, String str) {

        Label Label = new Label(str);

        root.add(Label,1,8);
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
