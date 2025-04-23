package com.example.projectxxx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectxxx.adapter.RecentsAdapter;
import com.example.projectxxx.adapter.TopPlacesAdapter;
import com.example.projectxxx.model.RecentsData;
import com.example.projectxxx.model.TopPlacesData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;

    ImageView calendarImage;
    ImageView mapImage;
    ImageView profileImage; // Added profile image
    SearchView searchView;

    // Category text views
    TextView topTravelText, popularDestText, favoritesText;

    // Keep original lists to restore when search is cleared
    List<RecentsData> originalRecentsDataList;
    List<TopPlacesData> originalTopPlacesDataList;

    // Data for category tabs
    List<RecentsData> topTravelPicksList;
    List<RecentsData> popularDestinationsList;
    List<RecentsData> favoritesList;

    // Current category displayed in top_places_recycler
    private int currentCategory = 0; // 0 = Top Travel Picks, 1 = Popular Destinations, 2 = Favorites

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View root = findViewById(android.R.id.content);
        Log.d("MainActivity", "Root layout: " + root);


        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize the calendar ImageView
        calendarImage = findViewById(R.id.calendar_image);

        // Initialize map image view
        mapImage = findViewById(R.id.map_image);

        // Initialize profile image view
        profileImage = findViewById(R.id.profile_image);

        // Initialize SearchView
        searchView = findViewById(R.id.searchView);

        // Initialize category TextViews
        topTravelText = findViewById(R.id.textView7);
        popularDestText = findViewById(R.id.textView3);
        favoritesText = findViewById(R.id.textView4);

        // Set click listeners for category switching
        topTravelText.setOnClickListener(v -> switchCategory(0));
        popularDestText.setOnClickListener(v -> switchCategory(1));
        favoritesText.setOnClickListener(v -> switchCategory(2));

        // List for Recents Data
        originalRecentsDataList = new ArrayList<>();
        originalRecentsDataList.add(new RecentsData("Banff", "Alberta, Canada", "From $200", R.drawable.banff));
        originalRecentsDataList.add(new RecentsData("Jasper National Park", "Alberta, Canada", "From $250", R.drawable.jasper2));
        originalRecentsDataList.add(new RecentsData("Lake Louise", "Alberta Canada", "From $300", R.drawable.lakelouise));
        originalRecentsDataList.add(new RecentsData("Canmore Downtown", "Canmore, Alberta", "From $100", R.drawable.canmore2));

        setRecentRecycler(new ArrayList<>(originalRecentsDataList));

        // List for Top Travel Picks Data
        topTravelPicksList = new ArrayList<>();
        topTravelPicksList.add(new RecentsData("Lake Moraine", "Alberta, Canada", "From $220", R.drawable.lakemoraine));
        topTravelPicksList.add(new RecentsData("Columbia Icefield", "Alberta, Canada", "From $180", R.drawable.columbia));

        // List for Popular Destinations Data
        popularDestinationsList = new ArrayList<>();
        popularDestinationsList.add(new RecentsData("Whistler", "British Columbia, Canada", "From $300", R.drawable.whistler));
        popularDestinationsList.add(new RecentsData("Niagara Falls", "Ontario, Canada", "From $190", R.drawable.niagra));

        // List for Favorites Data
        favoritesList = new ArrayList<>();
        favoritesList.add(new RecentsData("Banff National Park", "Alberta, Canada", "From $230", R.drawable.banff));
        favoritesList.add(new RecentsData("Lake Louise Lodge", "Alberta, Canada", "From $320", R.drawable.lodge));

        // Initialize top places recycler with Top Travel Picks data
        originalTopPlacesDataList = convertToTopPlacesData(topTravelPicksList);
        setTopPlacesRecycler(new ArrayList<>(originalTopPlacesDataList));

        // Highlight the selected category
        updateCategoryHighlight();

        // Set up the search functionality
        setupSearchFunctionality();

        // Set OnClickListener for the calendar image
        calendarImage.setOnClickListener(v -> {
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Create and show the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, year1, monthOfYear, dayOfMonth1) -> {
                        // Do something with the selected date, like display it in a Toast
                        Toast.makeText(MainActivity.this, "Selected Date: " + dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1, Toast.LENGTH_SHORT).show();
                    },
                    year, month, dayOfMonth
            );

            // Show the DatePickerDialog
            datePickerDialog.show();
        });

        // Set OnClickListener for the map image
        mapImage.setOnClickListener(v -> {
            // Show the Map Dialog (Pop-up)
            showMapDialog();
        });

        // Set OnClickListener for the profile image
        profileImage.setOnClickListener(v -> {
            // Navigate to UserDetails activity
            Intent intent = new Intent(MainActivity.this, UserDetails.class);

            // Get the username from SharedPreferences if available
            String username = sharedPreferences.getString("username", "");
            if (!username.isEmpty()) {
                intent.putExtra("USERNAME", username);
            }

            startActivity(intent);
        });
    }

    private void switchCategory(int categoryIndex) {
        if (currentCategory == categoryIndex) {
            return; // Already on this category
        }

        currentCategory = categoryIndex;
        updateCategoryHighlight();

        // Update the data in top_places_recycler based on selected category
        switch (categoryIndex) {
            case 0: // Top Travel Picks
                originalTopPlacesDataList = convertToTopPlacesData(topTravelPicksList);
                break;
            case 1: // Popular Destinations
                originalTopPlacesDataList = convertToTopPlacesData(popularDestinationsList);
                break;
            case 2: // Favorites
                originalTopPlacesDataList = convertToTopPlacesData(favoritesList);
                break;
        }

        setTopPlacesRecycler(new ArrayList<>(originalTopPlacesDataList));
    }

    private void updateCategoryHighlight() {
        // Reset all text styles
        topTravelText.setAlpha(0.6f);
        popularDestText.setAlpha(0.6f);
        favoritesText.setAlpha(0.6f);

        // Highlight the selected category
        switch (currentCategory) {
            case 0:
                topTravelText.setAlpha(1.0f);
                break;
            case 1:
                popularDestText.setAlpha(1.0f);
                break;
            case 2:
                favoritesText.setAlpha(1.0f);
                break;
        }
    }

    private List<TopPlacesData> convertToTopPlacesData(List<RecentsData> recentsDataList) {
        List<TopPlacesData> topPlacesDataList = new ArrayList<>();

        for (RecentsData data : recentsDataList) {
            // Normalize place name for key lookup
            String placeKey = data.getPlaceName().toLowerCase().replaceAll("\\s+", "");

            // Map of gallery images
            int[] galleryImages;
            switch (placeKey) {
                case "banff":
                    galleryImages = new int[]{R.drawable.banff1, R.drawable.banff2, R.drawable.banff3};
                    break;
                case "jasper":
                    galleryImages = new int[]{R.drawable.jasper1, R.drawable.jasper2, R.drawable.jasper3};
                    break;
                case "lakelouise":
                    galleryImages = new int[]{R.drawable.lakelouise1, R.drawable.lakelouise2, R.drawable.lakelouise3};
                    break;
                case "canmore":
                    galleryImages = new int[]{R.drawable.canmore1, R.drawable.canmore2, R.drawable.canmore3};
                    break;
                case "lakemoraine":
                    galleryImages = new int[]{R.drawable.lakemoraine1, R.drawable.lakemoraine2, R.drawable.lakemoraine3};
                    break;
                case "columbia":
                    galleryImages = new int[]{R.drawable.columbia1, R.drawable.columbia2, R.drawable.columbia3};
                    break;
                case "whistler":
                    galleryImages = new int[]{R.drawable.whistler1, R.drawable.whistler2, R.drawable.whistler3};
                    break;
                case "niagra":
                    galleryImages = new int[]{R.drawable.niagra1, R.drawable.niagra2, R.drawable.niagra3};
                    break;
                case "lodge":
                    galleryImages = new int[]{R.drawable.lodge1, R.drawable.lodge2, R.drawable.lodge3};
                    break;
                default:
                    galleryImages = new int[]{R.drawable.default_image, R.drawable.default_image, R.drawable.default_image};
                    break;
            }

            topPlacesDataList.add(new TopPlacesData(
                    data.getPlaceName(),
                    data.getCountryName(),
                    data.getPrice(),
                    data.getImageUrl(),
                    galleryImages
            ));
        }

        return topPlacesDataList;
    }


    private void setupSearchFunctionality() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void filterData(String query) {
        query = query.toLowerCase().trim();

        if (query.isEmpty()) {
            // If search is empty, restore original lists
            recentsAdapter.updateList(new ArrayList<>(originalRecentsDataList));
            topPlacesAdapter.updateList(new ArrayList<>(originalTopPlacesDataList));
            return;
        }

        // Filter recents data
        List<RecentsData> filteredRecentsDataList = new ArrayList<>();
        for (RecentsData data : originalRecentsDataList) {
            if (data.getPlaceName().toLowerCase().contains(query) ||
                    data.getCountryName().toLowerCase().contains(query)) {
                filteredRecentsDataList.add(data);
            }
        }
        recentsAdapter.updateList(filteredRecentsDataList);

        // Filter top places data
        List<TopPlacesData> filteredTopPlacesDataList = new ArrayList<>();
        for (TopPlacesData data : originalTopPlacesDataList) {
            if (data.getPlaceName().toLowerCase().contains(query) ||
                    data.getCountryName().toLowerCase().contains(query)) {
                filteredTopPlacesDataList.add(data);
            }
        }
        topPlacesAdapter.updateList(filteredTopPlacesDataList);
    }

    private void showMapDialog() {
        // Create and show the MapDialogFragment
        MapDialogFragment mapDialogFragment = new MapDialogFragment();
        mapDialogFragment.show(getSupportFragmentManager(), "mapDialog");
    }

    private void setRecentRecycler(List<RecentsData> recentsDataList) {
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);
    }

    private void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList) {
        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);
    }

}