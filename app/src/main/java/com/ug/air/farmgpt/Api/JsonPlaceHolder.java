package com.ug.air.farmgpt.Api;

import com.ug.air.farmgpt.Models.Auth;
import com.ug.air.farmgpt.Models.Chat;
import com.ug.air.farmgpt.Models.Gpt;
import com.ug.air.farmgpt.Models.Login;
import com.ug.air.farmgpt.Models.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface JsonPlaceHolder {

    @POST("api/v1/auth/register")
    Call<String> register(@Body User user);

    @POST("api/v1/auth/login")
    Call<Auth> login(@Body Login login);


    @POST("query/")
    Call<Gpt> sendQuery(@Body Chat chat);

}
