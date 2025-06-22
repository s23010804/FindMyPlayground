package com.s23010804.findmyplayground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch notificationSwitch, darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationSwitch = findViewById(R.id.notificationSwitch);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Notifications Enabled" : "Notifications Disabled", Toast.LENGTH_SHORT).show();
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Dark Mode On" : "Dark Mode Off", Toast.LENGTH_SHORT).show();
        });
    }
}
