package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.UI.*;
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
import javafx.stage.FileChooser;

import java.io.File;
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

        CustomTextField TitleField = new CustomTextField("Titre");
        gridPane.add(TitleField, 0, 0);

        CustomTextField DescriptionField = new CustomTextField("Description");
        gridPane.add(DescriptionField, 0, 1);

       CustomTextField priceField = new CustomTextField("Prix");
        gridPane.add(priceField, 0, 2);


        CustomChoiceBox TypePriceBox = new CustomChoiceBox(
                FXCollections.observableArrayList("EURO_PER_HOUR","EURO_PER_DAY","EURO_PER_WEEK", "EURO_PER_MONTH")
        );
        gridPane.add(TypePriceBox, 0, 3);


        CustomDatePicker startDatePicker = new CustomDatePicker("Début de l'offre");
        gridPane.add(startDatePicker, 0, 4);


        CustomDatePicker endDatePicker = new CustomDatePicker("Fin de l'offre");
        gridPane.add(endDatePicker, 0, 5);


        SearchBarButton chooseImage = new SearchBarButton("chooseImage","Choisir une image");
        chooseImage.initializeButton();
        gridPane.add(chooseImage,0,6);

        chooseImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");

            // Filtrez les fichiers pour ne montrer que les images
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            // Affichez la boîte de dialogue de choix de fichier
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                // Vous pouvez utiliser le chemin du fichier sélectionné comme nécessaire
                String imagePath = selectedFile.getAbsolutePath();
                // Vous pouvez faire quelque chose avec le chemin de l'image ici
                System.out.println("Image choisie : " + imagePath);
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
            if (priceText.isBlank()){
                addNewLabel(gridPane,"Le champs du prix est vide");
                return;
            }
            try {
                Double price = Double.valueOf(priceText);
                String priceTypestr = TypePriceBox.getValue();
                PriceType priceType = setPriceType(priceTypestr);
                LocalDate StartDate = startDatePicker.getValue();
                LocalDate EndDate = endDatePicker.getValue();

                //if nothing is filled
                if(title.isBlank() || price.isNaN() ){
                    addNewLabel(gridPane, "Informations Manquantes!");
                }
                else{

                    PersonController personcontroller = PersonController.getInstance();
                    Person currentUser = personcontroller.getCurrentUser();
                    if(currentUser instanceof User){
                        User user = (User) currentUser;
                        Service service = new Service(user,title,price,priceType);
                        if(!description.isEmpty()){
                            service.setDescription(description);
                        }
                        //add start date to offer if filled
                        if(StartDate!= null && !StartDate.isEqual(LocalDate.of(1, 1, 1))){
                            service.setStartingDate(StartDate);
                        }

                        //add end date to offer if filled and start date is already defined
                        if(EndDate!= null && !EndDate.isEqual(LocalDate.of(1, 1, 1)) && !StartDate.isEqual(LocalDate.of(1, 1, 1))){
                            service.setEndingDate(EndDate);
                        }

                        OfferController offercontroller = new OfferController();
                        offercontroller.insert(service);
                    }

                }



            } catch (NumberFormatException exception){
                addNewLabel(gridPane,"Format de prix invalide !");
            }




        });

        gridPane.add(submitButton, 0, 7, 2, 6);


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
