package com.example.projectxxx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectxxx.adapter.BookingsAdapter;
import com.example.projectxxx.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingsActivity extends AppCompatActivity {

    private RecyclerView bookingsRecycler;
    private BookingsAdapter bookingsAdapter;
    private ImageView backButton;
    private TextView noBookingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        // Initialize UI elements
        bookingsRecycler = findViewById(R.id.bookings_recycler);
        backButton = findViewById(R.id.imageViewBack);
        noBookingsTextView = findViewById(R.id.textViewNoBookings);

        // Get logged in user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");

        // Set up RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bookingsRecycler.setLayoutManager(layoutManager);

        // Get bookings for user (in a real app, this would come from a database)
        List<Booking> bookings = getDummyBookings(userEmail);

        if (bookings.isEmpty()) {
            noBookingsTextView.setVisibility(View.VISIBLE);
            bookingsRecycler.setVisibility(View.GONE);
        } else {
            noBookingsTextView.setVisibility(View.GONE);
            bookingsRecycler.setVisibility(View.VISIBLE);
            bookingsAdapter = new BookingsAdapter(this, bookings);
            bookingsRecycler.setAdapter(bookingsAdapter);
        }

        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private List<Booking> getDummyBookings(String userEmail) {
        // In a real app, you would fetch this data from your database
        List<Booking> bookings = new ArrayList<>();

        // Add a dummy booking (in a real app, this would be real data)
        bookings.add(new Booking(
                "booking123",
                userEmail,
                "Banff",
                "Apr 15, 2025",
                "Apr 20, 2025",
                2,
                1000.0,
                "Confirmed"
        ));

        return bookings;
    }
}