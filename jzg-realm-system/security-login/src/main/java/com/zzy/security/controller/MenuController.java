package com.zzy.security.controller;


import cn.hutool.core.util.StrUtil;
import com.zzy.core.dto.R;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.dto.SysMenuDto;
import com.zzy.security.lib.entity.SysMenu;
import com.zzy.security.lib.entity.SysRoleMenu;
import com.zzy.security.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@Api(tags = "用户菜单角色管理")
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    @GetMapping("/getCurrentUserMenuList")
    @ApiOperation(value = "当前用户菜单列表",notes = "返回当前用户角色拥有的菜单列表")
    public R menuCurrent(HttpServletRequest request) {
        CustomUser loginUser = JwtUtil.getFromJwt(request);
        Map<String,Object> map = new HashMap<>();
        map.put("userId", loginUser.getUserId());
        List<SysMenuDto> list = menuService.listByUserId(map);
        final List<SysMenuDto> menus = list.stream().filter(l -> l.getType().equals(1)).collect(Collectors.toList());
        List<SysMenuDto> firstLevel = menus.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(m ->setChild(m, menus));
        return R.ok(firstLevel);
    }


    @GetMapping("/getAllMenuList")
    @ApiOperation(value = "获取所有菜单列表",notes = "返回系统后台的菜单列表")
    public R getAllMenuList(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        List<SysMenuDto> list = menuService.listAll(map);
        final List<SysMenuDto> menus = list.stream().filter(l -> l.getType().equals(1)).collect(Collectors.toList());
        List<SysMenuDto> firstLevel = menus.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(m ->setChild(m, menus));
        return R.ok(firstLevel);
    }


    /**
     * 设置子元素
     * @param m
     * @param menus
     */
    private void setChild(SysMenuDto m, List<SysMenuDto> menus) {
        List<SysMenuDto> child = menus.parallelStream().filter(a -> a.getParentId().equals(m.getMenuId())).collect(Collectors.toList());
        m.setChild(child);
        if (!CollectionUtils.isEmpty(child)) {
            child.parallelStream().forEach(c->setChild(c, menus));
        }
    }


    @PostMapping("/addMenuInfo")
    @ApiOperation(value = "添加菜单信息",notes = "前端页面菜单/按钮/目录")
    public R addMenu(SysMenuDto menuDto){
        if(StrUtil.isBlankOrUndefined(menuDto.getUrl())
                || StrUtil.isBlankOrUndefined(menuDto.getName())
                || menuDto.getType()==null
        ){
            return R.nullValueError();
        }
        SysMenu sm = new SysMenu();
        sm.setCreateTime(new Date());
        sm.setIcon(menuDto.getIcon());
        sm.setName(menuDto.getName());
        sm.setIsDel(0);
        if(menuDto.getIsEnable()==null){
            menuDto.setIsEnable(1);
        }
        sm.setIsEnable(menuDto.getIsEnable());
        sm.setUrl(menuDto.getUrl());
        if(menuDto.getSequence()==null){
            menuDto.setSequence(1);
        }
        sm.setSequence(menuDto.getSequence());
        if(menuDto.getParentId()==null){
            menuDto.setParentId(0);
        }
        sm.setParentId(menuDto.getParentId());
        sm.setType(menuDto.getType());
        boolean r = menuService.addMenuInfo(sm);
        return r?R.success():R.fail();
    }


    @PostMapping("/delMenu")
    @ApiOperation(value = "删除菜单信息",notes = "删除后已有该菜单的角色将失效")
    public R delMenu(Integer menuId){
        if(menuId==null){
            return R.nullValueError();
        }
        List<SysMenu> mu = menuService.getMenuChildByParentId(menuId);
        if(mu!=null && mu.size()>0){
            return R.error("该菜单拥有子菜单项,无法删除");
        }
        SysMenu sm = new SysMenu();
        sm.setId(menuId);
        sm.setIsDel(1);
        sm.setUpdateTime(new Date());
        return menuService.updateByPkId(sm)?R.success():R.fail();
    }




    @PostMapping("/editMenuInfo")
    @ApiOperation(value = "修改菜单信息")
    public R editMenu(Integer menuId, SysMenuDto menuDto){
        if(StrUtil.isBlankOrUndefined(menuDto.getUrl())
                || StrUtil.isBlankOrUndefined(menuDto.getName())
                || menuDto.getType()==null
                || menuId==null
        ){
            return R.nullValueError();
        }
        SysMenu sm = new SysMenu();
        sm.setId(menuId);
        sm.setCreateTime(new Date());
        sm.setIcon(menuDto.getIcon());
        sm.setName(menuDto.getName());
        sm.setIsEnable(menuDto.getIsEnable());
        sm.setUrl(menuDto.getUrl());
        sm.setSequence(menuDto.getSequence());
        sm.setParentId(menuDto.getParentId());
        sm.setType(menuDto.getType());
        boolean r = menuService.editMenuInfo(sm);
        return r?R.success():R.fail();
    }




    @PostMapping("/addMenuForRole")
    @ApiOperation(value = "添加角色菜单",notes = "角色拥有的菜单增加(设置角色后只有拥有该角色的用户才能拥有该菜单的对应操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuIds",value="菜单Id,多个id用逗号分隔", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="roleId",value="角色ID", required=true,paramType = "query",  dataType="string"),
    })
    public R setMenuForRole(String menuIds, String roleId){
        if(StrUtil.isBlankOrUndefined(menuIds) || StrUtil.isBlankOrUndefined(roleId)
                || StrUtil.isBlankOrUndefined(menuIds=menuIds.replace("[", "").replace("]", ""))){
            return R.nullValueError();
        }
        String[] menus = menuIds.split(",");
        List<SysRoleMenu> li = new ArrayList<>();
        SysRoleMenu rm = null;
        for (String menu : menus) {
            rm = new SysRoleMenu();
            rm.setMenuId(Long.parseLong(menu));
            rm.setRoleId(Long.parseLong(roleId));
            li.add(rm);
        }
        boolean r = false;
        if(li.size()>0){
             r = menuService.batchSaveRoleMenu(li);
        }
        return r?R.success():R.fail();
    }


    @PostMapping("/removeMenuForRole")
    @ApiOperation(value = "移除角色菜单",notes = "角色拥有的菜单移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuIds",value="菜单Id,多个id用逗号分隔", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="roleId",value="角色ID", required=true,paramType = "query",  dataType="string"),
    })
    public R removeMenuFromRole(String menuIds, String roleId){
        if(StrUtil.isBlankOrUndefined(menuIds) || StrUtil.isBlankOrUndefined(roleId)
                || StrUtil.isBlankOrUndefined(menuIds=menuIds.replace("[", "").replace("]", ""))){
            return R.nullValueError();
        }
        String[] menus = menuIds.split(",");
        List<Long> ids = Arrays.asList(menus).stream().map(o->Long.parseLong(o)).collect(Collectors.toList());
        Map<String,Object> m = new HashMap<>();
        m.put("ids", ids);
        m.put("roleId", Long.parseLong(roleId));
        boolean r = false;
        r = menuService.removeMenuByRoleMenuId(m);
        return r?R.success():R.fail();
    }






















}
