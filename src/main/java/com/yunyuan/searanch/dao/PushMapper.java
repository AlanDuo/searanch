package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.Push;
import com.yunyuan.searanch.entity.PushExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PushMapper {
    int countByExample(PushExample example);

    int deleteByExample(PushExample example);

    int deleteByPrimaryKey(Long pushId);

    int insert(Push record);

    int insertSelective(Push record);

    List<Push> selectByExample(PushExample example);

    Push selectByPrimaryKey(Long pushId);

    int updateByExampleSelective(@Param("record") Push record, @Param("example") PushExample example);

    int updateByExample(@Param("record") Push record, @Param("example") PushExample example);

    int updateByPrimaryKeySelective(Push record);

    int updateByPrimaryKey(Push record);
}