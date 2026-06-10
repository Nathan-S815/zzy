package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantNavigationListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GetMerchantCoreAppListQryExe 获取商户已开通核心应用列表
 *
 * @author hy
 * @date 2021/6/3 14:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetMerchantCoreAppListQryExe extends AbstractQryExe<GetMerchantNavigationListQry, SingleResponse<List<GetMerchantCoreAppListQryExe.MerchantAppVO>>> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @Override
    protected SingleResponse<List<MerchantAppVO>> handle(GetMerchantNavigationListQry cmd) {
        List<AppInfo> appInfoList = baseMapperExt.selectUserCoreRootApps(cmd.getUserAware().getMchUserId());
        List<MerchantApp> merchantAppList = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getParentAppId, -1)
                .list();
        Map<Long, MerchantApp> merchantAppMap = merchantAppList.stream().collect(Collectors.toMap(MerchantApp::getAppId, Function.identity()));
        List<MerchantAppVO> merchantVo = appInfoList.stream().filter(x -> x.getStatus().equals(1)).map(x -> {
            MerchantAppVO vo = new MerchantAppVO();
            MerchantAppUrl url = merchantAppUrlService.lambdaQuery()
                    .eq(MerchantAppUrl::getAppId, x.getId())
                    .eq(MerchantAppUrl::getMchId, cmd.getUserAware().getMchId())
                    .one();
            if (BeanUtil.isNotEmpty(url)) {
                vo.setUrl(url.getLoginSubmitUrl());
            }
            vo.setAppId(x.getId());
            vo.setAppName(x.getAppName());
            vo.setLogo(x.getLogo());
            vo.setAppIntroEditor(x.getAppIntroEditor());
            vo.setSsh(x.getSsh());
            vo.setProvider(x.getProvider());
            MerchantApp merchantApp = merchantAppMap.get(x.getId());
            if (Objects.isNull(merchantApp)) {
                return null;
            }
            if (Objects.isNull(merchantApp.getOrderNum())) {
                vo.setOrderNum(999);
            } else {
                vo.setOrderNum(merchantApp.getOrderNum());
            }
            MerchantAppServer appServer = merchantAppServerService.lambdaQuery()
                    .eq(MerchantAppServer::getMerchantId, cmd.getUserAware().getMchId())
                    .eq(MerchantAppServer::getAppId, x.getId())
                    .eq(MerchantAppServer::getParentAppId, merchantApp.getParentAppId())
                    .one();
            if (Objects.nonNull(appServer)) {
                vo.setServerStatus(appServer.getStatus());
                vo.setStartTime(appServer.getStartTime());
                vo.setEndTime(appServer.getEndTime());
            }
            return vo;
        }).filter(Objects::nonNull).sorted(Comparator.comparing(MerchantAppVO::getOrderNum)).collect(Collectors.toList());

        return SingleResponse.of(merchantVo);

       /* List<String> groupIds = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getUserId, cmd.getUserAware().getMchUserId())
                .list().stream().map(BaseGroupMember::getGroupId).collect(Collectors.toList());
        if (groupIds.size() != 0) {
            List<String> appIds = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, groupIds)
                    .list().stream().map(BaseGroup::getCode).collect(Collectors.toList());
            List<MerchantAppVO> merchantVo = appInfoService.lambdaQuery()
                    .in(AppInfo::getId, appIds)
                    .eq(AppInfo::getAppType, 1)
                    .eq(AppInfo::getStatus, 1)
                    .list().stream().map(x -> {
                        MerchantAppVO vo = new MerchantAppVO();
                        MerchantAppUrl url = merchantAppUrlService.lambdaQuery().eq(MerchantAppUrl::getAppId, x.getId()).eq(MerchantAppUrl::getMchId, cmd.getUserAware().getMchId()).one();
                        if (BeanUtil.isNotEmpty(url)) {
                            vo.setUrl(url.getLoginSubmitUrl());
                        }
                        vo.setAppId(x.getId());
                        vo.setAppName(x.getAppName());
                        vo.setLogo(x.getLogo());
                        vo.setAppIntroEditor(x.getAppIntroEditor());
                        vo.setSsh(x.getSsh());
                        return vo;
                    }).collect(Collectors.toList());
            return SingleResponse.of(merchantVo);
        }
        return SingleResponse.buildSuccess();*/
    }

    @Data
    public static class MerchantAppVO {
        private String appName;
        @JsonSerialize(using = MaterialJson.class)
        private Long logo;
        private String appIntroEditor;
        private Long appId;
        private String url;
        private boolean isSkip;
        @ApiModelProperty("免密登录(0:不支持 1:支持)")
        private Integer ssh;
        private String provider;
        private Integer orderNum;

        @ApiModelProperty("服务周期状态 0:已过期 1:正常")
        private Integer serverStatus;

        @ApiModelProperty("服务周期开始时间")
        private Date startTime;

        @ApiModelProperty("服务周期结束时间")
        private Date endTime;
    }
}
