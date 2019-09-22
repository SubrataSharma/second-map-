package com.example.maptesttwoapplication.Model_java_class;

public class MapLocation {

    private double latitude;
    private double longitude;

    public MapLocation() {
    }

    public MapLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
