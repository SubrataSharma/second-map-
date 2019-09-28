package com.example.maptesttwoapplication.Model_java_class;

import com.google.firebase.firestore.Exclude;

public class MapLocation {

    private double latitude;
    private double longitude;
    private String company;
    private String registerName;
    private String contactNo;
    private String email;
    private String password;
    private String serviceType;
    private String documentId;



    public MapLocation() {
    }


    public MapLocation(double latitude, double longitude, String company, String registerName,
                       String contactNo, String email, String serviceType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
        this.registerName = registerName;
        this.contactNo = contactNo;
        this.email = email;
        this.serviceType = serviceType;
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

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
