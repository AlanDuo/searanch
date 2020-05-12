package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.MerchantRegisterDTO;
import com.yunyuan.searanch.dto.UserRegisterDTO;
import com.yunyuan.searanch.dto.UserUpdateDTO;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.User;

/**
 * @author alan
 * @date 2020/3/18
 */
public interface UserService {
    /**
     * 根据手机获取用户
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);
    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    String getRole(Long userId);
    /**
     * 更新登录状态
     *
     * @param user
     * @return
     */
    boolean updateLoginStatus(User user);
    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    User getUserById(Long userId);

    /**
     * 更新用户信息
     *
     * @param userId
     * @param userUpdateDTO
     * @return
     */
    User updateUser(Long userId, UserUpdateDTO userUpdateDTO);
    /**
     * 普通用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    boolean registerUser(UserRegisterDTO userRegisterDTO);

    /**
     * 根据手机号获取商户
     *
     * @param phone
     * @return
     */
    MerchantRegister getMerchantByPhone(String phone);
    /**
     * 商家注册
     *
     * @param merchantRegisterDTO
     * @return
     */
    boolean registerMerchant(MerchantRegisterDTO merchantRegisterDTO);

    /**
     * 用户反馈
     *
     * @param userId
     * @param content
     * @param image
     * @return
     */
    boolean feedback(Long userId,String content,String image);

}
