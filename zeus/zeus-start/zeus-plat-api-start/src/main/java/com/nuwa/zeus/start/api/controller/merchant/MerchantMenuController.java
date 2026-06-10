package com.nuwa.zeus.start.api.controller.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.mch.qry.GetUserElementQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetUserMenuQryExe;
import com.nuwa.app.zeus.vo.MenuTree;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetUserElementQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetUserMenuQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * MerchantMenuController 菜单管理
 *
 * @author hy
 * @date 2021/6/3 10:38
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/menu")
@Api(tags = {"菜单管理"})
public class MerchantMenuController {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private GetUserMenuQryExe getUserMenuQryExe;

    @Autowired
    private GetUserElementQryExe getUserElementQryExe;

    @ApiOperation(value = "获取用户菜单树")
    @RequestMapping(value = "authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MenuTree>> treeUserAuthorityMenu(@RequestParam(required = false, defaultValue = "-1") Integer parentId, UserAware userAware) {
        List<BaseMenu> baseMenus = baseMapperExt.selectAuthorityMenuByUserId(userAware.getMchUserId().intValue());
        baseMenus = baseMenus.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(BaseMenu::getId))), ArrayList::new));
        baseMenus = baseMenus.stream().sorted(Comparator.comparing(BaseMenu::getOrderNum)).collect(Collectors.toList());
        return SingleResponse.of(getMenuTree(baseMenus.stream().filter(x -> parentId.longValue() == x.getAppId()).collect(Collectors.toList()), -1));
    }

    @ApiOperation(value = "获取角色菜单树")
    @RequestMapping(value = "group/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MenuTree>> treeUserAuthorityMenuByGroup(@RequestParam(required = true) Integer groupId, UserAware userAware) {
        List<BaseMenu> baseMenus = baseMapperExt.selectUserAuthorityMenuByUserIdAndGroupId(userAware.getMchUserId().intValue(), groupId);
        baseMenus = baseMenus.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(BaseMenu::getId))), ArrayList::new));
        baseMenus = baseMenus.stream().sorted(Comparator.comparing(BaseMenu::getOrderNum)).collect(Collectors.toList());
        return SingleResponse.of(getMenuTree(baseMenus, -1));
    }


    @ApiOperation(value = "获取用户菜单树V2")
    @RequestMapping(value = "authorityTree/v2", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AppInfo>> treeUserAuthorityMenuV2(@RequestParam(required = false, defaultValue = "-1") Integer parentId, UserAware userAware) {
        List<Long> appIds = appDependentService.lambdaQuery()
                .eq(AppDependent::getDependentAppId, parentId)
                .eq(AppDependent::getIsBaseApp, 1)
                .list()
                .stream()
                .map(AppDependent::getAppId)
                .collect(Collectors.toList());

        List<String> group = baseGroupMemberService.lambdaQuery()
                .eq(BaseGroupMember::getUserId, userAware.getMchUserId())
                .list()
                .stream()
                .map(BaseGroupMember::getGroupId)
                .collect(Collectors.toList());

        if (group.size() != 0 && appIds.size() != 0) {
            List<String> app = baseGroupService.lambdaQuery()
                    .in(BaseGroup::getId, group)
                    .in(BaseGroup::getCode, appIds).list()
                    .stream()
                    .map(BaseGroup::getCode)
                    .collect(Collectors.toList());

            if (app.size() != 0) {
                return SingleResponse.of(appInfoService.lambdaQuery().in(AppInfo::getId, app).list());
            }
        }
        return SingleResponse.buildSuccess();
    }

//    @ApiOperation(value = "获取用户系统菜单")
//    @RequestMapping(value = "system", method = RequestMethod.GET)
//    @ResponseBody
//    public SingleResponse<List<BaseMenu>> listUserAuthoritySystem(UserAware userAware) {
//        List<BaseMenu> baseMenus = baseMapperExt.selectAuthoritySystemByUserId(userAware.getMchUserId().intValue());
//        return SingleResponse.of(baseMenus.stream().filter(x -> checkAppEnabled(x.getAppId())).collect(Collectors.toList()));
//    }

    @ApiOperation(value = "获取用户菜单权限")
    @RequestMapping(value = "menus", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GetUserMenuQryExe.MenuVO>> listUserMenu(GetUserMenuQry cmd, UserAware userAware) {
        return getUserMenuQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取用户按钮权限")
    @RequestMapping(value = "button", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GetUserElementQryExe.ElementVO>> listUserElement(GetUserElementQry cmd, UserAware userAware) {
        return getUserElementQryExe.execute(cmd);
    }


    private Boolean checkAppEnabled(Long appId) {
        if (appId == -1) {
            return true;
        }
        return appInfoService.lambdaQuery().eq(AppInfo::getStatus, 1).count() > 0;
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
