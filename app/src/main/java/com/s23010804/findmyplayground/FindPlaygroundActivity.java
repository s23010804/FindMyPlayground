package com.s23010804.findmyplayground;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Playground;

import java.util.ArrayList;
import java.util.List;

public class FindPlaygroundActivity extends AppCompatActivity {

    RecyclerView playgroundRecyclerView;
    List<Playground> playgroundList;
    PlaygroundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_playground);

        playgroundRecyclerView = findViewById(R.id.playgroundRecyclerView);
        playgroundList = new ArrayList<>();

        // ✅ Updated with image names: pg, pg2, pg3
        playgroundList.add(new Playground("New Action Indoor", "MADAWALA- KANDY", "pg1"));
        playgroundList.add(new Playground("Horizon Indoor Sports", "MADAWALA-Kandy", "pg2"));
        playgroundList.add(new Playground("Athlo Indoor Sports", "MADAWALA- KANDY", "pg3"));

        adapter = new PlaygroundAdapter(this, playgroundList);
        playgroundRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playgroundRecyclerView.setAdapter(adapter);
    }
}
