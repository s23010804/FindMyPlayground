package com.s23010804.findmyplayground;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView notificationsRecyclerView;
    List<Notification> notificationList;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        loadSampleNotifications();

        adapter = new NotificationAdapter(this, notificationList);
        notificationsRecyclerView.setAdapter(adapter);

        enableSwipeToDelete();
    }

    private void loadSampleNotifications() {
        notificationList.add(new Notification("Booking Confirmed", "Your booking at FunZone has been confirmed."));
        notificationList.add(new Notification("Reminder", "You have a booking tomorrow at Tiny Tots Park."));
        notificationList.add(new Notification("New Playground!", "Happy Kids Arena is now open in Kandy!"));
    }

    private void enableSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // We don't want drag & drop
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Notification removed = notificationList.get(position);
                notificationList.remove(position);
                adapter.notifyItemRemoved(position);

                Toast.makeText(NotificationsActivity.this, "Notification removed", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(notificationsRecyclerView);
    }
}
