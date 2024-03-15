package com.ug.air.farmgpt.Activities;

import static com.ug.air.farmgpt.Activities.SplashActivity.SHARED_PREFS;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ug.air.farmgpt.Adapters.SpinnerAdapter;
import com.ug.air.farmgpt.Models.Error;
import com.ug.air.farmgpt.Models.User;
import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Utils.Functions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView txtSignIn;
    EditText edFname, edLname, edContact, edDistrict;
    Spinner spinnerGender, spinnerAge;
    String fname, lname, contact, gender, age, district;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnSignUp = findViewById(R.id.btnsign);
        txtSignIn = findViewById(R.id.signup);
        edFname = findViewById(R.id.fname);
        edLname = findViewById(R.id.lname);
        edContact = findViewById(R.id.contact);
        edDistrict = findViewById(R.id.district);
        spinnerGender = findViewById(R.id.spinner_gender);
        spinnerAge = findViewById(R.id.spinner_age);
        progressBar = findViewById(R.id.progress_bar);

        txtSignIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        String[] items = {"Select one", "Male", "Female"};

        SpinnerAdapter genderAdapter = new SpinnerAdapter(this, R.layout.custom_spinner_item, items);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        String[] items_2 = {"Select one", "Below 20", "20 - 29", "30 -39", "40 - 49", "50 - 59", "60 and above"};

        SpinnerAdapter ageAdapter = new SpinnerAdapter(this, R.layout.custom_spinner_item, items_2);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(ageAdapter);

        btnSignUp.setOnClickListener(view -> {
            age = spinnerAge.getSelectedItem().toString();
            gender = spinnerGender.getSelectedItem().toString();
            fname = edFname.getText().toString().trim();
            lname = edLname.getText().toString().trim();
            contact = edContact.getText().toString().trim();
            district = edDistrict.getText().toString().trim();

            if (fname.isEmpty() || lname.isEmpty() || contact.isEmpty() || district.isEmpty() || age.equals("Select one") || gender.equals("Select one")){
                Functions.showToast(this, "Please fill in all fields");
            }
            else {
                register();
            }
        });

    }

    private void register() {
        btnSignUp.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        User user = new User(contact, lname, fname, district, age, gender);
        Call<String> call = Functions.jsonPlaceHolder("account").register(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnSignUp.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Functions.showToast(RegisterActivity.this, "Please Login to continue");
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
                            Functions.showToast(RegisterActivity.this, message);
                        }
                        else {
                            Functions.showToast(RegisterActivity.this, "Something went wrong, please try again later");
                        }
                    }
                    catch (IOException e) {
                        Functions.showToast(RegisterActivity.this, "Something went wrong, please try again later");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                btnSignUp.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Log.d("Adsurv Questions", "onFailure: " + t.getMessage());
                Functions.showToast(RegisterActivity.this, "Something went wrong, please try again later");
            }
        });
    }
}