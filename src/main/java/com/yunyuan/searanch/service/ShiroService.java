package com.yunyuan.searanch.service;

import com.yunyuan.searanch.entity.Permission;
import com.yunyuan.searanch.entity.Role;
import com.yunyuan.searanch.entity.User;

/**
 * @author alan
 * @date 2020/3/16
 */
public interface ShiroService {
    /**
     * 获取一个用户
     *
     * @param phoneNumber
     * @return User
     */
    User getUser(String phoneNumber);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return Role
     */
    Role getRole(Long userId);

    /**
     * 获取用户的权限
     *
     * @param userId
     * @return Permission
     */
    Permission getPermission(Long userId);
}
