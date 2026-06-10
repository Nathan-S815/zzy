package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildPermissionTreeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
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
public class MerchantChildPermissionTreeQryExe extends AbstractQryExe<MerchantChildPermissionTreeCmd, SingleResponse<List<MerchantChildPermissionTreeQryExe.MerchantChildTree>>> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    protected SingleResponse<List<MerchantChildTree>> handle(MerchantChildPermissionTreeCmd cmd) {
        List<MerchantChildTree> treeList = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantApp::getStatus, 1).list().stream().map(x -> {
                    MerchantChildTree tree = new MerchantChildTree();
                    tree.setId(x.getAppId().intValue());
                    tree.setParentId(x.getParentAppId().intValue());
                    tree.setText(x.getAppName());
                    tree.setKeyId(x.getId());
                    Long appId = x.getAppId();
                    AppInfo appInfo = appInfoService.getById(x.getAppId());
                    if (appInfo.getStatus().equals(0)) {
                        return null;
                    }
                    BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, appId).one();
                    Long parentAppId = x.getParentAppId();
                    long parentGroupId = -1L;
                    if (!parentAppId.equals(-1L)) {
                        BaseGroup parentBaseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, parentAppId).one();
                        parentGroupId = parentBaseGroup.getId().longValue();
                    }
                    Integer count = baseGroupMemberService.lambdaQuery()
                            .eq(BaseGroupMember::getUserId, cmd.getUserId())
                            .eq(BaseGroupMember::getGroupId, baseGroup.getId())
                            .eq(BaseGroupMember::getParentGroupId, parentGroupId)
                            .count();
                    tree.setIsCheck(count > 0 ? 1 : 0);
                    return tree;
                }).filter(Objects::nonNull).collect(Collectors.toList());

        List<MerchantChildTree> tree = TreeUtil.bulid(treeList, -1);
        return SingleResponse.of(tree);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MerchantChildTree extends TreeNode {
        private String text;

        @ApiModelProperty("0未选中 1选中")
        private Integer isCheck = 0;

        @ApiModelProperty("唯一id")
        private Long keyId;
    }
}
