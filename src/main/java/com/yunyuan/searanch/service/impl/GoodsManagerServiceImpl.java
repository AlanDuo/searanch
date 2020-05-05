package com.yunyuan.searanch.service.impl;

import com.yunyuan.searanch.dao.GoodsMapper;
import com.yunyuan.searanch.dao.GoodsTypeMapper;
import com.yunyuan.searanch.dto.GoodsAddDTO;
import com.yunyuan.searanch.entity.Goods;
import com.yunyuan.searanch.entity.GoodsType;
import com.yunyuan.searanch.service.GoodsManagerService;
import com.yunyuan.searanch.utils.FileUploadUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author alan
 * @date 2020/4/3
 */
@Service
public class GoodsManagerServiceImpl implements GoodsManagerService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoods(GoodsAddDTO goodsAddDTO, MultipartFile file, HttpServletRequest request) {
        int flag=0;
        Goods goods=new Goods();
        BeanUtils.copyProperties(goodsAddDTO,goods);
        FileUploadUtil.uploadFile(file, request);
        String path=FileUploadUtil.getUrl();
        goods.setPicture(path);
        flag=goodsMapper.insert(goods);
//        List<GoodsType> goodsTypeList=goodsAddDTO.getGoodsTypeList();
//        for(GoodsType goodsType:goodsTypeList){
//            flag+=goodsTypeMapper.insert(goodsType);
//        }
        return false;

    }
}
