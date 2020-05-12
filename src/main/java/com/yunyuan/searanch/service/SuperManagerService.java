package com.yunyuan.searanch.service;

import com.yunyuan.searanch.vo.AdminListVO;

import java.util.Map;

/**
 * @author alan
 * @date 2020/4/21
 */
public interface SuperManagerService {

    /**
     * 管理员列表
     *
     * @param check
     * @return
     */
    Map<String,Object> adminList(boolean check);

    /**
     * 管理员查询（精确查询）
     *
     * @param check
     * @param userId
     * @param username
     * @param phoneNumber
     * @return
     */
    AdminListVO searchAdmin(boolean check,Long userId,String username,String phoneNumber);

    /**
     * 审核管理员
     *
     * @param userId
     * @param check
     * @return
     */
    boolean examineAdmin(Long userId,boolean check);

    /**
     * 删除管理员
     *
     * @param userId
     * @return
     */
    boolean deleteAdmin(Long userId);
}
