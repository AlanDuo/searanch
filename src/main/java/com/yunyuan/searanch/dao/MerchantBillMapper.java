package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.MerchantBill;
import com.yunyuan.searanch.entity.MerchantBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantBillMapper {
    int countByExample(MerchantBillExample example);

    int deleteByExample(MerchantBillExample example);

    int deleteByPrimaryKey(Long billId);

    int insert(MerchantBill record);

    int insertSelective(MerchantBill record);

    List<MerchantBill> selectByExample(MerchantBillExample example);

    MerchantBill selectByPrimaryKey(Long billId);

    int updateByExampleSelective(@Param("record") MerchantBill record, @Param("example") MerchantBillExample example);

    int updateByExample(@Param("record") MerchantBill record, @Param("example") MerchantBillExample example);

    int updateByPrimaryKeySelective(MerchantBill record);

    int updateByPrimaryKey(MerchantBill record);
}