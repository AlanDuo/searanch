package com.yunyuan.searanch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateVO implements Serializable {
    private String nickname;

    private String userImag;

    private String goodsEva;

    private String image;

    private Integer goodsAbout;

    private List<EvaluateReplyVO> evaluateReplyVO;

    private Boolean anonymous;
}
