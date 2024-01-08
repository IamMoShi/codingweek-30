package eu.telecomnancy.codinglate.database.dataObject.offer;

import eu.telecomnancy.codinglate.database.dataObject.DataObject;

public class ProductImage extends DataObject {
    private int id;
    private Product product;
    private String path;

    public ProductImage(int id, Product product, String path) {
        this.id = id;
        this.product = product;
        this.path = path;
    }

    public ProductImage(Product product, String path) {
        this(-1, product, path);
    }

    // Getters ------------------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getPath() {
        return path;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
