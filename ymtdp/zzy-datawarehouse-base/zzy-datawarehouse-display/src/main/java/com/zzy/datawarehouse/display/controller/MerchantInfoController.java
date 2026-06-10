package com.zzy.datawarehouse.display.controller;


import com.alibaba.cola.dto.SingleResponse;
import com.zzy.datawarehouse.display.entity.OneOpenApiRecord;
import com.zzy.datawarehouse.display.service.MerchantInfoService;
import com.zzy.datawarehouse.display.vo.ScenicMerchantsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 商户信息表
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
@Api(tags = "商户信息")
@RestController
@RequestMapping("display/merchantinfo")
public class MerchantInfoController {

    @Autowired
    private MerchantInfoService merchantInfoService;

    @ApiOperation(value = "景区商家入住数据")
    @GetMapping("list")
    public SingleResponse<List<ScenicMerchantsVO>> getMerchantEnterNum() {
       List<ScenicMerchantsVO> list = merchantInfoService.getMerchantEnterNum();
        return SingleResponse.of(list);
    }
}
