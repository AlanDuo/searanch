package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author alan
 * @date 2020/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateReplyVO implements Serializable {
    private String replyUser;
    private String replyContent;
}
