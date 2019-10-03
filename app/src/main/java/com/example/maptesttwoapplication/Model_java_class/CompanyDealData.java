package com.example.maptesttwoapplication.Model_java_class;



public class CompanyDealData {
    private String userName;
    private String serviceType;
    private String contact;

    public CompanyDealData() {
    }

    public CompanyDealData(String userName, String serviceType, String contact) {
        this.userName = userName;
        this.serviceType = serviceType;
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
