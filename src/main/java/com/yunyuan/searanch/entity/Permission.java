package com.yunyuan.searanch.entity;

import java.io.Serializable;

public class Permission implements Serializable {
    private Long userId;

    private String permission;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}