package com.example.maptesttwoapplication.Model_java_class;

public class MapLocation {

    private double latitude;
    private double longitude;
    private String company_name;



    public MapLocation() {
    }

    public MapLocation(double latitude, double longitude, String company_name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.company_name = company_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCompany_name() {
        return company_name;
    }
}
