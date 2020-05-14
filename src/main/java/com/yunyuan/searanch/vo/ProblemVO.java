package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVO implements Serializable {
    private Long feedbackId;

    private Long feedbackUserId;

    private String feedbackUser;

    private String feedbackContent;

    private String email;

    private Date feedbackTime;

}
