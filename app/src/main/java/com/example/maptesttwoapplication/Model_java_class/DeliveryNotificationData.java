package com.example.maptesttwoapplication.Model_java_class;



public class DeliveryNotificationData {

    private String id;
    private String registerName;
    private String contactNo;


    public DeliveryNotificationData() {
    }

    public DeliveryNotificationData(String id, String registerName, String contactNo) {
        this.id = id;
        this.registerName = registerName;
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
