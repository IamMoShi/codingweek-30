package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.StringControlled;
import eu.telecomnancy.codinglate.database.dataObject.user.User;


public class Rating extends DataObject {
    private int id;
    private Offer offer;
    private User user;
    private int value;
    private String comment;

    public Rating(int id, Offer offer, User user, int value, String comment) {
        this.id = id;
        this.offer = offer;
        this.user = user;
        this.value = value;
        this.comment = StringControlled.correctedString(comment, 512);
    }

    public Rating(Offer offer, User user, int value) {
        this(-1, offer, user, value, "");
    }

    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
