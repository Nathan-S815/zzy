package com.nuwa.app.zeus.command.app.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.param.AppInfoListMallPageParam;
import com.nuwa.infrastructure.zeus.database.app.param.AppInfoPageParam;
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
public class AppMallPageListQryExe extends AbstractQryExe<AppInfoPageQry, SingleResponse<IPage<AppMallPageListQryExe.AppInfoVO>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppSkuInfoService appSkuInfoService;

    @Override
    protected SingleResponse<IPage<AppInfoVO>> handle(AppInfoPageQry cmd) {
        IPage<AppInfoVO> appInfoIPage = appInfoService.paginateAndConvert(new AppInfoListMallPageParam(cmd),AppInfoVO::toVo);
        appInfoIPage.getRecords().stream().forEach(x -> {
            AppSkuInfo sku = appSkuInfoService.lambdaQuery()
                    .eq(AppSkuInfo::getAppId, x.getId())
                    .eq(AppSkuInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                    .orderByAsc(AppSkuInfo::getPrice)
                    .last("limit 1")
                    .one();
            x.setPrice(sku.getNegotiable().equals("1") ? "面议" : sku.getPrice().toString());
        });
        return SingleResponse.of(appInfoIPage);
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

        public static AppInfoVO toVo(AppInfo appInfo){
            AppInfoVO vo = new AppInfoVO();
            BeanUtil.copyProperties(appInfo,vo);
            return vo;
        }
    }
}
