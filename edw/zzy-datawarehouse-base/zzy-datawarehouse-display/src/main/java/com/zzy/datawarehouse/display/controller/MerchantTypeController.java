package com.zzy.datawarehouse.display.controller;


import com.zzy.datawarehouse.display.service.MerchantTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户分类表
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
@Api(tags = "商户分类表 管理")
@RestController
@RequestMapping("display/merchanttype")
public class MerchantTypeController {

    @Autowired
    private MerchantTypeService merchantTypeService;

}
