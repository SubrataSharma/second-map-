package com.example.maptesttwoapplication.Model_java_class;


import java.util.List;

public class MapLocation {

    private String id;
    private double latitude;
    private double longitude;
    private String company;
    private String registerName;
    private String contactNo;
    private long pin;

    private List<String> services;
    private String about_company;
    private String service_process;
    private String owner_word;



    public MapLocation() {
    }

    public MapLocation(String id, double latitude, double longitude, String company, String registerName
            , String contactNo, long pin, List<String> services, String about_company, String service_process
            , String owner_word) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
        this.registerName = registerName;
        this.contactNo = contactNo;
        this.pin = pin;
        this.services = services;
        this.about_company = about_company;
        this.service_process = service_process;
        this.owner_word = owner_word;
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

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public List<String> getServices() {
        return services;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }


    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getAbout_company() {
        return about_company;
    }

    public void setAbout_company(String about_company) {
        this.about_company = about_company;
    }

    public String getService_process() {
        return service_process;
    }

    public void setService_process(String service_process) {
        this.service_process = service_process;
    }

    public String getOwner_word() {
        return owner_word;
    }

    public void setOwner_word(String owner_word) {
        this.owner_word = owner_word;
    }
}
