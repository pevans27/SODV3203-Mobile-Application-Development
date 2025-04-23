package com.example.projectxxx.model;

public class TopPlacesData {
    private String placeName;
    private String countryName;
    private String price;
    private Integer imageUrl;
    private int[] galleryImages; // ✅ This field holds gallery images

    // ✅ Constructor including galleryImages
    public TopPlacesData(String placeName, String countryName, String price, Integer imageUrl, int[] galleryImages) {
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.galleryImages = galleryImages;
    }

    public int[] getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(int[] galleryImages) {
        this.galleryImages = galleryImages;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
