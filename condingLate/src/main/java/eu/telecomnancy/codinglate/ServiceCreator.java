package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.FormButton;
import eu.telecomnancy.codinglate.database.dataController.offer.OfferController;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Objects;

public class ServiceCreator {



    private VBox vbox;

    public VBox getVbox() {
        return this.vbox;
    }


    public ServiceCreator() {
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

        Label TitleLabel = new Label("title:");
        TextField TitleField = new TextField();
        gridPane.add(TitleLabel, 0, 0);
        gridPane.add(TitleField, 1, 0);

        Label DescriptionLabel = new Label("description:");
        TextField DescriptionField = new TextField();
        gridPane.add(DescriptionLabel, 0, 1);
        gridPane.add(DescriptionField, 1, 1);

        Label priceLabel = new Label("prix:");
        TextField priceField = new TextField();
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceField, 1, 2);


        Label TypepriceLabel = new Label("Type de prix:");
        ChoiceBox<String> TypePriceBox = new ChoiceBox<>(
                FXCollections.observableArrayList("EURO_PER_HOUR","EURO_PER_DAY","EURO_PER_WEEK", "EURO_PER_MONTH")
        );
        gridPane.add(TypepriceLabel, 0, 3);
        gridPane.add(TypePriceBox, 1, 3);


        Label startDateLabel = new Label("Début de l'offre:");
        DatePicker startDatePicker = new DatePicker();
        gridPane.add(startDatePicker, 0, 4);
        gridPane.add(startDatePicker, 1, 4);

        Label endDateLabel = new Label("Fin de l'offre:");
        DatePicker endDatePicker = new DatePicker();
        gridPane.add(endDatePicker, 0, 5);
        gridPane.add(endDatePicker, 1, 5);


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



            //if nothing is filled
            if(title.isBlank() || description.isBlank() || price.isNaN() ){

                addNewLabel(gridPane, "Informations Manquantes!");

            }



            else{

                PersonController personcontroller = PersonController.getInstance();
                Person currentUser = personcontroller.getCurrentUser();
                if(currentUser instanceof User){
                    User user = (User) currentUser;
                    Service service = new Service(user.getId(),user,title,description,price,priceType,StartDate,EndDate);
                    OfferController offercontroller = new OfferController();
                    offercontroller.insert(service);

                }

                //SceneManager sceneManager = new SceneManager((Stage) this.getScene().getWindow());
                //Scene scene = sceneManager.createSceneProfil(personcontroller);
                //sceneManager.switchScene(scene);

            }
        });

        gridPane.add(submitButton, 0, 6, 2, 6);


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


}
