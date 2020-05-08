package com.yunyuan.searanch.entity;

import java.util.Date;

public class MerchantRegister {
    private Long registraId;

    private String username;

    private String merchantName;

    private String merchantPhone;

    private String image;

    private String idCard;

    private String idCardImag;

    private String license;

    private String licenseImag;

    private String country;

    private String province;

    private String city;

    private String address;

    private Boolean examine;

    private Date registraTime;

    private Integer star;

    public Long getRegistraId() {
        return registraId;
    }

    public void setRegistraId(Long registraId) {
        this.registraId = registraId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Boolean getExamine() {
        return examine;
    }

    public void setExamine(Boolean examine) {
        this.examine = examine;
    }

    public Date getRegistraTime() {
        return registraTime;
    }

    public void setRegistraTime(Date registraTime) {
        this.registraTime = registraTime;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}