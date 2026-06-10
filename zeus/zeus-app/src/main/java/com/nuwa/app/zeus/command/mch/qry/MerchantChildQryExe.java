package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.nuwa.app.zeus.util.TreeUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.param.MerchantChildPageParam;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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
public class MerchantChildQryExe extends AbstractQryExe<MerchantChildQry, SingleResponse<MerchantChildQryExe.MerchantChildVO>> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private AppInfoService appInfoService;

    @Override
    protected SingleResponse<MerchantChildVO> handle(MerchantChildQry cmd) {
        MerchantChildVO vo = new MerchantChildVO();
        BaseUser user = baseUserService.getById(cmd.getId());
        BeanUtil.copyProperties(user, vo);

        List<AppInfo> appList = appInfoService.lambdaQuery()
                .eq(AppInfo::getStatus, 1)
                .eq(AppInfo::getAppType, 1)
                .list();
        /*List<MerchantChildTree> treeList = appList.stream().filter(x -> this.checkAppEnabled(x.getId(), -1L, cmd.getUserAware().getMchId())).map(x -> {
            MerchantChildTree tree = new MerchantChildTree();
            tree.setText(x.getAppName());
            tree.setId(x.getId().intValue());
            tree.setKeyId(x.getId());
            BaseGroup group = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, tree.getId()).one();
            Integer count = baseGroupMemberService.lambdaQuery().eq(BaseGroupMember::getGroupId, group.getId()).eq(BaseGroupMember::getUserId, user.getId()).count();
            if (count>0){
                tree.setIsCheck(1);
            }
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
                    MerchantChildTree treeNode = new MerchantChildTree();
                    treeNode.setId(app.getId().intValue());
                    treeNode.setParentId(x.getId().intValue());
                    treeNode.setKeyId(x.getId());
                    treeNode.setText(app.getAppName());
                        AppDependent dependent = appDependentService.lambdaQuery().eq(AppDependent::getAppId, treeNode.getId()).eq(AppDependent::getDependentAppId, treeNode.getParentId()).one();
                        if (BeanUtil.isNotEmpty(dependent)){
                            BaseGroup groupChild = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, treeNode.getId()).one();
                            Integer countChild = baseGroupMemberService.lambdaQuery().eq(BaseGroupMember::getGroupId, groupChild.getId()).eq(BaseGroupMember::getUserId, user.getId()).count();
                            if (countChild>0){
                                treeNode.setIsCheck(1);
                            }
                        }
                    tree.add(treeNode);
                });
            }
            return tree;
        }).collect(Collectors.toList());*/

     //   List<MerchantChildTree> tree = TreeUtil.bulid(treeList, 0);

     //   vo.setGroup(tree);

        return SingleResponse.of(vo);
    }

    private Boolean checkAppEnabled(Long appId, Long parentAppId, Long mchId) {
        return merchantAppService.lambdaQuery()
                .eq(MerchantApp::getParentAppId, parentAppId)
                .eq(MerchantApp::getAppId, appId)
                .eq(MerchantApp::getMerchantId, mchId).count() > 0;
    }

    @Data
    public static class MerchantChildVO {
        @ApiModelProperty("主键")
        private Integer id;
        @ApiModelProperty("用户名")
        private String username;
        @ApiModelProperty("姓名")
        private String name;
        @ApiModelProperty("手机号")
        private String mobilePhone;
        @ApiModelProperty("邮箱")
        private String email;
        @ApiModelProperty("状态[0正常 1禁用]")
        private String status;
        @ApiModelProperty("登录次数")
        private Long loginTimes;
        @ApiModelProperty("最后登录IP")
        private String lastLoginIp;
        @ApiModelProperty("最后登录时间")
        private Date lastLoginTime;
        @ApiModelProperty("创建时间")
        private Date createTime;

      //  private List<MerchantChildTree> group;
    }

    @Data
    public static class MerchantChildTree extends TreeNode {

        private String text;
        @ApiModelProperty("0未选中 1选中")
        private Integer isCheck = 0;

        private Long keyId;
    }

}
