package com.s23010804.findmyplayground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    private Button btnFind, btnMap, btnBookings, btnFavorites, btnLogout, btnNotifications, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove toolbar/action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_home_screen);

        // Initialize buttons
        btnFind = findViewById(R.id.btnFind);
        btnMap = findViewById(R.id.btnMap);
        btnBookings = findViewById(R.id.btnBookings);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnLogout = findViewById(R.id.btnLogout);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnSettings = findViewById(R.id.btnsettings);

        // Set button actions
        btnFind.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, FindPlaygroundActivity.class))
        );

        btnMap.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, MapActivity.class))
        );

        btnBookings.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, MyBookingsActivity.class))
        );

        btnFavorites.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, FavoritesActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(HomeScreen.this, LoginScreen.class));
            finish(); // End HomeScreen so user can't go back without logging in
        });

        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, NotificationsActivity.class))
        );

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(HomeScreen.this, SettingsActivity.class))
        );
    }
}
