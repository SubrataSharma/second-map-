package com.example.maptesttwoapplication.Model_java_class;


public class SellerData {
    private String sellingId;
    private String productName;
    private String sellerName;
    private String contactNo;
    private String sellingPrice;
    private String sellingType;
    private String fileUrl;
    private String productDetail;
    private String contactDetail;
    private String imageFileName;


    public SellerData() {
    }

    public SellerData(String sellingId, String productName, String sellerName, String contactNo
            , String sellingPrice, String sellingType, String fileUrl, String productDetail
            , String contactDetail, String imageFileName) {
        this.sellingId = sellingId;
        this.productName = productName;
        this.sellerName = sellerName;
        this.contactNo = contactNo;
        this.sellingPrice = sellingPrice;
        this.sellingType = sellingType;
        this.fileUrl = fileUrl;
        this.productDetail = productDetail;
        this.contactDetail = contactDetail;
        this.imageFileName = imageFileName;
    }

    public String getSellingId() {
        return sellingId;
    }

    public void setSellingId(String sellingId) {
        this.sellingId = sellingId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSellingType() {
        return sellingType;
    }

    public void setSellingType(String sellingType) {
        this.sellingType = sellingType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
