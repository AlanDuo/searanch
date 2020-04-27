package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.util.Date;

public class Browse implements Serializable {
    private Long browseId;

    private Long userId;

    private Long goodsId;

    private Integer weight;

    private Date browseTime;

    public Long getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Long browseId) {
        this.browseId = browseId;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
}