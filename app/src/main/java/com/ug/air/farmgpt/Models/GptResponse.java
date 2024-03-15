package com.ug.air.farmgpt.Models;

public class GptResponse {

    String text, feedback;

    public GptResponse(String text, String feedback) {
        this.text = text;
        this.feedback = feedback;
    }

    public String getText() {
        return text;
    }

    public String getFeedback() {
        return feedback;
    }
}
