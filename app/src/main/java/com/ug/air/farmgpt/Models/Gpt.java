package com.ug.air.farmgpt.Models;

public class Gpt {

    int response_id;
    String answer;

    public Gpt(int response_id, String answer) {
        this.response_id = response_id;
        this.answer = answer;
    }

    public int getResponse_id() {
        return response_id;
    }

    public String getAnswer() {
        return answer;
    }
}
