package com.yunyuan.searanch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunyuan.searanch.dto.GoodsApplyDTO;
import com.yunyuan.searanch.entity.GoodsApply;
import com.yunyuan.searanch.entity.MerchantRegister;
import com.yunyuan.searanch.entity.User;
import com.yunyuan.searanch.service.MerchantService;
import com.yunyuan.searanch.utils.ResponseData;
import com.yunyuan.searanch.vo.BillVO;
import com.yunyuan.searanch.vo.TableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * @date 2020/4/30
 */
@Api(value = "商家界面",tags = "商家界面")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);
    private static final String PAGE_INFO="pageInfo";
    private MerchantService merchantService;
    @Autowired
    public MerchantController(MerchantService merchantService){
        this.merchantService=merchantService;
    }


    @ApiOperation(value="账单")
    @GetMapping("/bill")
    public TableVO billDetails(String date,
                               @RequestParam(value = "page",defaultValue = "1")Integer page,
                               @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        MerchantRegister merchantRegister=merchantService.getMerchantIdByUserId(user.getUserId());
        if(merchantRegister==null){
            return new TableVO();
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date recordDate=null;
        try {
            if (null != date && !"".equals(date)) {
                recordDate = simpleDateFormat.parse(date);
            }
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        PageHelper.startPage(page,limit);
        Map<String,Object> map=merchantService.getBill(merchantRegister.getRegistraId(),recordDate);
        PageInfo pageInfo=new PageInfo<>((List<GoodsApply>)map.get(PAGE_INFO));
        List<BillVO> billVOList=(List<BillVO>)map.get("billVOList");
        return new TableVO<>(pageInfo,billVOList);
    }

    @ApiOperation(value = "申请入库")
    @PostMapping("/applyInStorage")
    public ResponseData applyInStorage(@Validated @RequestBody GoodsApplyDTO goodsApplyDTO){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        MerchantRegister merchantRegister=merchantService.getMerchantIdByUserId(user.getUserId());
        merchantService.putInStorage(merchantRegister.getRegistraId(),goodsApplyDTO);
        return ResponseData.ok();
    }
}
