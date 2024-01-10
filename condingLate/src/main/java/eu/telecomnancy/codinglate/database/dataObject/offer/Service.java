package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.user.User;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class Service extends Offer {
    public Service(int id, User user, String title, String description, double price, PriceType priceType, LocalDateTime startingDate, LocalDateTime endingDate, ArrayList<String> images) {
        super(id, user, title, description, price, priceType, startingDate, endingDate, images);
    }

    public Service(User user, String title, double price, PriceType priceType) {
        super(-1, user, title, "", price, priceType, null, null, new ArrayList<>());
    }
}
