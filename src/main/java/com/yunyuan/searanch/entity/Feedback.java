package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {
    private Long feedbackId;

    private Long feedbackUser;

    private String feedbackContent;

    private String image;

    private Date feedbackTime;

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
}