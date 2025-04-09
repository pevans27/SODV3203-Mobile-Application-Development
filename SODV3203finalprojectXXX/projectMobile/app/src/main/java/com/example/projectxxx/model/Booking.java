package com.example.projectxxx.model;

import java.util.Date;

public class Booking {
    private String bookingId;
    private String userId;
    private String placeName;
    private String startDate;
    private String endDate;
    private int numGuests;
    private double totalPrice;
    private String status; // "Confirmed", "Pending", "Cancelled"

    public Booking() {
        // Empty constructor needed for Firebase
    }

    public Booking(String bookingId, String userId, String placeName,
                   String startDate, String endDate, int numGuests,
                   double totalPrice, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.placeName = placeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numGuests = numGuests;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getNumGuests() { return numGuests; }
    public void setNumGuests(int numGuests) { this.numGuests = numGuests; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}