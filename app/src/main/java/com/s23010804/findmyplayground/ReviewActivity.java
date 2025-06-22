package com.s23010804.findmyplayground;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    EditText commentInput;
    RatingBar ratingBar;
    Button submitBtn;
    RecyclerView reviewRecycler;
    ArrayList<Review> reviewList;
    ReviewAdapter adapter;
    DatabaseHelper db;
    String playgroundName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        commentInput = findViewById(R.id.commentInput);
        ratingBar = findViewById(R.id.ratingBar);
        submitBtn = findViewById(R.id.submitBtn);
        reviewRecycler = findViewById(R.id.reviewRecycler);

        db = new DatabaseHelper(this);
        reviewList = new ArrayList<>();

        playgroundName = getIntent().getStringExtra("playgroundName");

        loadReviews();

        submitBtn.setOnClickListener(v -> {
            int rating = (int) ratingBar.getRating();
            String comment = commentInput.getText().toString();

            if (rating == 0 || comment.isEmpty()) {
                Toast.makeText(this, "Please add both rating and comment", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = db.insertReview(playgroundName, rating, comment);
                if (inserted) {
                    Toast.makeText(this, "Review added!", Toast.LENGTH_SHORT).show();
                    commentInput.setText("");
                    ratingBar.setRating(0);
                    loadReviews();
                } else {
                    Toast.makeText(this, "Failed to add review", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadReviews() {
        reviewList.clear();
        Cursor cursor = db.getReviews(playgroundName);
        while (cursor.moveToNext()) {
            int rating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
            reviewList.add(new Review(rating, comment));
        }
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter(this, reviewList);
        reviewRecycler.setAdapter(adapter);
    }
}
