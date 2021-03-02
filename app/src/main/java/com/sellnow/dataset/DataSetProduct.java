package com.sellnow.dataset;

public class DataSetProduct {

    private int productID;
    private int categoryID;
    private String productName;
    private String productDescription;
    private double unitPrice;
    private String productURLPicture;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

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



