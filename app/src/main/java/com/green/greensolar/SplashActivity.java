package com.green.greensolar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.green.greensolar.Controllers.SharedPreferencesController;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);
        if(sharedPreferencesController.isLogged()) {
            finish();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            finish();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }
}
