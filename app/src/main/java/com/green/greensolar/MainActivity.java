package com.green.greensolar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.green.greensolar.Controllers.SharedPreferencesController;
import com.green.greensolar.Presentation.SystemInput.SystemInputFragment;

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

        // Start fragment view:
        startFragment(savedInstanceState);
    }

    void startFragment(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            SystemInputFragment sysInFragment = new SystemInputFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            // firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, sysInFragment).commit();
        }
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
}
