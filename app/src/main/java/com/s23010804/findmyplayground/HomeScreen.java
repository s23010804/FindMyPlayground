package com.s23010804.findmyplayground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    Button findPlaygroundBtn, myBookingsBtn, favoritesBtn, notificationsBtn, settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);  // Linked to your home screen layout

        // Initialize buttons
        findPlaygroundBtn = findViewById(R.id.findPlaygroundBtn);
        myBookingsBtn = findViewById(R.id.myBookingsBtn);
        favoritesBtn = findViewById(R.id.favoritesBtn);
        notificationsBtn = findViewById(R.id.notificationsBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        // Set onClickListeners
        findPlaygroundBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, FindPlaygroundActivity.class);
            startActivity(intent);
        });

        myBookingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, MyBookingsActivity.class);
            startActivity(intent);
        });

        favoritesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, FavoritesActivity.class);
            startActivity(intent);
        });

        notificationsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, NotificationsActivity.class);
            startActivity(intent);
        });

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
