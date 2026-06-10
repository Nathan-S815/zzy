package com.nuwa.zeus.start.api.controller.plat.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.command.app.*;
import com.nuwa.app.zeus.command.app.qry.*;
import com.nuwa.app.zeus.service.GroupBiz;
import com.nuwa.app.zeus.vo.AuthorityMenuTree;
import com.nuwa.client.zeus.dto.clientobject.app.*;
import com.nuwa.client.zeus.dto.clientobject.app.qry.*;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.MerchantAppPageVO;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.zeus.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.plat.app.param.ModifyMenuOrderNumParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.InnerAppCreateGroupParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.InnerAppModifyGroupParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.InnerAppModifyGroupStatusParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * AppController 应用管理
 *
 * @author hy
 * @date 2021/5/31 14:17
 * @since 1.0.0
 */
@Controller
@RequestMapping("app")
@Api(tags = {"应用管理"})
public class AppManageController {

    @Autowired
    private CreateAppCmdExe createAppCmdExe;

    @Autowired
    private AddAppMenuCmdExe addAppMenuCmdExe;

    @Autowired
    private ModifyAppMenuCmdExe modifyAppMenuCmdExe;

    @Autowired
    private AddAppMenuElementCmdExe addAppMenuElementCmdExe;

    @Autowired
    private ModifyAppMenuElementCmdExe modifyAppMenuElementCmdExe;

    @Autowired
    private GroupBiz groupBiz;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private ModifyAppCmdExe modifyAppCmdExe;

    @Autowired
    private AppInfoPageQryExe appInfoPageQryExe;

    @Autowired
    private JoinAppInfoPageQryExe joinAppInfoPageQryExe;

    @Autowired
    private AppInfoQryExe appInfoQryExe;

    @Autowired
    private JoinAppInfoListQryExe joinAppInfoListQryExe;

    @Autowired
    private AddAppPageInfoCmdExe addAppPageInfoCmdExe;

    @Autowired
    private ModifyAppPageInfoCmdExe modifyAppPageInfoCmdExe;

    @Autowired
    private AppPageInfoQryExe appPageInfoQryExe;

    @Autowired
    private AppPageInfoListQryExe appPageInfoListQryExe;

    @Autowired
    private OnAppCmdExe onAppCmdExe;

    @Autowired
    private OffAppCmdExe offAppCmdExe;

    @Autowired
    private DeleteAppCmdExe deleteAppCmdExe;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseMenuService baseMenuService;

