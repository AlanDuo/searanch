package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.AdminOrderUpdateDTO;
import com.yunyuan.searanch.dto.AdminRegisterDTO;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.vo.*;

import java.util.List;
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
     * 获取问题反馈
     *
     * @param role 用户的类型
     * @return
     */
    List<ProblemVO> getProblems(String role);

    /**
     * 以发送邮件的形式回复问题反馈
     *
     * @param feedbackId
     * @param content
     * @param userId
     * @return
     */
    boolean dealWithProblem(Long feedbackId, String content,Long userId);

    /**
     * 问题的细节
     *
     * @param feedbackId
     * @return
     */
    ProblemDetailVO problemDetails(Long feedbackId);

    /**
     * 管理员界面---订单列表
     *
     * @param orderNumber
     * @param phoneNumber
     * @param userName
     * @return
     */
    Map<String,Object> adminOrderList(String orderNumber, String phoneNumber, String userName);

    /**
     * 管理员查看订单信息
     *
     * @param orderNumber
     * @return
     */
    AdminOrderInfoVO adminOrderInfo(String orderNumber);

    /**
     * 管理员--更新订单
     *
     * @param orderUpdateDTO
     * @return
     */
    boolean adminUpdateOrder(AdminOrderUpdateDTO orderUpdateDTO);

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

    /**
     * 管理员界面-用户列表
     *
     * @return
     */
    Map<String,Object> adminUserList(String userName);

    /**
     * 用户购买记录
     *
     * @param userId
     * @return
     */
    List<UserConsumeVO> userConsumeRecord(Long userId);
}
