package com.yunyuan.searanch.service.impl;

import com.github.pagehelper.PageHelper;
import com.yunyuan.searanch.dao.*;
import com.yunyuan.searanch.entity.*;
import com.yunyuan.searanch.service.PageService;
import com.yunyuan.searanch.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author alan
 * @date 2020/3/21
 */
@Service
public class PageServiceImpl implements PageService {
    @Resource
    private BrowseMapper browseMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private EvaluateMapper evaluateMapper;
    @Resource
    private EvaluateReplyMapper evaluateReplyMapper;
    @Resource
    private DiscountMapper discountMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsTypeMapper goodsTypeMapper;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private GoodsApplyMapper applyMapper;
    @Resource
    private MerchantRegisterMapper merchantRegisterMapper;
    @Resource
    private GoodsPushMapper goodsPushMapper;

    @Override
    //@Cacheable(value = "goodsNoLogin")
    public Map<String,Object> recommendWithoutLogin(int page,int limit) {
        PageHelper.startPage(page,limit);
        GoodsExample goodsExample=new GoodsExample();
        GoodsExample.Criteria goodsCriteria=goodsExample.createCriteria();
        goodsCriteria.andUpShelfEqualTo(true);
        goodsCriteria.andStockGreaterThan(0);
        return goodsResult(goodsExample,null);
    }

    @Override
    //@Cacheable(value = "goodsWithLogin")
    public Map<String,Object> recommendWithLogin(int page,int limit,Long userId) {
        List<String> typeList=getRecentBrowse(userId);
        PageHelper.startPage(page,limit);
        GoodsExample goodsExample=new GoodsExample();
        for(String goodsType:typeList) {
            goodsExample.or().andTypeLike("%"+goodsType+"%");
        }
        return goodsResult(goodsExample,userId);
    }

    /**
     * 对查找商品并封装成页面展示对象的一个封装（对这两个方法recommendWithoutLogin，recommendWithLogin,searchGoods公共部分的封装）
     *
     * @param goodsExample
     * @return
     */
    private Map<String,Object> goodsResult(GoodsExample goodsExample,Long userId){
        Map<String,Object> map=new HashMap<>(2);
        List<Goods> goodsList=goodsMapper.selectByExample(goodsExample);
        map.put("pageInfo",goodsList);
        List<PageGoodsVO> pageGoodsVOList=new ArrayList<>();
        for(Goods goods:goodsList){
            PageGoodsVO pageGoodsVO=new PageGoodsVO();
            EvaluateExample evaluateExample=new EvaluateExample();
            EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
            evaluateCriteria.andGoodsIdEqualTo(goods.getGoodsId());
            int evaluateAmount=evaluateMapper.countByExample(evaluateExample);
            pageGoodsVO.setEvaluateAmount(evaluateAmount);
            CollectExample collectExample=new CollectExample();
            CollectExample.Criteria collectCriteria=collectExample.createCriteria();
            collectCriteria.andGoodsIdEqualTo(goods.getGoodsId());
            int collectAmount=collectMapper.countByExample(collectExample);
            pageGoodsVO.setLikeAmount(collectAmount);
            BeanUtils.copyProperties(goods,pageGoodsVO);
            pageGoodsVO.setDesc(goods.getGoodsDesc());
            if(null!=userId){
                CollectExample collectExample1=new CollectExample();
                CollectExample.Criteria collectCriteria1=collectExample1.createCriteria();
                collectCriteria1.andGoodsIdEqualTo(goods.getGoodsId());
                collectCriteria1.andUserIdEqualTo(userId);
                int isLike=collectMapper.countByExample(collectExample1);
                System.out.println(isLike);
                if(isLike>0){
                    pageGoodsVO.setIsLike(true);
                }else{
                    pageGoodsVO.setIsLike(false);
                }
            }
            pageGoodsVOList.add(pageGoodsVO);
        }
        map.put("pageGoodsVOList",pageGoodsVOList);
        return map;
    }

    @Override
    public List<String> getRecentBrowse(Long userId) {
        if(null==userId){
            return null;
        }
        BrowseExample browseExample=new BrowseExample();
        browseExample.setOrderByClause("weight desc");
        BrowseExample.Criteria browseCriteria=browseExample.createCriteria();
        browseCriteria.andUserIdEqualTo(userId);
        List<Browse> browses=browseMapper.selectByExample(browseExample);
        if(browses.size()==0){
            return null;
        }
        List<String> typeList=new ArrayList<>();
        for(Browse browse:browses){
            if(typeList.size()>5){
                break;
            }
            Goods goods=goodsMapper.selectByPrimaryKey(browse.getGoodsId());
            if(!typeList.contains(goods.getType())) {
                typeList.add(goods.getType());
            }
        }
        return typeList;
    }

