package com.green.greensolar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.green.greensolar.Controllers.SharedPreferencesController;

import java.util.List;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    // UI interface refs:
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Activity views initialization:
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        // Navigation Drawer config:
        setupNavigationView();
    }

    void setupNavigationView() {
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        String userName = new SharedPreferencesController(MainActivity.this).getUserName();
        if(!userName.isEmpty()) {
            TextView navigationHeader = mNavigationView
                    .getHeaderView(R.layout.app_header_main)
                    .findViewById(R.id.nav_header_textView);
            navigationHeader.setText(userName);
        }
    }

    // Logout from application:
    public void logOut(){
        new SharedPreferencesController(MainActivity.this).setLogged(false);

        // Finish and return to login activity:
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }


    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen((GravityCompat.START))){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.nav_item_one) {

        } else if (id == R.id.nav_item_two) {

        } else if (id == R.id.nav_item_three) {

        } else if (id == R.id.nav_item_four) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Show error on screen with an alert dialog:
    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
