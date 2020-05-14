package com.yunyuan.searanch.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author alan
 * @date 2020/5/13
 */
@Data
public class ProblemDetailVO implements Serializable {

    private String feedbackContent;

    private String image;

    private Date feedbackTime;

    private Boolean progressRate;

    private String response;

    private String handler;

    private Date handlerTime;
}
