package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.EvaluateReply;
import com.yunyuan.searanch.entity.EvaluateReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluateReplyMapper {
    int countByExample(EvaluateReplyExample example);

    int deleteByExample(EvaluateReplyExample example);

    int deleteByPrimaryKey(Long replyId);

    int insert(EvaluateReply record);

    int insertSelective(EvaluateReply record);

    List<EvaluateReply> selectByExample(EvaluateReplyExample example);

    EvaluateReply selectByPrimaryKey(Long replyId);

    int updateByExampleSelective(@Param("record") EvaluateReply record, @Param("example") EvaluateReplyExample example);

    int updateByExample(@Param("record") EvaluateReply record, @Param("example") EvaluateReplyExample example);

    int updateByPrimaryKeySelective(EvaluateReply record);

    int updateByPrimaryKey(EvaluateReply record);
}