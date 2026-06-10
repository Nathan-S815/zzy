package com.nuwa.zeus.start.api.controller.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.app.zeus.command.mch.*;
import com.nuwa.app.zeus.command.mch.qry.*;
import com.nuwa.client.zeus.dto.clientobject.mch.*;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.*;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.zeus.start.api.controller.merchant.param.*;
import com.nuwa.zeus.start.api.util.IpAddressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MerchantAppController
 *
 * @author hy
 * @date 2021/6/9 14:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/child")
@Api(tags = {"子账号管理模块"})
public class MerchantChildController {

    @Autowired
    private CreateMerchantChildCmdExe createMerchantChildCmdExe;

    @Autowired
    private ModifyMerchantChildCmdExe modifyMerchantChildCmdExe;

    @Autowired
    private DeleteMerchantChildCmdExe deleteMerchantChildCmdExe;

    @Autowired
    private MerchantChildPageQryExe merchantChildPageQryExe;

    @Autowired
    private MerchantChildQryExe merchantChildQryExe;

    @Autowired
    private EnableMerchantChildCmdExe enableMerchantChildCmdExe;

    @Autowired
    private ForbidMerchantChildCmdExe forbidMerchantChildCmdExe;

    @Autowired
    private MerchantChildPermissionTreeQryExe merchantChildPermissionTreeQryExe;

    @Autowired
    private MerchantChildElementTreeQryExe merchantChildElementTreeQryExe;

    @Autowired
    private MerchantChildAppTreeQryExe merchantChildAppTreeQryExe;

    @Autowired
    private EditMerchantChildElementCmdExe editMerchantChildElementCmdExe;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @ApiOperation(value = "新建子账号")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse create(@RequestBody @Validated CreateMerchantChildCmd cmd, UserAware userAware, HttpServletRequest request) {
        String ipAddress = IpAddressUtil.getIpAddress(request);
        cmd.setIp(ipAddress);
        return createMerchantChildCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改子账号")
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@RequestBody @Validated ModifyMerchantChildCmd cmd, UserAware userAware, HttpServletRequest request) {
        String ipAddress = IpAddressUtil.getIpAddress(request);
        cmd.setIp(ipAddress);
        return modifyMerchantChildCmdExe.execute(cmd);
    }

