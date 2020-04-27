package com.yunyuan.searanch.service;

import com.yunyuan.searanch.dto.GoodsAddDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/4/3
 */
public interface GoodsManagerService {
    /**
     * 增加商品
     *
     * @param goodsAddDTO
     * @param file
     * @param request
     * @return
     */
    boolean addGoods(GoodsAddDTO goodsAddDTO, MultipartFile file, HttpServletRequest request);
}
