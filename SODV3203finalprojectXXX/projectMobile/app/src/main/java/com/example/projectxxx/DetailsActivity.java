package com.example.projectxxx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve data passed from MainActivity or other activities
        String placeName = getIntent().getStringExtra("PLACE_NAME");
        String countryName = getIntent().getStringExtra("COUNTRY_NAME");
        String price = getIntent().getStringExtra("PRICE");
        int imageResource = getIntent().getIntExtra("IMAGE_RESOURCE", 0);



        // Set up the Book Now button
        Button bookNowButton = findViewById(R.id.buttonBookNow);
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the button is clicked, navigate to BookingActivity
                Intent intent = new Intent(DetailsActivity.this, BookingActivity.class);
                intent.putExtra("PLACE_NAME", placeName);
                intent.putExtra("PRICE", price);
                intent.putExtra("IMAGE_RESOURCE", imageResource);
                startActivity(intent);
            }
        });

        // Set up the back button functionality
        ImageView backButton = findViewById(R.id.imageView4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the MainActivity
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // Close DetailsActivity
            }
        });
    }
}
