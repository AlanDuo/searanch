package com.yunyuan.searanch.entity;

import java.util.Date;

public class Push {
    private Long pushId;

    private Long pushFrom;

    private Long pushTo;

    private String pushContent;

    private Date pushTime;

    public Long getPushId() {
        return pushId;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public Long getPushFrom() {
        return pushFrom;
    }

    public void setPushFrom(Long pushFrom) {
        this.pushFrom = pushFrom;
    }

    public Long getPushTo() {
        return pushTo;
    }

    public void setPushTo(Long pushTo) {
        this.pushTo = pushTo;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent == null ? null : pushContent.trim();
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}