package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {
    private Long feedbackId;

    private Long feedbackUser;

    private String feedbackContent;

    private String image;

    private Date feedbackTime;

    private String userType;

    private Byte progressRate;

    private String response;

    private Long handler;

    private Date handlerTime;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(Long feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent == null ? null : feedbackContent.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Byte getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(Byte progressRate) {
        this.progressRate = progressRate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public Long getHandler() {
        return handler;
    }

    public void setHandler(Long handler) {
        this.handler = handler;
    }

    public Date getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }
}