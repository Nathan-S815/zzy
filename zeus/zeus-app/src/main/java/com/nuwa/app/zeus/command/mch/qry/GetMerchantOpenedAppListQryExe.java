package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.vo.GroupUsers;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantOpenedAppListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GetMerchantOpenedAppListQryExe 获取商户已开通应用列表
 *
 * @author hy
 * @date 2021/6/3 14:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetMerchantOpenedAppListQryExe extends AbstractQryExe<GetMerchantOpenedAppListQry, SingleResponse<List<GetMerchantOpenedAppListQryExe.MerchantAppVO>>> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @Override
    protected SingleResponse<List<MerchantAppVO>> handle(GetMerchantOpenedAppListQry cmd) {
        List<MerchantAppVO> collect = merchantAppService.lambdaQuery().eq(MerchantApp::getMerchantId, cmd.getMerchantId()).list()
                .stream().map(x -> {
                    MerchantAppVO vo = new MerchantAppVO();
                    BeanUtil.copyProperties(x, vo);
                    vo.setParentAppId(x.getParentAppId());
                    MerchantAppUrl url = merchantAppUrlService.lambdaQuery().eq(MerchantAppUrl::getMchId, cmd.getMerchantId()).eq(MerchantAppUrl::getAppId, x.getAppId()).one();
                    if (BeanUtil.isNotEmpty(url)) {
                        vo.setUrl(url.getLoginSubmitUrl());
                    }

                    AppInfo appInfo = appInfoService.getById(x.getAppId());
                    vo.setAppType(appInfo.getAppType());
                    vo.setProvider(appInfo.getProvider());
                    vo.setGroupCount(0);

                    MerchantAppServer appServer = merchantAppServerService.lambdaQuery()
                            .eq(MerchantAppServer::getMerchantId, x.getMerchantId())
                            .eq(MerchantAppServer::getAppId, x.getAppId())
                            .eq(MerchantAppServer::getParentAppId, x.getParentAppId())
                            .one();
                    if (Objects.nonNull(appServer)) {
                        vo.setServerStatus(appServer.getStatus());
                        vo.setStartTime(appServer.getStartTime());
                        vo.setEndTime(appServer.getEndTime());
                    }
                    return vo;
                }).collect(Collectors.toList());
        return SingleResponse.of(collect);
    }

    @Data
    public static class MerchantAppVO {
        @ApiModelProperty("主键")
        private Long id;
        @ApiModelProperty("商户id")
        private Long merchantId;
        @ApiModelProperty("应用名称")
        private String appName;
        @ApiModelProperty("应用id")
        private Long appId;

        @ApiModelProperty("parentAppId")
        private Long parentAppId;

        @ApiModelProperty("url")
        private String url;
        @ApiModelProperty("状态 0:关闭 1:开启")
        private Integer status;
        @ApiModelProperty("应用类型 1:独立应用 2:功能应用")
        private Integer appType;
        private String provider;
        private Integer groupCount;

        @ApiModelProperty("服务周期状态 0:已过期 1:正常")
        private Integer serverStatus;

        @ApiModelProperty("服务周期开始时间")
        private Date startTime;

        @ApiModelProperty("服务周期结束时间")
        private Date endTime;

    }
}
