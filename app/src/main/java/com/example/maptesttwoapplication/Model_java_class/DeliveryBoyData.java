package com.example.maptesttwoapplication.Model_java_class;



public class DeliveryBoyData {

    private String id;
    private String area_of_work;
    private String registerName;
    private String contactNo;
    private long pin;

    public DeliveryBoyData() {
    }

    public DeliveryBoyData(String id, String area_of_work, String registerName, String contactNo, long pin) {
        this.id = id;
        this.area_of_work = area_of_work;
        this.registerName = registerName;
        this.contactNo = contactNo;
        this.pin = pin;
    }

    public String getArea_of_work() {
        return area_of_work;
    }

    public void setArea_of_work(String area_of_work) {
        this.area_of_work = area_of_work;
    }

    public String getId() {
        return id;
    }

    public String getRegisterName() {
        return registerName;
    }

    public String getContactNo() {
        return contactNo;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }
}
