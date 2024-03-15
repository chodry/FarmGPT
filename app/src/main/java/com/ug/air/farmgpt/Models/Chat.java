package com.ug.air.farmgpt.Models;

public class Chat {

    String question, language, category, sub_category, topic, sub_topic, location;
    Boolean simple_response;

    public Chat(String question, String language, String category, String sub_category, String topic, String sub_topic, String location, Boolean simple_response) {
        this.question = question;
        this.language = language;
        this.category = category;
        this.sub_category = sub_category;
        this.topic = topic;
        this.sub_topic = sub_topic;
        this.location = location;
        this.simple_response = simple_response;
    }
}
