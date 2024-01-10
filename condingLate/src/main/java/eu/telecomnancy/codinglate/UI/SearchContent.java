package eu.telecomnancy.codinglate.UI;

import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class SearchContent extends HBox {

    public SearchContent() {
        super();
        createGridPane();

    }


    private void createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);


        TextField searchField = searchField();
        GridPane.setConstraints(searchField, 0, 0);
        gridPane.getChildren().add(searchField);

        Button searchButton = searchButton();
        GridPane.setConstraints(searchButton, 1, 0);
        gridPane.getChildren().add(searchButton);

        GridPane options = options();
        GridPane.setConstraints(options, 0, 1);
        gridPane.getChildren().add(options);

        getChildren().add(gridPane);

        HBox.setHgrow(gridPane, javafx.scene.layout.Priority.ALWAYS);
    }

    private TextField searchField() {
        TextField searchField = new TextField();
        searchField.setPromptText("Entrer votre recherche");
        searchField.setPrefWidth(600);
        searchField.setPrefHeight(60);
        searchField.setPadding(new Insets(10, 10, 10, 10));
        searchField.setFont(javafx.scene.text.Font.font("Arial", 20));
        searchField.setStyle("-fx-background-radius: 20; " +
                "-fx-border-radius: 20; ");
        searchField.setPromptText("Entrer votre recherche");
        searchField.getStyleClass().add("search-field");
        searchField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                System.out.println("Enter pressed");
            }
        });
        return searchField;
    }

    private Button searchButton() {
        Button searchButton = new Button("Rechercher");
        searchButton.setPrefWidth(160);
        searchButton.setPrefHeight(50);
        searchButton.setPadding(new Insets(10, 10, 10, 10));
        searchButton.setFont(javafx.scene.text.Font.font("Arial", 20));
        searchButton.setStyle("-fx-background-radius: 20; " +
                "-fx-border-radius: 20; ");

        searchButton.setOnAction(e -> {
            searching();
        });

        return searchButton;
    }

    private void searching() {
        System.out.println("Searching...");

        // Récupérer les données de la recherche à partir du Hbox -> gridpane1 -> gridpane2
        try {
            GridPane gridPane1 = (GridPane) getChildren().get(0);
            GridPane gridPane2 = (GridPane) gridPane1.getChildren().get(2);
            ToggleButton service = ((ToggleButton) gridPane2.getChildren().get(0));
            Boolean isService = service.isSelected();
            TextField distanceField = ((TextField) gridPane2.getChildren().get(2));

            assert PersonController.getInstance().getCurrentUser() != null;

            int distance = 0;

            if (!distanceField.getText().isEmpty()) {
                distance = Integer.parseInt(distanceField.getText());
            }
            System.out.println(PersonController.getInstance().getCurrentUser().getFirstname());

            ArrayList<Offer> offers = new OfferController().getOfferByParameters(isService, null, "", "", null, -1);
            offers = new OfferController().checkDistance(offers, PersonController.getInstance().getCurrentUser(), distance);

            System.out.println("Offres trouvées : " + offers.size());

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des données de la recherche.");
        }

    }

    private GridPane options() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

        serviceOrProduct(gridPane);
        //priceConstraints(gridPane);
        createDistanceField(gridPane);

        return gridPane;
    }

    private void serviceOrProduct(GridPane gridPane) {
        ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton service = new ToggleButton("Service");
        service.setToggleGroup(toggleGroup);
        service.setMinHeight(50);
        service.setMinWidth(150);
        service.setFont(javafx.scene.text.Font.font("Arial", 20));
        ToggleButton product = new ToggleButton("Product");
        product.setToggleGroup(toggleGroup);
        product.setMinHeight(50);
        product.setMinWidth(150);
        product.setFont(javafx.scene.text.Font.font("Arial", 20));

        toggleGroup.selectToggle(product);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Toggle selectedToggle = toggleGroup.getSelectedToggle();
                System.out.println("Option sélectionnée : " + ((ToggleButton) selectedToggle).getText());
            }
        });

        gridPane.add(service, 0, 0);
        gridPane.add(product, 1, 0);
    }

    private void priceConstraints(GridPane gridPane) {
        TextField minPrice = new TextField("0");
        minPrice.setPromptText("Prix min");

        TextField maxPrice = new TextField("100000");
        maxPrice.setPromptText("Prix max");

        maxPrice.setOnAction(e -> {
            validatePriceInput(minPrice, maxPrice);
        });

        minPrice.setOnAction(e -> {
            validatePriceInput(minPrice, maxPrice);
        });

        minPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePriceInput(minPrice, maxPrice);
            }
        });

        maxPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePriceInput(minPrice, maxPrice);
            }
        });

        gridPane.add(minPrice, 2, 0);
        gridPane.add(maxPrice, 3, 0);
    }

    private void validatePriceInput(TextField minPrice, TextField maxPrice) {
        try {
            int leftValue = Integer.parseInt(minPrice.getText());
            int rightValue = Integer.parseInt(maxPrice.getText());

            if (leftValue > rightValue) {
                showAlert("L'entier de gauche doit être inférieur à celui de droite.");
                minPrice.requestFocus();
            }
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des entiers valides.");
            minPrice.clear();
            maxPrice.clear();

            System.out.println("Veuillez entrer des entiers valides.");
            minPrice.setText("0");
            maxPrice.setText("100000");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void createDistanceField(GridPane gridPane) {
        TextField distanceField = new TextField();
        distanceField.setPromptText("Distance maximale km");
        distanceField.setPrefWidth(200);
        distanceField.setPrefHeight(50);
        distanceField.setStyle("-fx-background-radius: 20; " +
                "-fx-border-radius: 20;" +
                "-fx-font-size: 18px;");


        distanceField.setOnAction(e -> {
            validateDistanceInput(distanceField);
        });

        distanceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateDistanceInput(distanceField);
            }
        });


        gridPane.add(distanceField, 2, 0);
    }

    private void validateDistanceInput(TextField distanceField) {
        try {
            if (!distanceField.getText().isEmpty()) {
                int distance = Integer.parseInt(distanceField.getText());
                if (distance <= 0) {
                    showAlert("La distance doit être supérieure à 0.");
                    distanceField.requestFocus();
                }
            }
        } catch (NumberFormatException exception) {
            showAlert("Veuillez entrer un entier valide.");
            distanceField.clear();
        }
    }
}
