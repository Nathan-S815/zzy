package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetAppTreeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
public class GetAppTreeQryExe extends AbstractQryExe<GetAppTreeQry, SingleResponse<List<AppTree>>> {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Override
    protected SingleResponse<List<AppTree>> handle(GetAppTreeQry cmd) {
        List<AppTree> trees = baseMapperExt.getAppTree(cmd.getMerchantId()).stream().map(x -> {
            AppTree tree = new AppTree();
            BeanUtil.copyProperties(x, tree);
            tree.setChecked("1".equals(x.get("checked").toString()));
            return tree;
        }).collect(Collectors.toList());
        return SingleResponse.of(TreeUtil.bulid(trees, -1));

    }
}
