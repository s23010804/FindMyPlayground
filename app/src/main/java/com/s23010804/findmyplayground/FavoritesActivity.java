package com.s23010804.findmyplayground;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Playground;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView favoritesRecyclerView;
    ArrayList<Playground> favoriteList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        favoriteList = new ArrayList<>();

        loadFavorites();
    }

    private void loadFavorites() {
        Cursor cursor = db.getAllFavorites();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No favorites found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("pg_name"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

            // If imageName is not stored in DB, use a default image name
            String imageName = "sample_playground"; // default or hardcoded value

            favoriteList.add(new Playground(name, location, imageName));
        }

        PlaygroundAdapter adapter = new PlaygroundAdapter(this, favoriteList);
        favoritesRecyclerView.setAdapter(adapter);
    }
}
