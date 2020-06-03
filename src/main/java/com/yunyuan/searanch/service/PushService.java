package com.yunyuan.searanch.service;

/**
 * @author alan
 * @date 2020/4/23
 */
public interface PushService {
    /**
     * 发送邮件
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param userId
     * @param tos 接收者邮箱
     */
    void sendMail(String subject, String content, Long userId,String... tos);

    /**
     * 向所有商家推送
     *
     * @param subject
     * @param content
     * @param userId
     */
    void sendMailAllMerchant(String subject, String content, Long userId);

    /**
     * 向所有的用户发送邮件
     *
     * @param subject
     * @param content
     * @param userId
     */
    void sendMailToAllUser(String subject, String content, Long userId);
}
