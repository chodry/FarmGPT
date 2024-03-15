package com.ug.air.farmgpt.Utils;

import android.content.Context;
import android.widget.Toast;

import com.ug.air.farmgpt.Activities.ChatActivity;
import com.ug.air.farmgpt.Api.ApiClient;
import com.ug.air.farmgpt.Api.JsonPlaceHolder;

public class Functions {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static JsonPlaceHolder jsonPlaceHolder(String value) {
        JsonPlaceHolder jsonPlaceHolder;

        if (value.equals("account")){
            jsonPlaceHolder = ApiClient.getClientAccount().create(JsonPlaceHolder.class);
        }
        else {
            jsonPlaceHolder = ApiClient.getClientChat().create(JsonPlaceHolder.class);
        }

        return jsonPlaceHolder;
    }

}
