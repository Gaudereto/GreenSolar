package com.green.greensolar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    // Constantes:
    static final String GREEN_PREFS = "GreenPrefs";
    static final String LOGGED_FLAG = "userlogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(GREEN_PREFS, 0);
        boolean loggedUser = prefs.getBoolean(LOGGED_FLAG,false);
        if(loggedUser) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
