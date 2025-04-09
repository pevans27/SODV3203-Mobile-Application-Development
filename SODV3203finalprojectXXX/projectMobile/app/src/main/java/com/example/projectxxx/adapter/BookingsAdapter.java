package com.example.projectxxx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectxxx.R;
import com.example.projectxxx.model.Booking;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder> {

    Context context;
    List<Booking> bookings;

    public BookingsAdapter(Context context, List<Booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookings_row_item, parent, false);
        return new BookingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        holder.placeNameTextView.setText(bookings.get(position).getPlaceName());
        holder.datesTextView.setText(bookings.get(position).getStartDate() + " - " + bookings.get(position).getEndDate());
        holder.guestsTextView.setText("Guests: " + bookings.get(position).getNumGuests());
        holder.priceTextView.setText("$" + bookings.get(position).getTotalPrice());
        holder.statusTextView.setText(bookings.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {

        TextView placeNameTextView, datesTextView, guestsTextView, priceTextView, statusTextView;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeNameTextView = itemView.findViewById(R.id.textViewPlaceName);
            datesTextView = itemView.findViewById(R.id.textViewDates);
            guestsTextView = itemView.findViewById(R.id.textViewGuests);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            statusTextView = itemView.findViewById(R.id.textViewStatus);
        }
    }
}