package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Discount implements Serializable {
    private Long discountId;

    private Long goodsId;

    private String discountDesc;

    private BigDecimal discountAmount;

    private BigDecimal discountTerm;

    private Byte discountType;

    private Boolean work;

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc == null ? null : discountDesc.trim();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountTerm() {
        return discountTerm;
    }

    public void setDiscountTerm(BigDecimal discountTerm) {
        this.discountTerm = discountTerm;
    }

    public Byte getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Byte discountType) {
        this.discountType = discountType;
    }

    public Boolean getWork() {
        return work;
    }

    public void setWork(Boolean work) {
        this.work = work;
    }
}