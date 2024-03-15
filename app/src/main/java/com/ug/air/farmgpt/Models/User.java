package com.ug.air.farmgpt.Models;

public class User {

    String phone_number, lastname, firstname, location, age_group, gender;

    public User(String phone_number, String lastname, String firstname, String location, String age_group, String gender) {
        this.phone_number = phone_number;
        this.lastname = lastname;
        this.firstname = firstname;
        this.location = location;
        this.age_group = age_group;
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLocation() {
        return location;
    }

    public String getAge_group() {
        return age_group;
    }

    public String getGender() {
        return gender;
    }
}
