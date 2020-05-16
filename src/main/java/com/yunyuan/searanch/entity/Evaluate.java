package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.util.Date;

public class Evaluate implements Serializable {
    private Long evaluateId;

    private Long orderId;

    private Long userId;

    private Long goodsId;

    private String goodsEva;

    private String serviceEva;

    private String image;

    private Integer goodsDesc;

    private Integer sellerService;

    private Integer logistics;

    private Integer goodsAbout;

    private Boolean anonymous;

    private Date evaluateTime;

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsEva() {
        return goodsEva;
    }

    public void setGoodsEva(String goodsEva) {
        this.goodsEva = goodsEva == null ? null : goodsEva.trim();
    }

    public String getServiceEva() {
        return serviceEva;
    }

    public void setServiceEva(String serviceEva) {
        this.serviceEva = serviceEva == null ? null : serviceEva.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(Integer goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getSellerService() {
        return sellerService;
    }

    public void setSellerService(Integer sellerService) {
        this.sellerService = sellerService;
    }

    public Integer getLogistics() {
        return logistics;
    }

    public void setLogistics(Integer logistics) {
        this.logistics = logistics;
    }

    public Integer getGoodsAbout() {
        return goodsAbout;
    }

    public void setGoodsAbout(Integer goodsAbout) {
        this.goodsAbout = goodsAbout;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }
}