    @ApiOperation(value = "子账号-分配应用")
    @RequestMapping(value = "subAccount/allotApp", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse subAccountAllotApp(@RequestBody @Validated SubAccountAllotAppParam cmd, UserAware userAware, HttpServletRequest request) {
        BaseGroupMember baseGroupMember = new BaseGroupMember();
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, cmd.getAppId()).one();
        baseGroupMember.setGroupId(baseGroup.getId() + "");
        if (!baseGroup.getParentId().equals(-1)) {
            BaseGroup patentBaseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getId, baseGroup.getParentId()).one();
            baseGroupMember.setParentGroupId(patentBaseGroup.getId().longValue());
        }
        baseGroupMember.setUserId(cmd.getUserId() + "");
        baseGroupMember.setDescription("开通应用");
        baseGroupMember.setCreateUserId(cmd.getUserId() + "");
        baseGroupMember.setCreateUserName(userAware.getUserName());
        baseGroupMember.setCreateHost(userAware.getHostIp());
        baseGroupMember.insert();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "子账号-移除应用")
    @RequestMapping(value = "subAccount/removeApp", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse subAccountRemoveApp(@RequestBody @Validated SubAccountRemovedAppParam cmd, UserAware userAware, HttpServletRequest request) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, cmd.getAppId()).one();
        baseGroupMemberService.lambdaUpdate()
                .eq(BaseGroupMember::getUserId, cmd.getUserId())
                .eq(BaseGroupMember::getGroupId, baseGroup.getId())
                .remove();

        baseGroupMemberService.lambdaUpdate()
                .eq(BaseGroupMember::getUserId, cmd.getUserId())
                .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                .remove();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "删除子账号")
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse delete(@Validated DeleteMerchantChildCmd cmd, UserAware userAware) {
        return deleteMerchantChildCmdExe.execute(cmd);
    }

    @ApiOperation(value = "子账号分页查询")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<BaseUser>> page(@Validated MerchantChildPageQry cmd, UserAware userAware) {
        return merchantChildPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "子账号详情查询")
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantChildQryExe.MerchantChildVO> getById(@Validated MerchantChildQry cmd, UserAware userAware) {
        return merchantChildQryExe.execute(cmd);
    }

    @ApiOperation(value = "开通子账号")
    @RequestMapping(value = "enable", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse enable(@Validated EnableMerchantChildCmd cmd, UserAware userAware, HttpServletRequest request) {
        return enableMerchantChildCmdExe.execute(cmd);
    }

    @ApiOperation(value = "禁用子账号")
    @RequestMapping(value = "forbid", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse forbid(@Validated ForbidMerchantChildCmd cmd, UserAware userAware, HttpServletRequest request) {
        return forbidMerchantChildCmdExe.execute(cmd);
    }

    @ApiOperation(value = "子账号应用权限")
    @RequestMapping(value = "permission", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse permission(@Validated MerchantChildPermissionTreeCmd cmd, UserAware userAware) {
        return merchantChildPermissionTreeQryExe.execute(cmd);
    }

    @ApiOperation(value = "查询子账号开通应用树")
    @RequestMapping(value = "app", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse app(@Validated MerchantChildAppTreeCmd cmd, UserAware userAware) {
        return merchantChildAppTreeQryExe.execute(cmd);
    }

    @ApiOperation(value = "查询子账号菜单权限")
    @RequestMapping(value = "element", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse element(@Validated MerchantChildElementTreeCmd cmd, UserAware userAware) {
        return merchantChildElementTreeQryExe.execute(cmd);
    }

    @ApiOperation(value = "子账号编辑菜单权限")
    @RequestMapping(value = "element/edit", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse elementEdit(@Validated @RequestBody EditMerchantChildElementCmd cmd, UserAware userAware) {
        return editMerchantChildElementCmdExe.execute(cmd);
    }

    @ApiOperation(value = "获取指定商戶指定应用所拥有的子角色")
    @RequestMapping(value = "listMerchantAppSubGroup", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse listMerchantAppSubGroup(@Validated GetAppSubGroupParam param, UserAware userAware) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();
        List<BaseGroupMember> userGroupList = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                .eq(BaseGroupMember::getUserId, userAware.getMchUserId())
                .list();
        List<String> groupIds = userGroupList.stream().map(BaseGroupMember::getGroupId).collect(Collectors.toList());
        List<BaseGroup> groupList;
        if (groupIds.size() > 0) {
            groupList = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, groupIds)
                    .eq(BaseGroup::getParentId, baseGroup.getId())
                    .eq(BaseGroup::getStatus, "on")
                    .eq(BaseGroup::getGroupType, 3)
                    .list();
        } else {
            groupList = new ArrayList<>();
        }
        return SingleResponse.of(groupList);
    }

    @ApiOperation(value = "子账号分配角色")
    @RequestMapping(value = "allot/group", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse allotGroup(@Validated @RequestBody MerchantSubUserAllotGroupParam param, UserAware userAware) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();
        List<BaseGroupMember> baseGroupMembers = param.getGroupList().stream().map(x -> {
            BaseGroupMember groupMember = new BaseGroupMember();
            groupMember.setGroupId(x + "");
            groupMember.setParentGroupId(baseGroup.getId().longValue());
            groupMember.setUserId(param.getUserId() + "");
            groupMember.setCreateHost(param.getUserAware().getHostIp());
            groupMember.setDescription("分配角色");
            groupMember.setCreateUserId(param.getUserAware().getMchUserId() + "");
            groupMember.setCreateUserName(param.getUserAware().getUserName());
            groupMember.setCreateTime(new Date());
            return groupMember;
        }).collect(Collectors.toList());

        baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getUserId, param.getUserId())
                .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                .list().forEach(x -> {
                    BaseGroup group = baseGroupService.getById(x.getGroupId());
                    if (group.getGroupType().equals(3)) {
                        baseGroupMemberService.removeById(x.getId());
                    }
                });
        boolean saveBatch = baseGroupMemberService.saveBatch(baseGroupMembers);
        Assert.isTrue(saveBatch, "保存商户账号对应角色失败");
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取指定子账号已分配角色")
    @RequestMapping(value = "listUserAllotGroup", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse getUserAllotGroupList(@Validated GetMerchantSubUserGroupParam param, UserAware userAware) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();
        List<BaseGroupMember> userGroupList = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                .eq(BaseGroupMember::getUserId, param.getUserId())
                .list();
        List<String> groupIds = userGroupList.stream().map(BaseGroupMember::getGroupId).collect(Collectors.toList());
        List<BaseGroup> groupList = new ArrayList<>();
        if (groupIds.size() > 0) {
            groupList = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, groupIds)
                    .eq(BaseGroup::getParentId, baseGroup.getId())
                    .eq(BaseGroup::getStatus, "on")
                    .eq(BaseGroup::getGroupType, 3)
                    .list();
        }
        return SingleResponse.of(groupList);
    }

}
