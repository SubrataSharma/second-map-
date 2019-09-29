package com.example.maptesttwoapplication.Model_java_class;


public class MapLocation {

    private String id;
    private double latitude;
    private double longitude;
    private String company;
    private String registerName;
    private String contactNo;
    private String email;
    private String password;
    private String serviceType;




    public MapLocation() {
    }


    public MapLocation(String id,double latitude, double longitude, String company, String registerName,
                       String contactNo, String email, String serviceType) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
        this.registerName = registerName;
        this.contactNo = contactNo;
        this.email = email;
        this.serviceType = serviceType;
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

    public String getEmail() {
        return email;
    }


    public String getServiceType() {
        return serviceType;
    }



}
