package com.example.hanying.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String productName;
    private String image;
    private String productDesc;
    private Double price;
    private int numberInCart;

    public FoodDomain(String productName, String image, String productDesc, Double price) {
        this.productName = productName;
        this.image = image;
        this.productDesc = productDesc;
        this.price = price;
    }

    public FoodDomain(String productName, String image, String productDesc, Double price, int numberInCart) {
        this.productName = productName;
        this.image = image;
        this.productDesc = productDesc;
        this.price = price;
        this.numberInCart = numberInCart;
    }

    public String getName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return productDesc;
    }

    public void setDescription(String productDesc) {
        this.productDesc = productDesc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
