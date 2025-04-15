package com.example.projectxxx;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BookingsActivity extends AppCompatActivity {

    private RecyclerView bookingsRecycler;
    private TextView noBookingsTextView;
    private BookingsAdapter bookingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        bookingsRecycler = findViewById(R.id.bookings_recycler);
        noBookingsTextView = findViewById(R.id.textViewNoBookings);

        ImageView backIcon = findViewById(R.id.imageViewBack);
        backIcon.setOnClickListener(v -> onBackPressed());

        List<Booking> bookings = loadBookingsFromSharedPreferences();

        if (bookings == null || bookings.isEmpty()) {
            noBookingsTextView.setVisibility(View.VISIBLE);
            bookingsRecycler.setVisibility(View.GONE);
        } else {
            noBookingsTextView.setVisibility(View.GONE);
            bookingsRecycler.setVisibility(View.VISIBLE);
            bookingsAdapter = new BookingsAdapter(this, bookings);
            bookingsRecycler.setAdapter(bookingsAdapter);
            bookingsRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private List<Booking> loadBookingsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        String bookingsJson = sharedPreferences.getString("bookings", "[]");

        // Convert the JSON string to a list of Booking objects
        try {
            return new Gson().fromJson(bookingsJson, new TypeToken<List<Booking>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
