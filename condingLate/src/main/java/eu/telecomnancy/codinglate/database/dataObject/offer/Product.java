package eu.telecomnancy.codinglate.database.dataObject.offer;


import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;


import java.util.ArrayList;


public class Product extends Offer {
    private ProductCategory category;
    private String brand;
    private String model;
    private int year;
    private ProductCondition condition;
    private ArrayList<ProductImage> images;

}
