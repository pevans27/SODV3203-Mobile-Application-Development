package com.example.projectxxx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectxxx.model.Booking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class BookingActivity extends AppCompatActivity {

    private TextView placeNameTextView, pricePerNightTextView;
    private EditText guestsEditText;
    private Button checkInButton, checkOutButton, bookNowButton;
    private ImageView backButton;
    private String placeName, priceString;
    private int imageResourceId;

    private Calendar checkInDate = Calendar.getInstance();
    private Calendar checkOutDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize UI elements
        placeNameTextView = findViewById(R.id.textViewPlaceName);
        pricePerNightTextView = findViewById(R.id.textViewPricePerNight);
        guestsEditText = findViewById(R.id.editTextGuests);
        checkInButton = findViewById(R.id.buttonCheckIn);
        checkOutButton = findViewById(R.id.buttonCheckOut);
        bookNowButton = findViewById(R.id.buttonBookNow);
        backButton = findViewById(R.id.imageViewBack);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            placeName = intent.getStringExtra("PLACE_NAME");
            priceString = intent.getStringExtra("PRICE");
            imageResourceId = intent.getIntExtra("IMAGE_RESOURCE", 0);

            // Set place details
            placeNameTextView.setText(placeName);
            pricePerNightTextView.setText(priceString);
        }

        // Initialize check-in date (today)
        checkInDate.set(Calendar.HOUR_OF_DAY, 0);
        checkInDate.set(Calendar.MINUTE, 0);
        checkInDate.set(Calendar.SECOND, 0);
        checkInDate.set(Calendar.MILLISECOND, 0);

        // Initialize check-out date (tomorrow)
        checkOutDate.setTimeInMillis(checkInDate.getTimeInMillis());
        checkOutDate.add(Calendar.DATE, 1);

        // Update buttons with initial dates
        updateDateButtons();

        // Check-in date picker
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(true);
            }
        });

        // Check-out date picker
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(false);
            }
        });

        // Book now button
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateBooking()) {
                    createBooking();
                    Toast.makeText(BookingActivity.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
                    // Navigate to booking confirmation or main activity
                    startActivity(new Intent(BookingActivity.this, BookingConfirmationActivity.class)
                            .putExtra("PLACE_NAME", placeName)
                            .putExtra("CHECK_IN", dateFormat.format(checkInDate.getTime()))
                            .putExtra("CHECK_OUT", dateFormat.format(checkOutDate.getTime()))
                            .putExtra("GUESTS", guestsEditText.getText().toString())
                            .putExtra("IMAGE_RESOURCE", imageResourceId));
                    finish();
                }
            }
        });

        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showDatePicker(final boolean isCheckIn) {
        Calendar calendar = isCheckIn ? checkInDate : checkOutDate;
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth, 0, 0, 0);
                        selectedDate.set(Calendar.MILLISECOND, 0);

                        if (isCheckIn) {
                            // Check-in date can't be in the past
                            Calendar today = Calendar.getInstance();
                            today.set(Calendar.HOUR_OF_DAY, 0);
                            today.set(Calendar.MINUTE, 0);
                            today.set(Calendar.SECOND, 0);
                            today.set(Calendar.MILLISECOND, 0);

                            if (selectedDate.before(today)) {
                                Toast.makeText(BookingActivity.this, "Check-in date cannot be in the past", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Check-in date can't be after check-out date
                            if (selectedDate.after(checkOutDate)) {
                                Toast.makeText(BookingActivity.this, "Check-in date must be before check-out date", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            checkInDate = selectedDate;
                        } else {
                            // Check-out date can't be before check-in date
                            if (selectedDate.before(checkInDate) || selectedDate.equals(checkInDate)) {
                                Toast.makeText(BookingActivity.this, "Check-out date must be after check-in date", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            checkOutDate = selectedDate;
                        }

                        updateDateButtons();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateButtons() {
        checkInButton.setText(dateFormat.format(checkInDate.getTime()));
        checkOutButton.setText(dateFormat.format(checkOutDate.getTime()));
    }

    private boolean validateBooking() {
        String guestsStr = guestsEditText.getText().toString().trim();
        if (guestsStr.isEmpty()) {
            guestsEditText.setError("Please enter number of guests");
            return false;
        }

        int guests;
        try {
            guests = Integer.parseInt(guestsStr);
        } catch (NumberFormatException e) {
            guestsEditText.setError("Please enter a valid number");
            return false;
        }

        if (guests <= 0) {
            guestsEditText.setError("Number of guests must be at least 1");
            return false;
        }

        if (guests > 10) {
            guestsEditText.setError("Maximum 10 guests allowed");
            return false;
        }

        return true;
    }

    private void createBooking() {
        // Get logged in user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");

        // Generate a random booking ID (in a real app, this would come from your backend)
        String bookingId = UUID.randomUUID().toString();

        // Create a booking object
        Booking booking = new Booking(
                bookingId,
                userEmail, // Using email as userId for simplicity
                placeName,
                dateFormat.format(checkInDate.getTime()),
                dateFormat.format(checkOutDate.getTime()),
                Integer.parseInt(guestsEditText.getText().toString()),
                calculateTotalPrice(),
                "Confirmed"
        );

        // Save the booking to SharedPreferences
        saveBookingToSharedPreferences(booking);
    }

    private void saveBookingToSharedPreferences(Booking booking) {
        SharedPreferences sharedPreferences = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // You need to store the bookings in a list. Here, we simulate adding the booking to a list.
        String bookingsJson = sharedPreferences.getString("bookings", "[]");

        // Convert the list of bookings to JSON
        List<Booking> bookingsList = new Gson().fromJson(bookingsJson, new TypeToken<List<Booking>>(){}.getType());
        bookingsList.add(booking);

        // Convert the updated list back to JSON and save it
        String updatedBookingsJson = new Gson().toJson(bookingsList);
        editor.putString("bookings", updatedBookingsJson);
        editor.apply();
    }

    private double calculateTotalPrice() {
        // Extract numeric price from priceString (e.g., "From $200" -> 200)
        String priceStr = priceString.replaceAll("[^0-9]", "");
        double pricePerNight = Double.parseDouble(priceStr);

        // Calculate number of nights
        long diffInMillis = checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis();
        int numNights = (int) (diffInMillis / (1000 * 60 * 60 * 24));

        return pricePerNight * numNights;
    }
}
