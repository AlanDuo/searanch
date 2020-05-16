package com.yunyuan.searanch.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProblemDetailVO implements Serializable {

    private String feedbackContent;

    private String image;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date feedbackTime;

    private Boolean progressRate;

    private String response;

    private String handler;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date handlerTime;
}
