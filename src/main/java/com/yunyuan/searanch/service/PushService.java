package com.yunyuan.searanch.service;

import java.util.List;

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
}
