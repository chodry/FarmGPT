package com.ug.air.farmgpt.Models;

public class Feedback {
    Boolean isClicked;

    public Feedback(Boolean isClicked) {
        this.isClicked = isClicked;
    }

    public Boolean getClicked() {
        return isClicked;
    }
}
