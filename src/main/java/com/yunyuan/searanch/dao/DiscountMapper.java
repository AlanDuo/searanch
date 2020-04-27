package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.Discount;
import com.yunyuan.searanch.entity.DiscountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscountMapper {
    int countByExample(DiscountExample example);

    int deleteByExample(DiscountExample example);

    int deleteByPrimaryKey(Long discountId);

    int insert(Discount record);

    int insertSelective(Discount record);

    List<Discount> selectByExample(DiscountExample example);

    Discount selectByPrimaryKey(Long discountId);

    int updateByExampleSelective(@Param("record") Discount record, @Param("example") DiscountExample example);

    int updateByExample(@Param("record") Discount record, @Param("example") DiscountExample example);

    int updateByPrimaryKeySelective(Discount record);

    int updateByPrimaryKey(Discount record);
}