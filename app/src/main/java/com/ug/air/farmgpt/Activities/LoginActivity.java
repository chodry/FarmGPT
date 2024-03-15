package com.ug.air.farmgpt.Activities;

import static com.ug.air.farmgpt.Activities.SplashActivity.SHARED_PREFS;
import static com.ug.air.farmgpt.Activities.SplashActivity.USERNAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.ug.air.farmgpt.Models.Auth;
import com.ug.air.farmgpt.Models.Error;
import com.ug.air.farmgpt.Models.Login;
import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Utils.Functions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView txtSignUp;
    EditText edUsername;
    TextInputEditText tnPassword;
    ProgressBar progressBar;
    String password, username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the status bar background color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
        }

        // Set the status bar text and icons color to the primary color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_login);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnSignIn = findViewById(R.id.btnsign);
        txtSignUp = findViewById(R.id.signup);
        edUsername = findViewById(R.id.username);
        tnPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);

        txtSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnSignIn.setOnClickListener(view -> {
            username = edUsername.getText().toString().trim();
            password = tnPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Functions.showToast(this, "Please fill in all fields");
            }
            else {
                login();
            }
        });

    }

    private void login() {
        btnSignIn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        Login login1 = new Login(username, password);
        Call<Auth> call = Functions.jsonPlaceHolder("account").login(login1);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                btnSignIn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    editor.putString(USERNAME, username);
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else {
                    try{
                        int statusCode = response.code();
                        if (statusCode == 401){
                            String error = response.errorBody().string();
                            Gson gson = new Gson();
                            Error error1 = gson.fromJson(error, Error.class);
                            String message = error1.getError();
                            Functions.showToast(LoginActivity.this, message);
                        }
                        else {
                            Functions.showToast(LoginActivity.this, "Something went wrong, please try again later");
                        }
                    }
                    catch (IOException e) {
                        Functions.showToast(LoginActivity.this, "Something went wrong, please try again later");
                    }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                btnSignIn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Log.d("FarmGPT", "onFailure: " + t.getMessage());
                Functions.showToast(LoginActivity.this, "Something went wrong, please try again later");
            }
        });
    }

}