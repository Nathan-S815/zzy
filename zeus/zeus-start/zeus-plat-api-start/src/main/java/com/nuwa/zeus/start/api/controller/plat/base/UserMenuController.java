package com.nuwa.zeus.start.api.controller.plat.base;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.vo.MenuTree;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.app.zeus.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuController
 *
 * @author hy
 * @date 2021/5/25 13:25
 * @since 1.0.0
 */
@Api(tags = {"用户菜单"})
@Controller
@RequestMapping("/user/menu")
public class UserMenuController {

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @ApiOperation(value = "获取用户菜单树")
    @RequestMapping(value = "authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MenuTree>> treeUserAuthorityMenu(Integer parentId, UserAware userAware) {
        List<BaseMenu> baseMenus = baseMapperExt.selectAuthorityMenuByUserId(userAware.getMchUserId().intValue());
        return SingleResponse.of(getMenuTree(baseMenus, parentId));
    }

    @ApiOperation(value = "获取用户系统菜单")
    @RequestMapping(value = "system", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseMenu>> listUserAuthoritySystem(UserAware userAware) {
        List<BaseMenu> baseMenus = baseMapperExt.selectAuthoritySystemByUserId(userAware.getMchUserId().intValue());
        return SingleResponse.of(baseMenus);
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
