package com.nuwa.app.zeus.command.app.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.app.co.AppSkuInfoCO;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.param.AppInfoPageParam;
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class AppInfoQryExe extends AbstractQryExe<AppInfoQry, SingleResponse<AppInfoQryExe.AppInfoVO>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppSkuInfoService appSkuInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Override
    protected SingleResponse<AppInfoVO> handle(AppInfoQry cmd) {
        AppInfo appInfo = appInfoService.getById(cmd.getAppId());
        AppInfoVO vo = new AppInfoVO();
        BeanUtil.copyProperties(appInfo, vo);
        List<AppSkuInfo> skuList = appSkuInfoService.lambdaQuery().eq(AppSkuInfo::getAppId, appInfo.getId()).eq(AppSkuInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode()).list();
        vo.setSku(skuList);
        List<Long> dependentAppId = appDependentService.lambdaQuery().eq(AppDependent::getAppId, cmd.getAppId()).list().stream().map(AppDependent::getDependentAppId).collect(Collectors.toList());
        vo.setDependentAppId(dependentAppId);
        return SingleResponse.of(vo);
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
        @ApiModelProperty("应用类型 1:独立应用 2:功能应用")
        private Integer appType;
        @ApiModelProperty("免密登录(0:不支持 1:支持)")
        private Integer ssh;
        @ApiModelProperty("私有化部署(0:不支持 1:支持)")
        private Integer privatization;
        @ApiModelProperty("版本号")
        private String version;
        @ApiModelProperty("版本名称")
        private String versionName;
        @ApiModelProperty("应用简介")
        private String appIntroEditor;
        @ApiModelProperty("依赖的应用ID")
        private List<Long> dependentAppId;

        private List<AppSkuInfo> sku;
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
        @ApiModelProperty("上架状态 0:下架 1:上架")
        private Integer status;
        @ApiModelProperty("应用链接")
        private String manageUrl;
    }
}
