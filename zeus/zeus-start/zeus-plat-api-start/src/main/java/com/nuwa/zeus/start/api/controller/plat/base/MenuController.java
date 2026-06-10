package com.nuwa.zeus.start.api.controller.plat.base;


import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.vo.AuthorityMenuTree;
import com.nuwa.app.zeus.vo.MenuTree;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.plat.base.param.CreateMenuParam;
import com.nuwa.zeus.start.api.controller.plat.base.param.ModifyMenuParam;
import com.nuwa.app.zeus.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuController
 *
 * @author hy
 * @date 2021/5/25 13:25
 * @since 1.0.0
 */
@Api(tags = {"菜单管理(平台超级管理员)"})
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private BaseMenuService baseMenuService;

    @LogRecordAnnotation(
            fail = "创建菜单失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "创建菜单成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "创建菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse createMenu(@Validated @RequestBody CreateMenuParam param) {
        BaseMenu baseMenu = new BaseMenu();
        BeanUtils.copyProperties(param, baseMenu);
        if (AdminCommonConstant.ROOT == param.getParentId()) {
            baseMenu.setPath("/" + param.getCode());
        } else {
            BaseMenu parent = baseMenuService.getById(param.getParentId());
            baseMenu.setPath(parent.getPath() + "/" + param.getCode());
        }
        boolean insert = baseMenu.insert();
        if (insert) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }

    @LogRecordAnnotation(
            fail = "修改菜单失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "修改菜单成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "修改菜单")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyMenu(@Validated @RequestBody ModifyMenuParam param) {
        BaseMenu baseMenu = baseMenuService.getById(param.getId());
        BeanUtils.copyProperties(param, baseMenu);
        baseMenu.setOrderNum(null);
        if (AdminCommonConstant.ROOT == param.getParentId()) {
            baseMenu.setPath("/" + param.getCode());
        } else {
            BaseMenu parent = baseMenuService.getById(param.getParentId());
            baseMenu.setPath(parent.getPath() + "/" + param.getCode());
        }
        boolean update = baseMenu.updateById();
        if (update) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }

    @ApiOperation(value = "删除菜单")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse remove(@PathVariable(value = "id") Integer id) {
        boolean remove = baseMenuService.removeById(id);
        if (remove) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }

    @ApiOperation(value = "获取菜单")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse getById(@PathVariable(value = "id") Integer id) {
        BaseMenu baseMenu = baseMenuService.getById(id);
        return SingleResponse.of(baseMenu);
    }

    @ApiOperation(value = "获取菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseMenu>> list(String title) {
        List<BaseMenu> menuList = baseMenuService.lambdaQuery().like(StrUtil.isNotBlank(title), BaseMenu::getTitle, title).list();
        return SingleResponse.of(menuList);
    }

    @ApiOperation(value = "获取菜单树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MenuTree>> tree(String title) {
        List<BaseMenu> menuList = baseMenuService.lambdaQuery().like(StrUtil.isNotBlank(title), BaseMenu::getTitle, title).list();
        return SingleResponse.of(getMenuTree(menuList, AdminCommonConstant.ROOT));
    }

    @ApiOperation(value = "根据父菜单id获取菜单树")
    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MenuTree>> getMenuTreeByParentId(Integer parentId) {
        if (parentId == null) {
            return SingleResponse.of(new ArrayList<MenuTree>());
        }
        BaseMenu parent = baseMenuService.getById(parentId);
        List<BaseMenu> menuList = baseMenuService.lambdaQuery().like(BaseMenu::getPath, parent.getPath()).list();
        return SingleResponse.of(getMenuTree(menuList, parent.getId()));
    }

    @ApiOperation(value = "获取菜单树")
    @RequestMapping(value = "/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AuthorityMenuTree>> listAuthorityMenu() {
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (BaseMenu menu : baseMenuService.lambdaQuery().list()) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            node.setOrderNum(menu.getOrderNum());
            node.setType(menu.getType());
            node.setDescription(menu.getDescription());
            node.setCode(menu.getCode());
            node.setHref(menu.getHref());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        List<AuthorityMenuTree> treeMenus = TreeUtil.bulid(trees, AdminCommonConstant.ROOT);
        return SingleResponse.of(treeMenus);
    }

    private List<MenuTree> getMenuTree(List<BaseMenu> menus, int root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (BaseMenu menu : menus) {
            node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            node.setLabel(menu.getTitle());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}
