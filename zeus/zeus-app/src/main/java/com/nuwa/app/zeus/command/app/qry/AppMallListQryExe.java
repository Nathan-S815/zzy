package com.nuwa.app.zeus.command.app.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.app.service.AppSkuInfoService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetAppTreeQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class AppMallListQryExe extends AbstractQryExe<AppMallListQry, SingleResponse<List<AppMallListQryExe.AppInfoVO>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppSkuInfoService appSkuInfoService;

    @Override
    protected SingleResponse<List<AppInfoVO>> handle(AppMallListQry cmd) {
        List<AppInfoVO> appInfoList = appInfoService.lambdaQuery()
                .eq(AppInfo::getAppType, cmd.getAppType())
                .eq(AppInfo::getStatus, 1)
                .eq(AppInfo::getPrivatization, 0)
                .eq(AppInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .orderByDesc(AppInfo::getRecommend)
                .list().stream().map(x -> {
                    AppInfoVO vo = new AppInfoVO();
                    BeanUtil.copyProperties(x, vo);
                    AppSkuInfo sku = appSkuInfoService.lambdaQuery()
                            .eq(AppSkuInfo::getAppId, x.getId())
                            .eq(AppSkuInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                            .orderByAsc(AppSkuInfo::getPrice)
                            .last("limit 1")
                            .one();
                    vo.setPrice(sku.getNegotiable().equals("1") ? "面议" : sku.getPrice().toString());
                    return vo;
                }).collect(Collectors.toList());
        return SingleResponse.of(appInfoList);
    }

    @Data
    public static class AppInfoVO {
        @ApiModelProperty("主键")
        private Long id;
        @ApiModelProperty("应用名称")
        private String appName;
        @ApiModelProperty("logo")
        @JsonSerialize(using = MaterialJson.class)
        private Long logo;
        @ApiModelProperty("版本号")
        private String version;
        @ApiModelProperty("版本名称")
        private String versionName;
        @ApiModelProperty("应用简介")
        private String appIntroEditor;
        @ApiModelProperty("应用详情")
        private String appDetailEditor;
        @ApiModelProperty("应用教程")
        private String appCourseEditor;
        @ApiModelProperty("销量")
        private Long salesVolume;
        @ApiModelProperty("价格")
        private String price;
    }
}
