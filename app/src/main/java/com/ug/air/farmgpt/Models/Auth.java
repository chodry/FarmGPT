package com.ug.air.farmgpt.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Auth {

    String access_token;

    public Auth(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

}
