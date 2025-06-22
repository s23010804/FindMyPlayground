package com.s23010804.findmyplayground;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.s23010804.findmyplayground.model.Booking;
import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    RecyclerView bookingsRecyclerView;
    List<Booking> bookingList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        bookingList = new ArrayList<>();

        loadBookings();
    }

    private void loadBookings() {
        Cursor cursor = db.getAllBookings();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("pg_name"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            bookingList.add(new Booking(name, date, time));
        }

        BookingAdapter adapter = new BookingAdapter(this, bookingList);
        bookingsRecyclerView.setAdapter(adapter);
    }
}
