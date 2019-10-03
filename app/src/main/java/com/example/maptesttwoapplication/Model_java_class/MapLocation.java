package com.example.maptesttwoapplication.Model_java_class;


import java.util.List;

public class MapLocation {

    private String id;
    private double latitude;
    private double longitude;
    private String company;
    private String registerName;
    private String contactNo;
    private int pin;

    private List<String> services;



    public MapLocation() {
    }


    public MapLocation(String id,double latitude, double longitude, String company, String registerName,
                       String contactNo,int pin, List<String> services) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
        this.registerName = registerName;
        this.contactNo = contactNo;
        this.pin=pin;
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCompany() {
        return company;
    }

    public String getRegisterName() {
        return registerName;
    }

    public String getContactNo() {
        return contactNo;
    }


    public int getPin() {
        return pin;
    }

    public List<String> getServices() {
        return services;
    }
}
