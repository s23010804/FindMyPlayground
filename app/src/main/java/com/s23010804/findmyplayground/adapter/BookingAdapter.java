package com.s23010804.findmyplayground;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010804.findmyplayground.model.Booking; // ✅ Fix: import the Booking model

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private Context context;
    private List<Booking> bookings;

    public BookingAdapter(Context context, List<Booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bookingPlayground.setText("Playground: " + booking.getPgName());
        holder.bookingDate.setText("Date: " + booking.getDate());
        holder.bookingTime.setText("Time: " + booking.getTime());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingPlayground, bookingDate, bookingTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingPlayground = itemView.findViewById(R.id.bookingPlayground);
            bookingDate = itemView.findViewById(R.id.bookingDate);
            bookingTime = itemView.findViewById(R.id.bookingTime);
        }
    }
}
