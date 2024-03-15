package com.ug.air.farmgpt.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ug.air.farmgpt.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARED_PREFS = "ae43jGUT57DD67";
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_splash);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        startSplashTimeout(2000);
    }

    protected void onResume() {
        super.onResume();
        startSplashTimeout(2000);
    }

    private void startSplashTimeout(int i) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check_credentials();
            }
        }, i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void check_credentials() {
        String token = sharedPreferences.getString(USERNAME, "");
        if (token.isEmpty()){
            Toast.makeText(this, "Please first login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        else{
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
    }
}