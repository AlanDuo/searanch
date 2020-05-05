package com.yunyuan.searanch.entity;

import java.util.Date;

public class GoodsApply {
    private Long applyId;

    private Long merchantId;

    private String goodsName;

    private String goodsType;

    private String picture;

    private Integer amount;

    private Date breedTime;

    private Date planUpTime;

    private String country;

    private String region;

    private String city;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getBreedTime() {
        return breedTime;
    }

    public void setBreedTime(Date breedTime) {
        this.breedTime = breedTime;
    }

    public Date getPlanUpTime() {
        return planUpTime;
    }

    public void setPlanUpTime(Date planUpTime) {
        this.planUpTime = planUpTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }
}