package com.nuwa.zeus.start.api.controller.plat.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.command.mch.OpenMerchantAppCmdExe;
import com.nuwa.app.zeus.command.mch.qry.GetAppTreeQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantOpenedAppIdQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantOpenedAppListQryExe;
import com.nuwa.app.zeus.command.ship.EditMerchantAppSubmitUrlCmdExe;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.OpenMerchantAppCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetAppTreeQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantOpenedAppListQry;
import com.nuwa.client.zeus.dto.clientobject.ship.EditMerchantAppSubmitUrlCmd;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.plat.mch.param.GetMerchantSubGroupParam;
import com.nuwa.zeus.start.api.controller.plat.mch.param.MerchantAllotGroupParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AppController
 *
 * @author hy
 * @date 2021/5/31 14:17
 * @since 1.0.0
 */
@Controller
@RequestMapping("plat/merchant/app")
@Api(tags = {"商户应用管理模块"})
public class PlatMerchantAppController {

    @Autowired
    private OpenMerchantAppCmdExe openMerchantAppCmdExe;

    @Autowired
    private GetMerchantOpenedAppListQryExe getMerchantOpenedAppListQryExe;

    @Autowired
    private GetAppTreeQryExe getAppTreeQryExe;

    @Autowired
    private EditMerchantAppSubmitUrlCmdExe editMerchantAppSubmitUrlCmdExe;

    @Autowired
    private GetMerchantOpenedAppIdQryExe getMerchantOpenedAppIdQryExe;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @LogRecordAnnotation(
            fail = "开通应用失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "开通应用成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "商户开通应用")
    @RequestMapping(value = "open", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse openApp(@Validated @RequestBody OpenMerchantAppCmd cmd, UserAware userAware) {
        return SingleResponse.of(openMerchantAppCmdExe.execute(cmd));
    }

    @ApiOperation(value = "商户分配角色")
    @RequestMapping(value = "allot/group", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse allotGroup(@Validated @RequestBody MerchantAllotGroupParam param, UserAware userAware) {
        BaseUser adminUser = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, param.getMchId())
                .eq(BaseUser::getType, AdminCommonConstant.SUPPER_USER_TYPE)
                .one();
        Assert.notNull(adminUser, "商户主账号为空");

        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();

        List<BaseGroupMember> baseGroupMembers = param.getGroupList().stream().map(x -> {
            BaseGroupMember groupMember = new BaseGroupMember();
            groupMember.setGroupId(x + "");
            groupMember.setParentGroupId(baseGroup.getId().longValue());
            groupMember.setUserId(adminUser.getId() + "");
            groupMember.setCreateHost(param.getUserAware().getHostIp());
            groupMember.setDescription("分配角色");
            groupMember.setCreateUserId(param.getUserAware().getMchUserId() + "");
            groupMember.setCreateUserName(param.getUserAware().getUserName());
            groupMember.setCreateTime(new Date());
            return groupMember;
        }).collect(Collectors.toList());

        List<Integer> userIds = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, param.getMchId())
                .list().stream().map(BaseUser::getId).collect(Collectors.toList());

        if (userIds.size() > 0) {
            baseGroupMemberService
                    .lambdaQuery()
                    .in(BaseGroupMember::getUserId, userIds)
                    .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                    .list()
                    .forEach(x -> {
                        BaseGroup group = baseGroupService.getById(x.getGroupId());
                        if (group.getGroupType().equals(3)) {
                            baseGroupMemberService.removeById(x.getId());
                        }
                    });
        }

        boolean saveBatch = baseGroupMemberService.saveBatch(baseGroupMembers);
        Assert.isTrue(saveBatch, "保存商户账号对应角色失败");
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取商戶已分配角色")
    @RequestMapping(value = "listMerchantAllotGroup", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse listMerchantAllotGroup(@Validated GetMerchantSubGroupParam param, UserAware userAware) {
        BaseUser adminUser = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, param.getMchId())
                .eq(BaseUser::getType, AdminCommonConstant.SUPPER_USER_TYPE)
                .one();
        Assert.notNull(adminUser, "商户主账号为空");

        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();
        List<BaseGroupMember> userGroupList = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                .eq(BaseGroupMember::getUserId, adminUser.getId())
                .list();

        List<BaseGroup> groupList = new ArrayList<>();

        List<String> groupIds = userGroupList.stream().map(BaseGroupMember::getGroupId).collect(Collectors.toList());
        if (groupIds.size() > 0) {
            groupList = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, groupIds)
                    .eq(BaseGroup::getParentId, baseGroup.getId())
                    .eq(BaseGroup::getGroupType, 3)
                    .list();
        }
        return SingleResponse.of(groupList);
    }

    @ApiOperation(value = "获取已开通应用列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GetMerchantOpenedAppListQryExe.MerchantAppVO>> openedList(@Validated GetMerchantOpenedAppListQry cmd, UserAware userAware) {
        return getMerchantOpenedAppListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取已开通应用Id列表")
    @RequestMapping(value = "list/id", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<Long>> openedListIds(@Validated GetMerchantOpenedAppListQry cmd, UserAware userAware) {
        return getMerchantOpenedAppIdQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取商户已开通应用Tree")
    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AppTree>> treeAppList(@Validated GetAppTreeQry cmd, UserAware userAware) {
        return getAppTreeQryExe.execute(cmd);
    }

    @LogRecordAnnotation(
            fail = "修改应用链接失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "修改应用链接成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.id}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "修改商户应用app登录提交链接")
    @RequestMapping(value = "editMerchantAppSubmitUrl", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse editMerchantAppSubmitUrl(@Validated @RequestBody EditMerchantAppSubmitUrlCmd cmd, UserAware userAware) {
        return SingleResponse.of(editMerchantAppSubmitUrlCmdExe.execute(cmd));
    }
}
