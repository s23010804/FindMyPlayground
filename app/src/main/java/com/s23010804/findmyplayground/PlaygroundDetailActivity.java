package com.s23010804.findmyplayground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PlaygroundDetailActivity extends AppCompatActivity {

    TextView playgroundName, playgroundLocation, playgroundDescription;
    ImageView playgroundImage;
    Button bookNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground_detail);

        playgroundName = findViewById(R.id.playgroundName);
        playgroundLocation = findViewById(R.id.playgroundLocation);
        playgroundDescription = findViewById(R.id.playgroundDescription);
        playgroundImage = findViewById(R.id.playgroundImage);
        bookNowBtn = findViewById(R.id.bookNowBtn);

        // Receive data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");

        playgroundName.setText(name);
        playgroundLocation.setText(location);
        playgroundDescription.setText("This is a safe and fun indoor playground located in " + location + ". Perfect for kids under 12!");

        bookNowBtn.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(PlaygroundDetailActivity.this, BookingActivity.class);
            bookingIntent.putExtra("playgroundName", name);
            startActivity(bookingIntent);
        });
    }
}
