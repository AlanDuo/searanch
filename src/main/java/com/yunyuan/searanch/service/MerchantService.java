package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.GoodsApplyDTO;
import com.yunyuan.searanch.entity.MerchantRegister;

import java.util.Date;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/30
 */
public interface MerchantService {
    /**
     * 根据用户id获取商户的id
     *
     * @param userId
     * @return
     */
    MerchantRegister getMerchantIdByUserId(Long userId);

    /**
     * 商家账单
     *
     * @param merchantId
     * @param date
     * @return
     */
    Map<String,Object> getBill(Long merchantId, Date date);

    /**
     * 商品申请入库
     *
     * @param merchant
     * @param goodsApplyDTO
     * @return
     */
    boolean putInStorage(Long merchant, GoodsApplyDTO goodsApplyDTO);
}
