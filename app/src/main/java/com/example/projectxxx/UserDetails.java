package com.example.projectxxx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetails extends AppCompatActivity {

    private TextView usernameDisplay;
    private TextView emailDisplay;
    private Button bookingsButton;
    private Button signOutButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Initialize UI components
        usernameDisplay = findViewById(R.id.username_display);
        emailDisplay = findViewById(R.id.email_display);
        bookingsButton = findViewById(R.id.bookings_button);
        signOutButton = findViewById(R.id.sign_out_button);
        backButton = findViewById(R.id.imageViewBack);

        // Get user data from SharedPreferences
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = loginPrefs.getString("username", "User");
        String email = loginPrefs.getString("userEmail", "user@example.com");

        // Display username and email
        usernameDisplay.setText(username);
        emailDisplay.setText(email);

        // Set up button click listeners
        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookingsActivity();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to previous activity
            }
        });
    }

    private void openBookingsActivity() {
        // Start the BookingsActivity
        Intent intent = new Intent(UserDetails.this, BookingsActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        // Clear login session (but keep credentials saved)
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putBoolean("isLoggedIn", false);  // Mark as not logged in
        editor.apply();

        // Navigate back to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
