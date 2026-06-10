package com.zzy.datawarehouse.display.controller;



import com.alibaba.cola.dto.SingleResponse;
import com.zzy.datawarehouse.display.entity.CnRegionInfo;
import com.zzy.datawarehouse.display.param.RegionParam;
import com.zzy.datawarehouse.display.service.CnRegionInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 中国地区信息
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Api(tags = "中国地区信息 管理")
@RestController
@RequestMapping("display/cnregioninfo")
public class CnRegionInfoController {

    @Autowired
    private CnRegionInfoService cnRegionInfoService;

    @ApiOperation(value = "地区查询接口")
    @GetMapping("list")
    public SingleResponse<List<CnRegionInfo>> getRegionList(RegionParam param) {
        if (!"1".equals(param.getLevel())) {
            if (StringUtils.isBlank(param.getAreaCode())) {
                return SingleResponse.buildFailure("9632", "缺少参数");
            }
        }
        List<CnRegionInfo> list = cnRegionInfoService.lambdaQuery()
                .eq(CnRegionInfo::getCriLevel, param.getLevel())
                .eq(!StringUtils.isBlank(param.getAreaCode()), CnRegionInfo::getCriSuperiorCode, param.getAreaCode())
                .list();
        return SingleResponse.of(list);
    }
}
