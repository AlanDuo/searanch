package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.GoodsPush;
import com.yunyuan.searanch.entity.GoodsPushExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsPushMapper {
    int countByExample(GoodsPushExample example);

    int deleteByExample(GoodsPushExample example);

    int deleteByPrimaryKey(Long pushId);

    int insert(GoodsPush record);

    int insertSelective(GoodsPush record);

    List<GoodsPush> selectByExample(GoodsPushExample example);

    GoodsPush selectByPrimaryKey(Long pushId);

    int updateByExampleSelective(@Param("record") GoodsPush record, @Param("example") GoodsPushExample example);

    int updateByExample(@Param("record") GoodsPush record, @Param("example") GoodsPushExample example);

    int updateByPrimaryKeySelective(GoodsPush record);

    int updateByPrimaryKey(GoodsPush record);
}