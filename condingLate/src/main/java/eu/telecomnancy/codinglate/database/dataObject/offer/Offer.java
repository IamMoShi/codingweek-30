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

    public Offer(int id, User user, String title, String description, float price, PriceType priceType, LocalDate startingDate, LocalDate endingDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.price = price;
        this.priceType = priceType;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

}
