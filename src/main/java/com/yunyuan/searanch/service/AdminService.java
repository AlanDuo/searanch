package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.User;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/25
 */
public interface AdminService {
    /**
     * 判断该管理员是否已经注册
     *
     * @param phone
     * @return
     */
    boolean adminExist(String phone);
    /**
     * 管理员注册
     *
     * @param adminRegisterDTO
     * @return
     */
    boolean adminRegister(AdminRegisterDTO adminRegisterDTO);
    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param user
     * @return
     */
    boolean changePassword(String oldPassword,String newPassword, User user);

    /**
     * 管理员界面---订单列表
     *
     * @param orderNumber
     * @param goodsName
     * @param userName
     * @param merchant
     * @return
     */
    Map<String,Object> adminOrderList(String orderNumber, String goodsName, String userName, String merchant);

    /**
     * 管理员--更新订单
     *
     * @param orderNumber
     * @param amount
     * @return
     */
    boolean adminUpdateOrder(String orderNumber,Integer amount);

    /**
     * 管理员--删除订单
     *
     * @param orderNumber
     * @return
     */
    boolean adminDeleteOrder(String orderNumber);

    /**
     * 商家列表
     *
     * @param merchantId
     * @param userName
     * @param merchantName
     * @param check
     * @return
     */
    Map<String,Object> adminMerchantList(Long merchantId,String userName,String merchantName,Boolean check);

    /**
     *商家证件信息
     *
     * @param registerId
     * @return
     */
    Map<String,String> adminMerchantInfo(Long registerId);

    /**
     * 审核商家
     *
     * @param registerId
     * @param check
     * @return
     */
    boolean adminCheckMerchant(Long registerId,boolean check);
}
