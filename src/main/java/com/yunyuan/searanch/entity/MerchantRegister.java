package com.yunyuan.searanch.entity;

import java.util.Date;

public class MerchantRegister {
    private Long registraId;

    private String userName;

    private String merchantName;

    private String merchantPhone;

    private String image;

    private String idCard;

    private String idCardImag;

    private String license;

    private String licenseImag;

    private Boolean check;

    private Date registraTime;

    public Long getRegistraId() {
        return registraId;
    }

    public void setRegistraId(Long registraId) {
        this.registraId = registraId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone == null ? null : merchantPhone.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getIdCardImag() {
        return idCardImag;
    }

    public void setIdCardImag(String idCardImag) {
        this.idCardImag = idCardImag == null ? null : idCardImag.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getLicenseImag() {
        return licenseImag;
    }

    public void setLicenseImag(String licenseImag) {
        this.licenseImag = licenseImag == null ? null : licenseImag.trim();
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Date getRegistraTime() {
        return registraTime;
    }

    public void setRegistraTime(Date registraTime) {
        this.registraTime = registraTime;
    }
}