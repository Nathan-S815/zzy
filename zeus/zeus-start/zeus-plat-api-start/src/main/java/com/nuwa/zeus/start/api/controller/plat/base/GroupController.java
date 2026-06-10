package com.nuwa.zeus.start.api.controller.plat.base;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.command.base.CreateBaseGroupCmdExe;
import com.nuwa.app.zeus.command.base.ModifyBaseGroupCmdExe;
import com.nuwa.app.zeus.command.base.qry.GetBaseGroupByIdQryExe;
import com.nuwa.app.zeus.command.base.qry.GetBaseGroupPageQryExe;
import com.nuwa.app.zeus.service.GroupBiz;
import com.nuwa.app.zeus.service.dto.ModifyMenuAuthorityDTO;
import com.nuwa.app.zeus.vo.AuthorityMenuTree;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetBaseGroupByIdQry;
import com.nuwa.client.zeus.dto.clientobject.base.CreateBaseGroupCmd;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyBaseGroupCmd;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseGroupPageQry;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.plat.base.param.ModifyMenuAuthorityParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("group")
@Api(tags = {"群组模块"})
public class GroupController {

    @Autowired
    private GroupBiz groupBiz;

    @Autowired
    private GetBaseGroupByIdQryExe getBaseGroupByIdQryExe;

    @Autowired
    private GetBaseGroupPageQryExe getBaseGroupPageQryExe;

    @Autowired
    private CreateBaseGroupCmdExe createBaseGroupCmdExe;

    @Autowired
    private ModifyBaseGroupCmdExe modifyBaseGroupCmdExe;

    @ApiOperation(value = "获取单个群组")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<BaseGroup> get(@PathVariable Integer id) {
        return getBaseGroupByIdQryExe.execute(new GetBaseGroupByIdQry(id));
    }

    @ApiOperation(value = "分页查询")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<BaseGroup>> page(BaseGroupPageQry pageQry) {
        return getBaseGroupPageQryExe.execute(pageQry);
    }

    @LogRecordAnnotation(
            fail = "创建分组失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "创建分组成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.code}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "创建新分组")
    //  @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse create(@RequestBody @Validated CreateBaseGroupCmd cmd) {
        return createBaseGroupCmdExe.execute(cmd);
    }

    @LogRecordAnnotation(
            fail = "修改分组失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "修改分组成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.id}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "修改分组")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@RequestBody @Validated ModifyBaseGroupCmd cmd) {
        return modifyBaseGroupCmdExe.execute(cmd);
    }

    @LogRecordAnnotation(
            fail = "修改分组失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "修改分组成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#id}}",
            detail = "{{#param.toJson()}}")
    @ApiOperation(value = "分组菜单批量修改")
    //@RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyMenuAuthority(@PathVariable int id, @RequestBody ModifyMenuAuthorityDTO param) {
        groupBiz.modifyAuthorityMenu(id, param);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "分组分配资源权限")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}/authority/element/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse addElementAuthority(@PathVariable int id, int menuId, int elementId) {
        groupBiz.modifyAuthorityElement(id, menuId, elementId);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "分组移除资源权限")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}/authority/element/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse removeElementAuthority(@PathVariable int id, int menuId, int elementId) {
        groupBiz.removeAuthorityElement(id, menuId, elementId);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取分组下的菜单列表")
    // @RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable int id) {
        return SingleResponse.of(groupBiz.getAuthorityMenu(id));
    }

    @ApiOperation(value = "获取分组下的权限列表")
    //@RequiresRoles(AdminCommonConstant.ADMIN_ROLE)
    @RequestMapping(value = "/{id}/authority/element", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<Integer>> getElementAuthority(@PathVariable int id) {
        return SingleResponse.of(groupBiz.getAuthorityElement(id));
    }

}
