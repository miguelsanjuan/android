package com.sellnow.dataset;

public class DataSetCart {
    /*private int productID;
    private String userEmail;
    private int quantity;
    private String productURLPicture;*/

    private String productName;
    private String productDescription;
    private int quantity;
    private double unitPrice;
    private String productURLPicture;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductURLPicture() {
        return productURLPicture;
    }

    public void setProductURLPicture(String productURLPicture) {
        this.productURLPicture = productURLPicture;
    }
}
