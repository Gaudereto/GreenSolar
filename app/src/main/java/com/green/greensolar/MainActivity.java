package com.green.greensolar;

import android.content.Intent;
import android.content.res.Configuration;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.green.greensolar.Controllers.MainViewDataController;
import com.green.greensolar.Controllers.SharedPreferencesController;
import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.Data.CityData;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // UI interface refs:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView, mClientFareTextView;
    private RadioGroup mRadioGroup;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    // Main Activity data and logic controller:
    private MainViewDataController mMainViewDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewDataController = new MainViewDataController();

        // Activity views initialization:
        mCityTextView = findViewById(R.id.city_edit_text);
        mConsumeTextView = findViewById(R.id.consumo_edit_text);
        mClientFareTextView = findViewById(R.id.fare_edit_text);
        mRadioGroup = findViewById(R.id.radio_group);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        // Navigation Drawer config:
        setupNavigationView();

        // City name auto complete adapter config:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                mMainViewDataController.getListOfCityNames());
        mCityTextView.setAdapter(adapter);
        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityTextView.showDropDown();
            }
        });

        // Estimate pv system listener button:
        findViewById(R.id.estimate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePvSystem();
            }
        });
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

    public void calculatePvSystem() {
        CityData chosenCity = mMainViewDataController
                .getCityDataByName(mCityTextView.getText().toString());
        if(chosenCity == null){
            showErrorDialog(getString(R.string.error_city_not_found));
            return;
        }

        String energyConsumption = mConsumeTextView.getText().toString();
        if(!mMainViewDataController.validateEnergyConsumption(energyConsumption)){
            showErrorDialog(getString(R.string.error_invalid_energy_consumption));
            return;
        }

        double fare = mMainViewDataController
                .validateFare(mClientFareTextView.getText().toString());

        int phases = mMainViewDataController
                .getNumberOfPhases(mRadioGroup.getCheckedRadioButtonId());
        if(phases == -1) {
            showErrorDialog(getString(R.string.error_envalid_energy_phases));
            return;
        }

        SolarSystem solarSystem = new SolarSystem(
                chosenCity,
                Double.parseDouble(energyConsumption),
                fare,
                phases);

        // Start Solar system result activity:
        Intent newIntent = new Intent(MainActivity.this, ResultsActivity.class);
        newIntent.putExtra("SolarSystem", solarSystem);
        startActivity(newIntent);
    }

    // Logout from application:
    public void logOut(){
        new SharedPreferencesController(MainActivity.this).setLogged(false);

        // Finish and return to login activity:
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}
