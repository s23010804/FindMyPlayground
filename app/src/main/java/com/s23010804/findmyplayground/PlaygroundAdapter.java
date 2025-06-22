package com.s23010804.findmyplayground;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Playground;

import java.util.List;

public class PlaygroundAdapter extends RecyclerView.Adapter<PlaygroundAdapter.ViewHolder> {

    Context context;
    List<Playground> playgroundList;

    public PlaygroundAdapter(Context context, List<Playground> playgroundList) {
        this.context = context;
        this.playgroundList = playgroundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playground, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playground pg = playgroundList.get(position);
        holder.pgName.setText(pg.getName());
        holder.pgLocation.setText(pg.getLocation());

        // Set dynamic image
        int imageResId = context.getResources().getIdentifier(pg.getImageName(), "drawable", context.getPackageName());
        holder.playgroundImage.setImageResource(imageResId);

        // Book button click
        holder.bookBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaygroundDetailActivity.class);
            intent.putExtra("name", pg.getName());
            intent.putExtra("location", pg.getLocation());
            context.startActivity(intent);
        });

        // Favorite button click
        holder.favoriteBtn.setOnClickListener(v -> {
            boolean saved = new DatabaseHelper(context).addToFavorites(pg.getName(), pg.getLocation());
            if (saved) {
                Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Already in favorites or failed", Toast.LENGTH_SHORT).show();
            }
        });

        // View Reviews button click
        holder.viewReviewsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewActivity.class);
            intent.putExtra("playgroundName", pg.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return playgroundList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pgName, pgLocation;
        Button bookBtn, favoriteBtn, viewReviewsBtn;
        ImageView playgroundImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pgName = itemView.findViewById(R.id.pgName);
            pgLocation = itemView.findViewById(R.id.pgLocation);
            bookBtn = itemView.findViewById(R.id.bookBtn);
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn);
            viewReviewsBtn = itemView.findViewById(R.id.viewReviewsBtn); // ✅ newly added
            playgroundImage = itemView.findViewById(R.id.playgroundImage);
        }
    }
}
