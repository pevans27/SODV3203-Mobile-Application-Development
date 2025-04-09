package com.example.projectxxx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
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
    SearchView searchView;

    // Keep original lists to restore when search is cleared
    List<RecentsData> originalRecentsDataList;
    List<TopPlacesData> originalTopPlacesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Initialize SearchView
        searchView = findViewById(R.id.searchView);

        // List for Recents Data
        originalRecentsDataList = new ArrayList<>();
        originalRecentsDataList.add(new RecentsData("Banff", "Alberta, Canada", "From $200", R.drawable.banff));
        originalRecentsDataList.add(new RecentsData("Jasper National Park", "Alberta, Canada", "From $250", R.drawable.jasper2));
        originalRecentsDataList.add(new RecentsData("Lake Louise", "Alberta Canada", "From $300", R.drawable.lakelouise));
        originalRecentsDataList.add(new RecentsData("Canmore Downtowm", "Canmore, Alberta", "From $100", R.drawable.canmore2));

        setRecentRecycler(new ArrayList<>(originalRecentsDataList));

        // List for Top Places Data
        originalTopPlacesDataList = new ArrayList<>();
        originalTopPlacesDataList.add(new TopPlacesData("Banff", "Alberta, Canada", "From $200", R.drawable.canmore));
        originalTopPlacesDataList.add(new TopPlacesData("Jasper National Park", "Alberta, Canada", "From $250", R.drawable.jasper2));
        originalTopPlacesDataList.add(new TopPlacesData("Lake Louise", "Alberta Canada", "From $300", R.drawable.lakelouise));
        originalTopPlacesDataList.add(new TopPlacesData("Canmore Downtowm", "Canmore, Alberta", "From $100", R.drawable.canmore2));

        setTopPlacesRecycler(new ArrayList<>(originalTopPlacesDataList));

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

    // Sign out method
    private void setupSignOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Clear all shared preferences data
        editor.apply();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
