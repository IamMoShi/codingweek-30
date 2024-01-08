package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.user.User;



public class Rating extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private int value;
    private String comment;
}