    @Override
    @Cacheable(value = "goodsInfo")
    public GoodsInfoVO getGoodsInfo(Long goodsId,User user) {
        GoodsInfoVO goodsInfoVO=new GoodsInfoVO();
        Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
        BeanUtils.copyProperties(goods,goodsInfoVO);
        List<String> pictures=Arrays.asList(goods.getPicture().split(","));
        goodsInfoVO.setPictures(pictures);
        goodsInfoVO.setDesc(goods.getGoodsDesc());

        if(null!=goods.getApplyId() && goods.getApplyId()!=0) {
            GoodsApply apply = applyMapper.selectByPrimaryKey(goods.getApplyId());
            goodsInfoVO.setBreedTime(apply.getBreedTime());
        }
        MerchantRegister merchant=merchantRegisterMapper.selectByPrimaryKey(goods.getBusiness());
        goodsInfoVO.setMerchant(merchant.getMerchantName());

        EvaluateExample evaluateExample=new EvaluateExample();
        EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
        evaluateCriteria.andGoodsIdEqualTo(goodsId);
        int evaluateAmount=evaluateMapper.countByExample(evaluateExample);
        goodsInfoVO.setEvaluateAmount(evaluateAmount);
        DiscountExample discountExample=new DiscountExample();
        DiscountExample.Criteria discountCriteria=discountExample.createCriteria();
        discountCriteria.andGoodsIdEqualTo(goodsId);
        discountCriteria.andWorkEqualTo(true);
        List<Discount> discountList=discountMapper.selectByExample(discountExample);
        if(discountList.size()!=0) {
            List<DiscountVO> discountInfo = new ArrayList<>();
            for (Discount discount : discountList) {
                DiscountVO discountVO=new DiscountVO();
                BeanUtils.copyProperties(discount,discountVO);
                discountInfo.add(discountVO);
            }
            goodsInfoVO.setDiscountInfo(discountInfo);
        }
        GoodsTypeExample goodsTypeExample=new GoodsTypeExample();
        GoodsTypeExample.Criteria typeCriteria=goodsTypeExample.createCriteria();
        typeCriteria.andGoodsIdEqualTo(goodsId);
        List<GoodsType> goodsTypes=goodsTypeMapper.selectByExample(goodsTypeExample);
        if(goodsTypes.size()!=0){
            goodsInfoVO.setGoodsTypes(goodsTypes);
        }
        if(null!=user) {
            BrowseExample browseExample = new BrowseExample();
            BrowseExample.Criteria browseCriteria = browseExample.createCriteria();
            browseCriteria.andUserIdEqualTo(user.getUserId());
            browseCriteria.andGoodsIdEqualTo(goodsId);
            List<Browse> browses=browseMapper.selectByExample(browseExample);
            if(browses.size()!=0){
                Browse browse=browses.get(0);
                browse.setWeight(browse.getWeight()+10);
                browse.setBrowseTime(new Date());
                browseMapper.updateByPrimaryKeySelective(browse);
            }else{
                Browse browse=new Browse();
                browse.setUserId(user.getUserId());
                browse.setGoodsId(goodsId);
                browse.setWeight(10);
                browse.setBrowseTime(new Date());
                browseMapper.insertSelective(browse);
            }
        }
        return goodsInfoVO;
    }

