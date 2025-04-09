package com.example.projectxxx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmationActivity extends AppCompatActivity {

    private TextView placeNameTextView, checkInTextView, checkOutTextView, guestsTextView;
    private ImageView placeImageView, backButton;
    private Button viewAllBookingsButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        // Initialize UI elements
        placeNameTextView = findViewById(R.id.textViewPlaceName);
        checkInTextView = findViewById(R.id.textViewCheckInDate);
        checkOutTextView = findViewById(R.id.textViewCheckOutDate);
        guestsTextView = findViewById(R.id.textViewGuests);
        placeImageView = findViewById(R.id.imageViewPlace);
        backButton = findViewById(R.id.imageViewBack);
        viewAllBookingsButton = findViewById(R.id.buttonViewAllBookings);
        homeButton = findViewById(R.id.buttonHome);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String placeName = intent.getStringExtra("PLACE_NAME");
            String checkIn = intent.getStringExtra("CHECK_IN");
            String checkOut = intent.getStringExtra("CHECK_OUT");
            String guests = intent.getStringExtra("GUESTS");
            int imageResourceId = intent.getIntExtra("IMAGE_RESOURCE", 0);

            // Set booking details
            placeNameTextView.setText(placeName);
            checkInTextView.setText("Check-in: " + checkIn);
            checkOutTextView.setText("Check-out: " + checkOut);
            guestsTextView.setText("Guests: " + guests);

            if (imageResourceId != 0) {
                placeImageView.setImageResource(imageResourceId);
            }
        }

        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // View all bookings button
        viewAllBookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingConfirmationActivity.this, BookingsActivity.class));
            }
        });

        // Home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingConfirmationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}