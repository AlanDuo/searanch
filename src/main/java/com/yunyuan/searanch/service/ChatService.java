package com.yunyuan.searanch.service;

/**
 * @author alan
 * @date 2020/4/28
 */
public interface ChatService {
    /**
     * 增加一条聊天记录（文本）
     *
     * @param fromId
     * @param toId
     * @param content
     * @return
     */
    boolean addChatRecord(Long fromId,Long toId,String content);

    /**
     * 检查此人是否管理员
     *
     * @param userId
     * @return
     */
    boolean isAdmin(Long userId);
}
