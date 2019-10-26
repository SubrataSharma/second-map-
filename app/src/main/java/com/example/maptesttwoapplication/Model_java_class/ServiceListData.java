package com.example.maptesttwoapplication.Model_java_class;


import com.google.firebase.firestore.Exclude;

public class ServiceListData {
    private String userName;
    private String serviceType;
    private String company_contact_email;
    private String userId;
    private String serviceTime;
    private String company_name;
    private String company_contact_no;
    private String user_contact_no;
    private boolean status;

    public ServiceListData() {
    }

    public ServiceListData(String userName, String serviceType, String company_contact_email,String user_contact_no
                           ,String userId, String serviceTime,boolean status) {
        this.userName = userName;
        this.serviceType = serviceType;
        this.company_contact_email = company_contact_email;
        this.user_contact_no = user_contact_no;
        this.userId = userId;
        this.serviceTime = serviceTime;
        this.status = status;

    }

    public ServiceListData(String userName, String serviceType, String company_contact_email, String userId
            , String serviceTime, String company_name, String company_contact_no, boolean status) {
        this.userName = userName;
        this.serviceType = serviceType;
        this.company_contact_email = company_contact_email;
        this.userId = userId;
        this.serviceTime = serviceTime;
        this.company_name = company_name;
        this.company_contact_no = company_contact_no;
        this.status = status;
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

    public String getCompany_contact_email() {
        return company_contact_email;
    }

    public void setCompany_contact_email(String company_contact_email) {
        this.company_contact_email = company_contact_email;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_contact_no() {
        return company_contact_no;
    }

    public void setCompany_contact_no(String company_contact_no) {
        this.company_contact_no = company_contact_no;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(String user_contact_no) {
        this.user_contact_no = user_contact_no;
    }
}
