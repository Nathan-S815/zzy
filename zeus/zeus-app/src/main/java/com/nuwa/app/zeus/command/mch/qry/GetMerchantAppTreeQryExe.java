package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetAppTreeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GetMerchantAppTreeQryExe 获取商户已开通的应用树
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetMerchantAppTreeQryExe extends AbstractQryExe<GetAppTreeQry, SingleResponse<List<AppTree>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Override
    protected SingleResponse<List<AppTree>> handle(GetAppTreeQry cmd) {
        List<AppInfo> appList = appInfoService.lambdaQuery()
                .eq(AppInfo::getStatus, 1)
                .eq(AppInfo::getAppType, 1)
                .list();
        List<AppTree> treeList = appList.stream().filter(x -> this.checkAppEnabled(x.getId(), 0L, cmd.getUserAware().getMchId())).map(x -> {
            AppTree tree = new AppTree();
            tree.setIcon(x.getLogo() + "");
            tree.setText(x.getAppName());
            tree.setId(x.getId().intValue());
            tree.setChildren(new ArrayList<TreeNode>());
            List<Long> ids = appDependentService.lambdaQuery()
                    .eq(AppDependent::getDependentAppId, x.getId())
                    .list()
                    .stream()
                    .map(AppDependent::getAppId)
                    .filter(appId -> this.checkAppEnabled(appId, x.getId(), cmd.getUserAware().getMchId()))
                    .collect(Collectors.toList());
            if (ids.size() > 0) {
                appInfoService.lambdaQuery().eq(AppInfo::getStatus, 1).in(AppInfo::getId, ids).list().forEach(app -> {
                    AppTree treeNode = new AppTree();
                    treeNode.setId(app.getId().intValue());
                    treeNode.setParentId(x.getId().intValue());
                    treeNode.setIcon(app.getLogo() + "");
                    treeNode.setText(app.getAppName());
                    tree.add(treeNode);
                });
            }
            return tree;
        }).collect(Collectors.toList());
        return SingleResponse.of(treeList);
    }

    private Boolean checkAppEnabled(Long appId, Long parentAppId, Long mchId) {
        return merchantAppService.lambdaQuery()
                .eq(MerchantApp::getParentAppId, parentAppId)
                .eq(MerchantApp::getAppId, appId)
                .eq(MerchantApp::getMerchantId, mchId).count() > 0;
    }
}
