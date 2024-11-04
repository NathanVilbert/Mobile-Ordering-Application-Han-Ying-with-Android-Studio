package com.example.hanying.Domain;

public class TrendsDomain {
    private int imageResourceId;
    private String title;
    private int buttonAction;

    public TrendsDomain(int imageResourceId, String title, int buttonAction) {
        this.imageResourceId = imageResourceId;
        this.title = title;
        this.buttonAction = buttonAction;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTitle() {
        return title;
    }
    public int getButtonAction() {
        return buttonAction;
    }
}
