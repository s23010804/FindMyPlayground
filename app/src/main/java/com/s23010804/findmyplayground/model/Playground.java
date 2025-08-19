package com.s23010804.findmyplayground.models;

import android.location.Location;

public class Playground {
    private String name;
    private String location;
    private String imageName;   // For images stored as file names
    private int imageResId;     // For Android drawable resources
    private double latitude;    // For Maps
    private double longitude;   // For Maps

    // Constructor for RecyclerView list (with image resource)
    public Playground(String name, String location, int imageResId) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
    }

    // Constructor for RecyclerView list (with image name as string)
    public Playground(String name, String location, String imageName) {
        this.name = name;
        this.location = location;
        this.imageName = imageName;
    }

    // Constructor for Map markers
    public Playground(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getImageName() { return imageName; }
    public int getImageResId() { return imageResId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // ✅ Distance calculation (in meters)
    public float distanceTo(double userLat, double userLng) {
        float[] results = new float[1];
        Location.distanceBetween(userLat, userLng, latitude, longitude, results);
        return results[0]; // distance in meters
    }
}
