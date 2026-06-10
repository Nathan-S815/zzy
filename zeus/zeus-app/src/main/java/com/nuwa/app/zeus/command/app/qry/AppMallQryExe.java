package com.nuwa.app.zeus.command.app.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallListQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
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
import java.util.Set;
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
public class AppMallQryExe extends AbstractQryExe<AppMallQry, SingleResponse<AppMallQryExe.AppInfoVO>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppSkuInfoService appSkuInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Override
    protected SingleResponse<AppInfoVO> handle(AppMallQry cmd) {
        AppInfo appInfo = appInfoService.lambdaQuery()
                .eq(AppInfo::getId, cmd.getId())
                .one();
        AppInfoVO vo = new AppInfoVO();
        BeanUtil.copyProperties(appInfo, vo);
        AppSkuInfo sku = appSkuInfoService.lambdaQuery()
                .eq(AppSkuInfo::getAppId, appInfo.getId())
                .eq(AppSkuInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .orderByAsc(AppSkuInfo::getPrice)
                .last("limit 1")
                .one();
        vo.setPrice("1".equals(sku.getNegotiable()) ? "面议" : sku.getPrice().toString());
        Set<Long> appIds = appDependentService.lambdaQuery()
                .eq(AppDependent::getAppId, cmd.getId())
                .list()
                .stream()
                .map(AppDependent::getDependentAppId)
                .collect(Collectors.toSet());
        if (appIds.size() > 0) {
            List<Dependent> dependents = appInfoService.lambdaQuery()
                    .in(AppInfo::getId, appIds)
                    .eq(AppInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                    .list()
                    .stream()
                    .map(x -> {
                        Dependent dependent = new Dependent();
                        dependent.setAppId(x.getId());
                        dependent.setAppName(x.getAppName());
                        return dependent;
                    }).collect(Collectors.toList());
            vo.setDependents(dependents);
        }
        return SingleResponse.of(vo);
    }

    @Data
    public static class AppInfoVO {
        @ApiModelProperty("主键")
        private Long id;
        @ApiModelProperty("应用名称")
        private String appName;
        @ApiModelProperty("版本号")
        private String version;
        @ApiModelProperty("版本名称")
        private String versionName;
        @ApiModelProperty("logo")
        @JsonSerialize(using = MaterialJson.class)
        private Long logo;
        @ApiModelProperty("应用链接")
        private String manageUrl;
        @ApiModelProperty("应用类型 1:独立应用 2:功能应用")
        private Integer appType;
        @ApiModelProperty("上架状态 0:下架 1:上架")
        private Integer status;
        @ApiModelProperty("是否推荐[0否 1是]")
        private Integer recommend;
        @ApiModelProperty("应用简介")
        private String appIntroEditor;
        @ApiModelProperty("应用详情")
        private String appDetailEditor;
        @ApiModelProperty("应用教程")
        private String appCourseEditor;
        @ApiModelProperty("服务商名称")
        private String serviceName;
        @ApiModelProperty("咨询电话")
        private String hotline;
        @ApiModelProperty("技术咨询电话")
        private String technologyHotline;
        @ApiModelProperty("邮箱")
        private String email;
        @ApiModelProperty("销量")
        private Long salesVolume;
        @ApiModelProperty("价格")
        private String price;

        private List<Dependent> dependents;
    }

    @Data
    public static class Dependent {
        private Long appId;
        private String appName;
    }
}
