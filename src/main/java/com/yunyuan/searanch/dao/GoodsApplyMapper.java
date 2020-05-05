package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.GoodsApply;
import com.yunyuan.searanch.entity.GoodsApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsApplyMapper {
    int countByExample(GoodsApplyExample example);

    int deleteByExample(GoodsApplyExample example);

    int deleteByPrimaryKey(Long applyId);

    int insert(GoodsApply record);

    int insertSelective(GoodsApply record);

    List<GoodsApply> selectByExample(GoodsApplyExample example);

    GoodsApply selectByPrimaryKey(Long applyId);

    int updateByExampleSelective(@Param("record") GoodsApply record, @Param("example") GoodsApplyExample example);

    int updateByExample(@Param("record") GoodsApply record, @Param("example") GoodsApplyExample example);

    int updateByPrimaryKeySelective(GoodsApply record);

    int updateByPrimaryKey(GoodsApply record);
}