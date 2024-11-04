package com.example.hanying.Domain;

public class ProductDomain {
    private String productID, productName, image;
    private double price;
    private int stock;

    public ProductDomain() {

    }

    public ProductDomain(String productID, String productName, double price, int stock, String image) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public String getProductID() { return productID; }
    public void setProductID(String productID) {this.productID = productID;}

    public String getProductName() { return productName; }
    public void setProductName(String productName) {this.productName = productName;}

    public double getPrice() { return price;}
    public void setPrice(double price) {this.price = price;}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public String getImage() {return image;}
    public void setImage (String image) {this.image = image;}
}
