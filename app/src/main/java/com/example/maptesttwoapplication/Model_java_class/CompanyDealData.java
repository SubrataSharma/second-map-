package com.example.maptesttwoapplication.Model_java_class;



public class CompanyDealData {
    private String userName;
    private String serviceType;
    private String contact;
    private String contactNo;
    private String userId;



    public CompanyDealData() {
    }


    public CompanyDealData(String userName, String serviceType, String contact, String contactNo, String userId) {
        this.userName = userName;
        this.serviceType = serviceType;
        this.contact = contact;
        this.contactNo = contactNo;
        this.userId = userId;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
