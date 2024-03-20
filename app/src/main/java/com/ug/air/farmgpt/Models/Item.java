package com.ug.air.farmgpt.Models;

import androidx.annotation.DrawableRes;

public class Item {

    private int type;
    private Object object;
    private String showImage; // Flag to control image visibility
    @DrawableRes
    private int imageResource; // Resource ID for the image

    public Item(int type, Object object, String showImage, int imageResource) {
        this.type = type;
        this.object = object;
        this.showImage = showImage;
        this.imageResource = imageResource;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public String getShowImage() {
        return showImage;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
