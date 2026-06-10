package com.nuwa.discovery.start.api.controller.common;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.discovery.start.api.controller.common.param.RegionParam;
import com.nuwa.infrastructure.discovery.database.user.entity.CnRegionInfo;
import com.nuwa.infrastructure.discovery.database.user.service.CnRegionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 中国地区信息 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2020-11-11
 */
@Api(tags = {"中国地区信息-相关接口"})
@Slf4j
@RestController
@RequestMapping("/region")
public class CnRegionInfoController {

    @Autowired
    private CnRegionInfoService cnRegionInfoService;

    @ApiOperation(value = "地区查询接口")
    @GetMapping("list")
    public SingleResponse<List<CnRegionInfo>> getRegionList(RegionParam param) {
        if (!"1".equals(param.getLevel())) {
            if (StrUtil.isBlankOrUndefined(param.getAreaCode())) {
                return SingleResponse.buildFailure("9632", "缺少参数");
            }
        }
        List<CnRegionInfo> list = cnRegionInfoService.lambdaQuery()
                .eq(CnRegionInfo::getCriLevel, param.getLevel())
                .eq(!StrUtil.isBlankOrUndefined(param.getAreaCode()), CnRegionInfo::getCriSuperiorCode, param.getAreaCode())
                .list();
        return SingleResponse.of(list);
    }

}
