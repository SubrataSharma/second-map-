package com.example.maptesttwoapplication.Model_java_class;



public class ServiceListData {
    private String userName;
    private String serviceType;
    private String contact;
    private String userId;
    private String serviceTime;

    public ServiceListData() {
    }

    public ServiceListData(String userName, String serviceType, String contact, String userId, String serviceTime) {
        this.userName = userName;
        this.serviceType = serviceType;
        this.contact = contact;
        this.userId = userId;
        this.serviceTime = serviceTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}
