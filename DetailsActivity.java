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

        ImageView imageView = findViewById(R.id.imageView3);
        imageView.setImageResource(imageResource);

        if (galleryImages != null && galleryImages.length == 3) {
            ImageView photo1 = findViewById(R.id.imageView6);
            ImageView photo2 = findViewById(R.id.imageView7);
            ImageView photo3 = findViewById(R.id.imageView8);

            photo1.setImageResource(galleryImages[0]);
            photo2.setImageResource(galleryImages[1]);
            photo3.setImageResource(galleryImages[2]);
        }

        TextView placeNameTextView = findViewById(R.id.textView8);
        placeNameTextView.setText(placeName);

        TextView locationTextView = findViewById(R.id.textView9);
        locationTextView.setText(countryName);

        Button bookNowButton = findViewById(R.id.buttonBookNow);
        bookNowButton.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(DetailsActivity.this, BookingActivity.class);
            bookingIntent.putExtra("PLACE_NAME", placeName);
            bookingIntent.putExtra("PRICE", price);
            bookingIntent.putExtra("IMAGE_RESOURCE", imageResource);
            startActivity(bookingIntent);
        });

        ImageView backButton = findViewById(R.id.imageView4);
        backButton.setOnClickListener(v -> finish());

        TextView aboutTextView = findViewById(R.id.textView12);
        String description = "";

        if (placeName != null) {
            switch (placeName.toLowerCase()) {
                case "banff":
                case "banff national park":
                    description = getString(R.string.description_banff);
                    break;
                case "jasper national park":
                    description = getString(R.string.description_jasper);
                    break;
                case "lake louise":
                    description = getString(R.string.description_lake_louise);
                    break;
                case "canmore downtown":
                    description = getString(R.string.description_canmore);
                    break;
                case "lake moraine":
                    description = getString(R.string.description_lake_moraine);
                    break;
                case "columbia icefield":
                    description = getString(R.string.description_columbia_icefield);
                    break;
                case "whistler":
                    description = getString(R.string.description_whistler);
                    break;
                case "niagara falls":
                    description = getString(R.string.description_niagara_falls);
                    break;
                case "lake louise lodge":
                    description = getString(R.string.description_lake_louise_lodge);
                    break;
                default:
                    description = "No description available.";
                    break;
            }
        }

        aboutTextView.setText(description);

    }
}
