package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.Collect;
import com.yunyuan.searanch.entity.CollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    int countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Long collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Long collectId);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
}