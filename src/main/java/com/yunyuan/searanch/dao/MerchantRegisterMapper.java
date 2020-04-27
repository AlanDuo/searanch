package com.yunyuan.searanch.dao;

import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.MerchantRegisterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantRegisterMapper {
    int countByExample(MerchantRegisterExample example);

    int deleteByExample(MerchantRegisterExample example);

    int deleteByPrimaryKey(Long registraId);

    int insert(MerchantRegister record);

    int insertSelective(MerchantRegister record);

    List<MerchantRegister> selectByExample(MerchantRegisterExample example);

    MerchantRegister selectByPrimaryKey(Long registraId);

    int updateByExampleSelective(@Param("record") MerchantRegister record, @Param("example") MerchantRegisterExample example);

    int updateByExample(@Param("record") MerchantRegister record, @Param("example") MerchantRegisterExample example);

    int updateByPrimaryKeySelective(MerchantRegister record);

    int updateByPrimaryKey(MerchantRegister record);
}