    @Override
    //@Cacheable(value = "evaluateInfo")
    public Map<String,Object> evaluateInfo(Long goodsId) {
        Map<String,Object> map=new HashMap<>(2);
        EvaluateExample evaluateExample=new EvaluateExample();
        EvaluateExample.Criteria evaluateCriteria=evaluateExample.createCriteria();
        evaluateCriteria.andGoodsIdEqualTo(goodsId);
        List<Evaluate> evaluateList=evaluateMapper.selectByExample(evaluateExample);
        map.put("pageInfo",evaluateList);
        List<EvaluateVO> evaluateVOList=new ArrayList<>();
        for(Evaluate evaluate:evaluateList){
            EvaluateVO evaluateVO=new EvaluateVO();
            BeanUtils.copyProperties(evaluate,evaluateVO);

            User user = userMapper.selectByPrimaryKey(evaluate.getUserId());
            evaluateVO.setNickname(user.getNickname());
            evaluateVO.setUserImag(user.getImage());

            EvaluateReplyExample evaluateReplyExample=new EvaluateReplyExample();
            EvaluateReplyExample.Criteria evaluateReplyCriteria=evaluateReplyExample.createCriteria();
            evaluateReplyCriteria.andEvaluateIdEqualTo(evaluate.getEvaluateId());
            List<EvaluateReply> evaluateReplyList=evaluateReplyMapper.selectByExample(evaluateReplyExample);
            if(evaluateReplyList.size()!=0) {
                List<EvaluateReplyVO> replyVOList = new ArrayList<>();
                for (EvaluateReply reply : evaluateReplyList) {
                    EvaluateReplyVO evaluateReplyVO = new EvaluateReplyVO();
                    Long replyUser = reply.getReplyUser();
                    String replyNickname = userMapper.selectByPrimaryKey(replyUser).getNickname();
                    evaluateReplyVO.setReplyUser(replyNickname);
                    evaluateReplyVO.setReplyContent(reply.getReplyContent());
                    replyVOList.add(evaluateReplyVO);
                }
                evaluateVO.setEvaluateReplyVO(replyVOList);
            }
            evaluateVOList.add(evaluateVO);
        }
        map.put("evaluateVOList",evaluateVOList);
        return map;
    }

    @Override
    public Map<String, Object> searchGoods(String searchName,Long userId) {
        GoodsExample goodsExample=new GoodsExample();
        goodsExample.or().andGoodsNameLike("%"+searchName+"%");
        goodsExample.or().andTypeLike("%"+searchName+"%");
        goodsExample.or().andProcessModeLike("%"+searchName+"%");
        goodsExample.or().andGoodsDescLike("%"+searchName+"%");
        goodsExample.or().andCountryLike("%"+searchName+"%");
        goodsExample.or().andRegionLike("%"+searchName+"%");
        goodsExample.or().andCityLike("%"+searchName+"%");
        return goodsResult(goodsExample,userId);
    }

    @Override
    public Map<String, Object> bottomRecommend(int page, int limit, long goodsId) {
        Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
        PageHelper.startPage(page,limit);
        GoodsExample goodsExample=new GoodsExample();
        GoodsExample.Criteria goodsCriteria=goodsExample.createCriteria();
        goodsCriteria.andUpShelfEqualTo(true);
        goodsCriteria.andStockGreaterThan(0);
        goodsCriteria.andTypeLike("%"+goods.getType()+"%");
        return goodsResult(goodsExample,null);
    }

    @Override
    public List<AdsVO> adsRecommend(){
        List<AdsVO> adsVOList=new ArrayList<>();
        GoodsPushExample goodsPushExample=new GoodsPushExample();
        goodsPushExample.setOrderByClause("push_id DESC");
        GoodsPushExample.Criteria goodsPushCriteria=goodsPushExample.createCriteria();
        List<GoodsPush> goodsPushList=goodsPushMapper.selectByExample(goodsPushExample);
        int len=goodsPushList.size();
        int count=0;
        for(GoodsPush goodsPush:goodsPushList){
            if(len>5 && count>5){
                break;
            }
            Goods goods=goodsMapper.selectByPrimaryKey(goodsPush.getGoodsId());
            AdsVO adsVO=new AdsVO();
            BeanUtils.copyProperties(goods,adsVO);
            adsVOList.add(adsVO);
            count++;
        }
        return adsVOList;
    }

    @Override
    public int getLikeTimes(Long userId, Long goodsId) {
        CollectExample collectExample=new CollectExample();
        CollectExample.Criteria collectCriteria=collectExample.createCriteria();
        collectCriteria.andUserIdEqualTo(userId);
        collectCriteria.andGoodsIdEqualTo(goodsId);
        return collectMapper.countByExample(collectExample);
    }

    @Override
    public boolean likeGoods(Long userId, Long goodsId) {
        Collect collect=new Collect();
        collect.setUserId(userId);
        collect.setGoodsId(goodsId);
        collect.setCollectTime(new Date());
        return collectMapper.insertSelective(collect)>0;
    }

    @Override
    public boolean cancelLike(Long userId, Long goodsId) {
        CollectExample collectExample=new CollectExample();
        CollectExample.Criteria collectCriteria=collectExample.createCriteria();
        collectCriteria.andUserIdEqualTo(userId);
        collectCriteria.andGoodsIdEqualTo(goodsId);
        return collectMapper.deleteByExample(collectExample)>0;
    }
}
