package com.s23010804.findmyplayground;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    EditText dateInput, timeInput;
    TextView playgroundNameText;
    Button confirmBookingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        playgroundNameText = findViewById(R.id.playgroundNameText);
        confirmBookingBtn = findViewById(R.id.confirmBookingBtn);

        // Receive data from detail screen
        String playgroundName = getIntent().getStringExtra("playgroundName");
        playgroundNameText.setText("Playground: " + playgroundName);

        confirmBookingBtn.setOnClickListener(v -> {
            String date = dateInput.getText().toString().trim();
            String time = timeInput.getText().toString().trim();

            if (date.isEmpty() || time.isEmpty()) {
                Toast.makeText(BookingActivity.this, "Please enter both date and time", Toast.LENGTH_SHORT).show();
            } else {
                boolean saved = new DatabaseHelper(this).insertBooking(playgroundName, date, time);
                if (saved) {
                    Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
