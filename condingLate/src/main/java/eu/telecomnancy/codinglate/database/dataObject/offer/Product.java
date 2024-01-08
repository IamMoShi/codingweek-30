package eu.telecomnancy.codinglate.database.dataObject.offer;


import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.user.User;


import java.time.LocalDate;
import java.util.ArrayList;


public class Product extends Offer {
    private ProductCategory category;
    private String brand;
    private String model;
    private int year;
    private ProductCondition condition;
    private ArrayList<ProductImage> images;

    public Product(int id, User user, String title, String description, float price, PriceType priceType, LocalDate startingDate, LocalDate endingDate, ProductCategory category, String brand, String model, int year, ProductCondition condition, ArrayList<ProductImage> images) {
        super(id, user, title, description, price, priceType, startingDate, endingDate);
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.condition = condition;
        this.images = images;
    }

    public Product(User user, String title, float price, PriceType priceType) {
        super(-1, user, title, "", price, priceType, null, null);
        this.category = null;
        this.brand = "";
        this.model = "";
        this.year = 0;
        this.condition = null;
        this.images = new ArrayList<>();
    }

    // Getters ------------------------------------------------------------------------------------

    public ProductCategory getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public ArrayList<ProductImage> getImages() {
        return images;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public void setImages(ArrayList<ProductImage> images) {
        this.images = images;
    }

}
