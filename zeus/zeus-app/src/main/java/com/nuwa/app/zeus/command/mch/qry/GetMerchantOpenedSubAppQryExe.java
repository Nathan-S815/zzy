package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.mch.vo.SortAppInfo;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantSubAppQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GetMerchantOpenedSubAppQryExe 获取商户系统应用的子应用
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetMerchantOpenedSubAppQryExe extends AbstractQryExe<GetMerchantSubAppQry, SingleResponse<List<SortAppInfo>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @Override
    protected SingleResponse<List<SortAppInfo>> handle(GetMerchantSubAppQry cmd) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, cmd.getAppId()).one();
        List<MerchantApp> merchantAppList = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getParentAppId, cmd.getAppId())
                .list();
        Map<Long, MerchantApp> merchantAppMap = merchantAppList.stream().collect(Collectors.toMap(MerchantApp::getAppId, Function.identity()));
        List<AppInfo> appInfos = baseMapperExt.selectUserSubApps(cmd.getUserAware().getMchUserId(), baseGroup.getId().longValue());
        List<SortAppInfo> sortAppInfoList = appInfos.stream().map(x -> {
            SortAppInfo sortAppInfo = new SortAppInfo();
            BeanUtil.copyProperties(x, sortAppInfo);
            MerchantApp merchantApp = merchantAppMap.get(x.getId());
            if (Objects.nonNull(merchantApp)) {
                if (Objects.isNull(merchantApp.getOrderNum())) {
                    sortAppInfo.setOrderNum(999);
                } else {
                    sortAppInfo.setOrderNum(merchantApp.getOrderNum());
                }
            }

            return sortAppInfo;
        }).sorted(Comparator.comparing(SortAppInfo::getOrderNum)).collect(Collectors.toList());
        return SingleResponse.of(sortAppInfoList);
    }

   /* @Override
    protected SingleResponse<List<AppInfo>> handle(GetMerchantSubAppQry cmd) {
        List<String> groupIds = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getUserId, cmd.getUserAware().getMchUserId())
                .list().stream().map(BaseGroupMember::getGroupId).collect(Collectors.toList());
        if (groupIds.size()!=0) {
            List<String> appIds = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, groupIds)
                    .list().stream().map(BaseGroup::getCode).collect(Collectors.toList());
            List<Long> dependentsAppIds = appDependentService.lambdaQuery()
                    .eq(AppDependent::getDependentAppId, cmd.getAppId())
                    .in(AppDependent::getAppId, appIds)
                    .list().stream().map(AppDependent::getAppId).collect(Collectors.toList());
            if (dependentsAppIds.size()!=0){
                List<AppInfo> apps = appInfoService.lambdaQuery()
                        .in(AppInfo::getId, dependentsAppIds)
                        .eq(AppInfo::getStatus, 1)
                        .eq(AppInfo::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                        .list();
                return SingleResponse.of(apps);
            }
        }
        return SingleResponse.buildSuccess();
    }*/
}
