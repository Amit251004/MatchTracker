package com.example.matchtracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.matchtracker.db.VenueDbHelper;
import com.example.matchtracker.fragments.AllMatchesFragment;
import com.example.matchtracker.fragments.SavedMatchesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private VenueDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new VenueDbHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull android.view.View drawerView) {
                updateSavedBadge();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AllMatchesFragment())
                    .commit();
            navView.setCheckedItem(R.id.nav_all_matches);
            setTitle("All Venues");
        }
    }

    private void updateSavedBadge() {
        int count = dbHelper.getAllSavedVenues().size();
        MenuItem savedItem = navView.getMenu().findItem(R.id.nav_saved_matches);
        if (count > 0) {
            TextView badgeView = (TextView) savedItem.getActionView();
            if (badgeView == null) {
                badgeView = new TextView(this);
                badgeView.setBackgroundResource(android.R.drawable.ic_notification_overlay);
                badgeView.setTextColor(getResources().getColor(R.color.white));
                badgeView.setTextSize(11);
                badgeView.setPadding(16, 4, 16, 4);
                savedItem.setActionView(badgeView);
            }
            badgeView.setText(String.valueOf(count));
        } else {
            savedItem.setActionView(null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String title = "";

        int id = item.getItemId();
        if (id == R.id.nav_all_matches) {
            fragment = new AllMatchesFragment();
            title = "All Venues";
        } else if (id == R.id.nav_saved_matches) {
            fragment = new SavedMatchesFragment();
            title = "Saved Venues";
        }


        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            setTitle(title);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
