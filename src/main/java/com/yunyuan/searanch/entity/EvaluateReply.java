package com.yunyuan.searanch.entity;

import java.io.Serializable;
import java.util.Date;

public class EvaluateReply implements Serializable {
    private Long replyId;

    private Long replyUser;

    private Long evaluateId;

    private String replyContent;

    private Date replyTime;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(Long replyUser) {
        this.replyUser = replyUser;
    }

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}