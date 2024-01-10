package eu.telecomnancy.codinglate.database.dataObject.offer;


import eu.telecomnancy.codinglate.database.dataObject.StringControlled;
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

    public Product(int id,
                   User user,
                   String title,
                   String description,
                   double price,
                   PriceType priceType,
                   LocalDate startingDate,
                   LocalDate endingDate,
                   ProductCategory category,
                   String brand,
                   String model,
                   int year,
                   ProductCondition condition,
                   ArrayList<ProductImage> images) {
        super(id, user, title, description, price, priceType, startingDate, endingDate, images);
        this.category = category;
        setBrand(brand);
        setModel(model);
        setYear(year);
        setCondition(condition);
        setImages(images);
    }

    public Product(User user, String title, double price, PriceType priceType) {
        super(-1, user, title, "", price, priceType, null, null, new ArrayList<>());
        setCategory(ProductCategory.OTHER);
        setBrand("");
        setModel("");
        setYear(0);
        setCondition(null);
        setImages(new ArrayList<>());
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


    // Setters ------------------------------------------------------------------------------------

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = StringControlled.correctedString(brand, 64);
    }

    public void setModel(String model) {
        this.model = StringControlled.correctedString(model, 64);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }



}