    @IgnoreAuth
    @ApiOperation(value = "分页查询Test")
    @RequestMapping(value = "/pageTest", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantAppPageVO>> pageTest(AppInfoPageJoinQry qry) {
        return joinAppInfoPageQryExe.execute(qry);
    }

    @IgnoreAuth
    @ApiOperation(value = "testList")
    @RequestMapping(value = "/testList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MerchantAppPageVO>> testList(AppInfoGetListJoinQry qry) {
        return joinAppInfoListQryExe.execute(qry);
    }

    @LogRecordAnnotation(fail = "创建应用失败，原因：「{{#_errorMsg}}」", category = LogCategoryType.PLAT, success = "创建应用成功", prefix = LogRecordType.MERCHANT_APP, bizNo = "{{#cmd.merchantId}}", detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "创建应用")
//    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse create(@Validated @RequestBody CreateAppCmd cmd, UserAware userAware) {
        return createAppCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改应用")
//    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@Validated @RequestBody ModifyAppCmd cmd, UserAware userAware) {
        return modifyAppCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除应用")
//    @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse delete(@Validated DeleteAppCmd cmd, UserAware userAware) {
        return deleteAppCmdExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<AppInfo>> page(AppInfoPageQry cmd, UserAware userAware) {
        return appInfoPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "查询详情")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<AppInfoQryExe.AppInfoVO> getById(AppInfoQry cmd, UserAware userAware) {
        return appInfoQryExe.execute(cmd);
    }

    @ApiOperation(value = "上架")
    @RequestMapping(value = "/on", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse on(OnAppCmd cmd, UserAware userAware) {
        return onAppCmdExe.execute(cmd);
    }

    @ApiOperation(value = "下架")
    @RequestMapping(value = "/off", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse off(OffAppCmd cmd, UserAware userAware) {
        return offAppCmdExe.execute(cmd);
    }

    @ApiOperation(value = "应用增加菜单")
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse addMenu(@Validated @RequestBody AddAppMenuCmd cmd, UserAware userAware) {
        return SingleResponse.of(addAppMenuCmdExe.execute(cmd));
    }

    @ApiOperation(value = "应用修改菜单")
    @RequestMapping(value = "/modifyMenu", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyMenu(@Validated @RequestBody ModifyAppMenuCmd cmd, UserAware userAware) {
        return SingleResponse.of(modifyAppMenuCmdExe.execute(cmd));
    }

    @ApiOperation(value = "应用菜单增加权限")
    @RequestMapping(value = "/menu/element/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse addMenuElement(@Validated @RequestBody AddAppMenuElementCmd cmd, UserAware userAware) {
        return SingleResponse.of(addAppMenuElementCmdExe.execute(cmd));
    }

    @ApiOperation(value = "应用菜单修改权限点")
    @RequestMapping(value = "/menu/element/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyMenuElement(@Validated @RequestBody ModifyAppMenuElementCmd cmd, UserAware userAware) {
        return SingleResponse.of(modifyAppMenuElementCmdExe.execute(cmd));
    }

    @ApiOperation(value = "应用增加页面地址")
    @RequestMapping(value = "/addPage", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse addPage(@Validated @RequestBody AddAppMenuCmd cmd, UserAware userAware) {
        return SingleResponse.of(addAppMenuCmdExe.execute(cmd));
    }

    @ApiOperation(value = "获取应用菜单树")
    @RequestMapping(value = "/{appId}/menuTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable int appId) {
        return SingleResponse.of(TreeUtil.bulid(groupBiz.getAppAuthorityMenu(appId), AdminCommonConstant.ROOT));
    }

    @ApiOperation(value = "获取指定菜单权限列表")
    @RequestMapping(value = "/menu/element", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseElement>> getAuthorityElement(String menuId) {
        List<BaseElement> elements = baseMapperExt.selectAuthorityMenuElementByMenuId(menuId);
        return SingleResponse.of(elements);
    }

    @ApiOperation(value = "微页面增加页面地址")
    @RequestMapping(value = "/app/addPage", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse addAppPage(@Validated @RequestBody AddAppPageInfoCmd cmd, UserAware userAware) {
        return addAppPageInfoCmdExe.execute(cmd);
    }

    @ApiOperation(value = "微页面修改页面地址")
    @RequestMapping(value = "/app/modifyPage", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyAppPage(@Validated @RequestBody ModifyAppPageInfoCmd cmd, UserAware userAware) {
        return modifyAppPageInfoCmdExe.execute(cmd);
    }

    @ApiOperation(value = "微页面查询页面地址")
    @RequestMapping(value = "/app/getPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<AppPageInfo> getPage(AppPageInfoQry cmd, UserAware userAware) {
        return appPageInfoQryExe.execute(cmd);
    }

    @ApiOperation(value = "微页面查询页面列表")
    @RequestMapping(value = "/app/getList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AppPageInfo>> getList(AppPageInfoListQry cmd, UserAware userAware) {
        return appPageInfoListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取应用拥有的子角色列表")
    @RequestMapping(value = "/{appId}/getAppSubGroup", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseGroup>> listAppSubGroup(@PathVariable int appId, String groupName, String status) {
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, appId).one();
        Integer baseGroupId = baseGroup.getId();
        List<BaseGroup> listGroup = baseGroupService.lambdaQuery()
                .eq(BaseGroup::getParentId, baseGroupId)
                .like(StrUtil.isNotBlank(groupName), BaseGroup::getName, groupName)
                .eq(StrUtil.isNotBlank(status), BaseGroup::getStatus, status)
                .eq(BaseGroup::getGroupType, 3).list();
        return SingleResponse.of(listGroup);
    }

    @ApiOperation(value = "内部应用创建角色")
    @RequestMapping(value = "inner/create_group", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse innerAppCreateGroup(@Validated @RequestBody InnerAppCreateGroupParam param, UserAware userAware) {
        Integer count = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getCode()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9874", "角色编码重复");
        }
        BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getAppId() + "").one();
        Integer baseGroupId = baseGroup.getId();
        //{"adminUrl":"https://b2c-gongying-manage-prod.zhongzhiyou.cn/#/commodity/index","linkType":"inner"}
        JSONObject extJson = new JSONObject();
        extJson.putOpt("adminUrl", param.getAdminUrl());
        extJson.putOpt("linkType", "inner");
        extJson.putOpt("remark", param.getRemark());
        BaseGroup groupNew = new BaseGroup();
        groupNew.setExtend(JSONUtil.toJsonStr(extJson));
        groupNew.setCreateHost(userAware.getHostIp());
        groupNew.setCreateTime(new Date());
        groupNew.setGroupType(3);
        groupNew.setCode(param.getCode());
        groupNew.setName(param.getName());
        groupNew.setParentId(baseGroupId);
        groupNew.setPath(baseGroup.getCode() + "/" + param.getCode());
        groupNew.setStatus("on");
        groupNew.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取应用角色详情")
    @RequestMapping(value = "/app/group/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<BaseGroup> groupGetById(Long id) {
        BaseGroup baseGroup = baseGroupService.getById(id);
        return SingleResponse.of(baseGroup);
    }

    @ApiOperation(value = "内部应用修改角色")
    @RequestMapping(value = "inner/modify_group", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse innerAppModifyGroup(@Validated @RequestBody InnerAppModifyGroupParam param, UserAware userAware) {
        BaseGroup baseGroup = baseGroupService.getById(param.getId());
        BeanUtil.copyProperties(param, baseGroup);
        Integer count = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, param.getCode()).count();
        if (count > 0 && !baseGroup.getCode().equalsIgnoreCase(param.getCode())) {
            return SingleResponse.buildFailure("9874", "角色编码重复");
        }
        JSONObject extJson = new JSONObject();
        extJson.putOpt("adminUrl", param.getAdminUrl());
        extJson.putOpt("linkType", "inner");
        extJson.putOpt("remark", param.getRemark());
        baseGroup.setExtend(JSONUtil.toJsonStr(extJson));
        boolean b = baseGroup.updateById();
        if (b) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "修改角色失败");
    }

    @ApiOperation(value = "内部应用修改角色")
    @RequestMapping(value = "inner/modify_group_status", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyGroupStatus(@Validated @RequestBody InnerAppModifyGroupStatusParam param, UserAware userAware) {
        boolean update = baseGroupService.lambdaUpdate()
                .set(BaseGroup::getStatus, param.getStatus())
                .set(BaseGroup::getCreateTime, new Date())
                .in(BaseGroup::getId, param.getIds()).update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "修改角色状态失败");
    }

    @ApiOperation(value = "修改菜单权重")
    @RequestMapping(value = "menu/modify_order_num", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyMenuOrderNum(@Validated @RequestBody ModifyMenuOrderNumParam param, UserAware userAware) {
        boolean update = baseMenuService.lambdaUpdate()
                .set(BaseMenu::getOrderNum, param.getOrderNum())
                .set(BaseMenu::getUpdateTime, new Date())
                .in(BaseMenu::getId, param.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "修改菜单权重失败");
    }

    public static void main(String[] args) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(AppInfo.class);
        System.out.println(tableInfo);
    }
}
