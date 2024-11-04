package com.example.hanying.Domain;

public class CategoryDomain {
    private String categoryName;
    private String categoryImage;

    public CategoryDomain(String categoryName, String categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getName() {
        return categoryName;
    }
    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return categoryImage;
    }
    public void setImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
