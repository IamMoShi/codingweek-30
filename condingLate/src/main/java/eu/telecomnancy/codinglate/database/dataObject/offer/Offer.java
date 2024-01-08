package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDate;


public abstract class Offer extends DataObject {
    private int id;
    private User user;
    private String title;
    private String description;
    private float price;
    private PriceType priceType;
    private LocalDate startingDate;
    private LocalDate endingDate;
}
