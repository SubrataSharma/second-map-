package com.example.maptesttwoapplication.Model_java_class;



public class UserData {
    private String id;
    private String userName;
    private String userEmail;
    private String contactNo;

    public UserData() {
    }

    public UserData(String id, String userName, String userEmail, String contactNo) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
