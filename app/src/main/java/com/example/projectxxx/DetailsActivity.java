package com.example.projectxxx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve the data passed from the adapter
        String placeName = getIntent().getStringExtra("PLACE_NAME");
        String countryName = getIntent().getStringExtra("COUNTRY_NAME");
        String price = getIntent().getStringExtra("PRICE");
        int imageResource = getIntent().getIntExtra("IMAGE_RESOURCE", 0);
        int[] galleryImages = getIntent().getIntArrayExtra("GALLERY_IMAGES");

        // Set the main image
        ImageView imageView = findViewById(R.id.imageView3);
        imageView.setImageResource(imageResource);

        // Set gallery images
        if (galleryImages != null && galleryImages.length == 3) {
            ImageView photo1 = findViewById(R.id.imageView6);
            ImageView photo2 = findViewById(R.id.imageView7);
            ImageView photo3 = findViewById(R.id.imageView8);

            // Set the gallery images
            photo1.setImageResource(galleryImages[0]);
            photo2.setImageResource(galleryImages[1]);
            photo3.setImageResource(galleryImages[2]);
        }

        // Set place details
        TextView placeNameTextView = findViewById(R.id.textView8);
        placeNameTextView.setText(placeName);

        TextView locationTextView = findViewById(R.id.textView9);
        locationTextView.setText(countryName);

        // Set the "Book Now" button functionality
        Button bookNowButton = findViewById(R.id.buttonBookNow);
        bookNowButton.setOnClickListener(v -> {
            // Create an Intent to navigate to the BookingActivity
            Intent bookingIntent = new Intent(DetailsActivity.this, BookingActivity.class);

            // Pass data to the BookingActivity
            bookingIntent.putExtra("PLACE_NAME", placeName);
            bookingIntent.putExtra("PRICE", price);
            bookingIntent.putExtra("IMAGE_RESOURCE", imageResource);

            // Start the BookingActivity
            startActivity(bookingIntent);
        });


        // Set back button functionality
        ImageView backButton = findViewById(R.id.imageView4);
        backButton.setOnClickListener(v -> finish());
    }
}
