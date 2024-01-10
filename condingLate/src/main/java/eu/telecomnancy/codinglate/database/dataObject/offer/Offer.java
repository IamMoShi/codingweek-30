package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;
import eu.telecomnancy.codinglate.database.dataObject.StringControlled;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;


public abstract class Offer extends DataObject {
    private int id;
    private User user;
    private String title;
    private String description;
    private double price;
    private PriceType priceType;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;

    private ArrayList<String> images;

    public Offer(int id, User user, String title, String description, double price, PriceType priceType, LocalDateTime startingDate, LocalDateTime endingDate, ArrayList<String> images) {
        setId(id);
        setUser(user);
        setTitle(title);
        setDescription(description);
        setPrice(price);
        setPriceType(priceType);
        setStartingDate(startingDate);
        setEndingDate(endingDate);
        setImages(images);
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

    public double getPrice() {
        return price;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = StringControlled.correctedString(title, 64);
    }

    public void setDescription(String description) {
        this.description = StringControlled.correctedString(description, 512);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

}
