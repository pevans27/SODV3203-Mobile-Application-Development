package com.example.projectxxx.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectxxx.R;
import com.example.projectxxx.DetailsActivity;
import com.example.projectxxx.model.RecentsData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    Context context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Context context, List<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recent_row_item, parent, false);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {
        RecentsData currentItem = recentsDataList.get(position);

        // Set the data to the views
        holder.countryName.setText(currentItem.getCountryName());
        holder.placeName.setText(currentItem.getPlaceName());
        holder.price.setText(currentItem.getPrice());
        holder.placeImage.setImageResource(currentItem.getImageUrl());

        // Set the click listener to start DetailsActivity with the correct data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("PLACE_NAME", currentItem.getPlaceName());
                intent.putExtra("COUNTRY_NAME", currentItem.getCountryName());
                intent.putExtra("PRICE", currentItem.getPrice());
                intent.putExtra("IMAGE_RESOURCE", currentItem.getImageUrl());

                String place = currentItem.getPlaceName().toLowerCase().replaceAll("\\s+", "");

                // Create a map of place names to their respective image resources
                Map<String, int[]> placeImages = new HashMap<>();
                placeImages.put("banff", new int[]{R.drawable.banff1, R.drawable.banff2, R.drawable.banff3});
                placeImages.put("jasper", new int[]{R.drawable.jasper1, R.drawable.jasper2, R.drawable.jasper3});
                placeImages.put("canmore", new int[]{R.drawable.canmore1, R.drawable.canmore2, R.drawable.canmore3});
                placeImages.put("lakelouise", new int[]{R.drawable.lakelouise1, R.drawable.lakelouise2, R.drawable.lakelouise3});
                placeImages.put("lakemoraine", new int[]{R.drawable.lakemoraine1, R.drawable.lakemoraine2, R.drawable.lakemoraine3});
                placeImages.put("columbia", new int[]{R.drawable.columbia1, R.drawable.columbia2, R.drawable.columbia3});
                placeImages.put("whistler", new int[]{R.drawable.whistler1, R.drawable.whistler2, R.drawable.whistler3});
                placeImages.put("niagara", new int[]{R.drawable.niagra1, R.drawable.niagra2, R.drawable.niagra3});
                placeImages.put("lodge", new int[]{R.drawable.lodge1, R.drawable.lodge2, R.drawable.lodge3});

                int[] galleryImages = new int[3];
                boolean matched = false;

                // Loop through placeImages map to find the matching place
                for (Map.Entry<String, int[]> entry : placeImages.entrySet()) {
                    // Use contains to match any substring from the place name (case insensitive)
                    if (place.contains(entry.getKey().toLowerCase())) {
                        galleryImages = entry.getValue();
                        matched = true;
                        break;
                    }
                }

                // If no match found, use the default images
                if (!matched) {
                    galleryImages = new int[]{currentItem.getImageUrl(), currentItem.getImageUrl(), currentItem.getImageUrl()};
                }
                // Pass gallery images to DetailsActivity
                intent.putExtra("GALLERY_IMAGES", galleryImages);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<RecentsData> newList) {
        this.recentsDataList = newList;
        notifyDataSetChanged();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
