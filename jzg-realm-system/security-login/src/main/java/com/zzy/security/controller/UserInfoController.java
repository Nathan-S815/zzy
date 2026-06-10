package com.zzy.security.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zzy.core.dto.R;
import com.zzy.core.utils.AuthUtil;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.dto.PermissionInfoDto;
import com.zzy.security.dto.RoleInfoDto;
import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.RolePermissionKey;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.lib.entity.UserRole;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/root/user")
@Api(tags = "用户信息相关")
public class UserInfoController {
    @Autowired
    private UsersService usersService;
    final String auth_token_start = "Bearer ";

    @PostMapping("/getRoleForAdmin")
    @ApiOperation(value = "查询管理员角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userName", value = "用户Name", required = true, dataType = "Integer"),
    })
    public R getRoleForAdmin(String userName){
        if(userName==null){
            return R.ok("参数不能为空");
        }
        return R.ok(usersService.findRolesByUserName(userName));
    }

    @PostMapping("/editRoleForAdmin")
    @ApiOperation(value = "修改管理员角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userName", value = "用户Name", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="roleIds", value = "角色Id,多个用逗号分隔", required = true, dataType = "string")
    })
    public R editRoleForUser(String userName, String roleId){
        if(roleId==null || userName==null){
            return R.ok("参数不能为空");
        }
        UserInfo userByName = usersService.findUserByName(userName);
        int userId = userByName.getId();
        List<RoleInfo> rolesByUserName = usersService.findRolesByUserName(userName);
        List<Integer> oldRole = new ArrayList<>();
        List<Integer> oldRole2 = new ArrayList<>();
        List<Integer> newRole = new ArrayList<>();
        List<Integer> newRole2 = new ArrayList<>();
        String[] split = roleId.split(",");
        for (int i =0;i<split.length;i++){
            newRole.add(Integer.parseInt(split[i]));
            newRole2.add(Integer.parseInt(split[i]));
        }
        for (RoleInfo roleInfo : rolesByUserName) {
            oldRole.add(roleInfo.getId());
            oldRole2.add(roleInfo.getId());
        }
        newRole.removeAll(oldRole);
        for (Integer role : newRole) {
            addRoleForUser(userId,role);
        }
        oldRole2.removeAll(newRole2);
        for (Integer role : oldRole2) {
            removeRoleForUser(userId,role);
        }
        return R.ok("修改成功");
    }


    @RequiredPermission(hasAnyRole = {"ADMIN","ROOT"})
    @PostMapping("/changeUserEnableState")
    @ApiOperation(value = "启用禁用登录用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value = "登录用户Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="isEnable", value = "1:启用,0:禁用", required = true, dataType = "Integer"),
    })
    public R changeUserEnableState(Integer userId, Integer isEnable,HttpServletRequest request){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(isEnable==null || userId==null){
            return R.ok("参数不能为空");
        }
        UserInfo ui = new UserInfo();
        ui.setIsEnable(isEnable);
        ui.setId(userId);
        return usersService.editUserById(ui)>0?R.ok("修改成功"):R.ok("修改失败");
    }

    @GetMapping("/selectUserInfo")
    @ApiOperation(value = "根据id获取用户基本信息")
    public R selectUserInfo(Integer id){
        return R.ok(usersService.selectUserInfo(id));
    }


    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="pageSize", value = "条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="username", value = "用户名", required = true, dataType = "string"),
    })
    @GetMapping("/findUsersPage")
    public R findUsersPage(Integer pageNo,Integer pageSize,String username, HttpServletRequest request){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        Map<String,Object> m = new HashMap<>();
        if(pageNo==null || pageNo == 0) {
            pageNo = 1;
        }
        if(pageSize==null || pageSize == 0){
            pageSize = 10;
        }
        if(!JSON.toJSONString(us.getAuthorities()).contains("ADMIN")){
            m.put("userName",us.getUsername());
        }else{
            m.put("userName",username);
        }
        PageInfo<Map<String,Object>> p = usersService.findUserPages(pageNo,pageSize,m);
        return R.ok(p);
    }

    @GetMapping("/findUserRoles")
    @ApiOperation(value = "根据用户ID获取该用户对应的角色列表")
    public R findUserRoles(Integer userId, HttpServletRequest request){
        if(userId==null){
            return R.ok("用户id不能为空");
        }
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(!JSON.toJSONString(us.getAuthorities()).contains("ADMIN")){
            userId = us.getUserId();
        }
        return R.ok(usersService.findUserRoles(userId));
    }

    @GetMapping("/findRolePermissions")
    @ApiOperation(value = "根据角色ID获取该角色对应的权限列表")
    public R findRolePermissions(Integer roleId, HttpServletRequest request){
        if(roleId==null){
            return R.ok("角色id不能为空");
        }
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(!JSON.toJSONString(us.getAuthorities()).contains("ADMIN")){
            return R.ok("权限不足");
        }
        return R.ok(usersService.findRolePermissions(roleId));
    }


    @GetMapping("/findRoleList")
    @ApiOperation(value = "获取角色列表")
    public R findRoleList(HttpServletRequest request){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(!JSON.toJSONString(us.getAuthorities()).contains("ADMIN")){
            return R.ok(usersService.findUserRoles(us.getUserId()));
        }
        return R.ok(usersService.findRoleList());
    }

    @GetMapping("/findPermissionList")
    @ApiOperation(value = "获取权限列表")
    public R findPermissionList(Integer pageNo,Integer pageSize,HttpServletRequest request){
        if(pageNo==null || pageNo == 0) {
            pageNo = 1;
        }
        if(pageSize==null || pageSize == 0){
            pageSize = 10;
        }
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(!JSON.toJSONString(us.getAuthorities()).contains("ADMIN")){
            return R.ok(usersService.findPermissionList(pageNo,pageSize,us.getUserId()));
        }
        return R.ok(usersService.findPermissionList(pageNo,pageSize));
    }


    @PostMapping("/createAdmin")
    @ApiOperation(value = "创建管理员账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value = "用户名", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="pwd", value = "密码", required = true, dataType = "Integer")
    })
    public R createAdmin(HttpServletRequest request,String username,String pwd){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(us.getAuths().contains("ROOT")){
            return R.ok("权限不足");
        }
        if(StrUtil.isBlankOrUndefined(username) ||StrUtil.isBlankOrUndefined(pwd)){
            return R.nullValueError();
        }
        UserInfo user = usersService.findUserByName(username);
        if(user!=null){
            return R.ok("用户名已存在");
        }
        UserInfo u = new UserInfo();
        u.setPassWord(AuthUtil.getSaltedPwd(pwd));
        u.setUserName(username);
        u.setIsDelete(0);
        u.setIsEnable(1);
        u.setCreateTime(new Date());
        return usersService.addUser(u,"5")>0?R.ok("创建成功"):R.ok("创建失败");

    }




    @PostMapping("/addUser")
    @ApiOperation(value = "创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value = "用户名", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="pwd", value = "密码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="roleIds", value = "角色Id(多个用逗号分隔)", required = true, dataType = "string"),
    })
    public R insertUserInfo(HttpServletRequest request,String username,String pwd,String roleIds){
        String token = request.getHeader(JwtUtil.jwtHead);
        if(StrUtil.isBlank(token)){
            return R.ok("登录状态失效,请重新登录");
        }
        token = token.substring(auth_token_start.length());
        CustomUser us = JwtUtil.getUserFromToken(token);
        if(us==null){
            return R.ok("登录状态失效,请重新登录");
        }
        if(us.getAuths().contains("ADMIN")){
            return R.ok("权限不足");
        }
        if(StrUtil.isBlankOrUndefined(username) ||StrUtil.isBlankOrUndefined(pwd) ||StrUtil.isBlankOrUndefined(roleIds) || ArrayUtil.isEmpty(roleIds.split(","))){
            return R.nullValueError();
        }
        UserInfo user = usersService.findUserByName(username);
        if(user!=null){
            return R.ok("用户名已存在");
        }
        UserInfo u = new UserInfo();
        u.setPassWord(AuthUtil.getSaltedPwd(pwd));
        u.setUserName(username);
        u.setIsDelete(0);
        u.setIsEnable(1);
        u.setCreateTime(new Date());
        return usersService.addUser(u,roleIds.split(","))>0?R.ok("创建成功"):R.ok("创建失败");

    }


    @RequiredPermission(hasAnyRole = {"ADMIN","ROOT"})
    @PostMapping("/editPasswordByAdmin")
    @ApiOperation(value = "管理员修改密码")
    public R editPassword(String newPwd,String uname){
        if(StrUtil.isBlankOrUndefined(newPwd) || StrUtil.isBlankOrUndefined(uname)){
            return R.error("参数不能为空");
        }
        UserInfo user = usersService.findUserByName(uname);
        if(user==null || user.getIsDelete()==1){
            return R.error("账号不存在或异常");
        }
        UserInfo u = new UserInfo();
        u.setPassWord(AuthUtil.getSaltedPwd(newPwd));
        u.setUserName(uname);
        return usersService.updateUserByUserName(u)>0?R.ok("修改成功"):R.ok("修改失败");

    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    public R changePpwd(String oldPwd,String newPwd,String uname){
        if(StrUtil.isBlankOrUndefined(oldPwd) ||StrUtil.isBlankOrUndefined(newPwd) || StrUtil.isBlankOrUndefined(uname)){
            return R.ok("参数不能为空");
        }
        String password = AuthUtil.getSaltedPwd(oldPwd);
        UserInfo user = usersService.findUserByName(uname);
        if(!user.getPassWord().equals(password)){
            return R.ok("原密码不匹配");
        }
        UserInfo u = new UserInfo();
        u.setPassWord(AuthUtil.getSaltedPwd(newPwd));
        u.setUserName(uname);
        return usersService.updateUserByUserName(u)>0?R.ok("修改成功"):R.ok("修改失败");

    }

    @PostMapping("/editUserInfo")
    @ApiOperation(value = "修改用户基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="username", value = "修改后的用户名", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="roleIds", value = "角色Id,多个用逗号分隔", required = true, dataType = "string"),
    })
    public R editUserInfo(String userId, String username, String roleIds){
        if(StrUtil.isBlankOrUndefined(username) || StrUtil.isBlankOrUndefined(userId)){
            return R.ok("参数不能为空");
        }
        UserInfo user = usersService.findUserByName(username);
        if(user!=null && !user.getId().equals(Integer.parseInt(userId))){
            return R.ok("用户名已存在");
        }
        String[] roles = new String[0];
        if(!StrUtil.isBlankOrUndefined(roleIds)){
            roleIds = roleIds.replace("[", "").replace("]", "");
            roles = roleIds.split(",");
        }

        UserInfo u = new UserInfo();
        u.setUserName(username);
        u.setId(Integer.parseInt(userId));
        boolean r = usersService.editUserById(u)>0;
        if(r){
            UserRole ur = null;
            List<UserRole> l = new ArrayList<>();
            for (String role : roles) {
                ur = new UserRole();
                ur.setRoleId(Integer.parseInt(role));
                ur.setUserId(Integer.parseInt(userId));
                ur.setCreationTime(new Date());
                l.add(ur);
            }
            r = usersService.deleteRoleByUserId(userId);
            if(l.size()>0){
                r = usersService.batchInsertUserRole(l) > 0;
            }
        }
        return r?R.ok("修改成功"):R.ok("修改失败");
    }


    @PostMapping("/addRoleForUser")
    @ApiOperation(value = "为某用户添加角色")
    public R addRoleForUser(Integer userId, Integer roleId){
        if(roleId==null || userId==null){
            return R.ok("参数不能为空");
        }
        Map<String,Object> m = new HashMap<>();
        m.put("userId",userId);
        m.put("roleId",roleId);
        return usersService.addRoleToUser(m)>0?R.ok("修改成功"):R.ok("修改失败");
    }

    @PostMapping("/removeRoleFromUser")
    @ApiOperation(value = "移除用户的角色")
    public R removeRoleForUser(Integer userId, Integer roleId){
        if(roleId==null || userId==null){
            return R.ok("参数不能为空");
        }
        Map<String,Object> m = new HashMap<>();
        m.put("userId",userId);
        m.put("roleId",roleId);
        return usersService.removeRoleToUser(m)>0?R.ok("修改成功"):R.ok("修改失败");
    }

    @PostMapping("/addPermissionForRole")
    @ApiOperation(value = "为某角色添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleId", value = "角色id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name="permissionIds", value = "权限Id,多个用逗号分隔", required = true, dataType = "string"),
    })
    public R addPermissionForRole(String permissionIds, Integer roleId){
        if(roleId==null || StrUtil.isBlankOrUndefined(permissionIds)){
            return R.ok("参数不能为空");
        }
//        Map<String,Object> m = new HashMap<>();
//        m.put("permissionId",permissionId);
//        m.put("roleId",roleId);
        String[] pIds = permissionIds.replace("[", "").replace("]", "").split(",");
        RolePermissionKey rp = null;
        List<RolePermissionKey> list = new ArrayList<>();
        for (String id : pIds) {
            rp = new RolePermissionKey();
            rp.setPermissionId(Integer.parseInt(id));
            rp.setRoleId(roleId);
            rp.setUpdateTime(new Date());
            list.add(rp);
        }
        boolean r = false;
        if(list.size() > 0){
            r = usersService.removePermissionByRoleId(roleId);
            r = usersService.batchInsertRolePermission(list);
        }
        return r?R.ok("修改成功"):R.ok("修改失败");
    }

    @PostMapping("/removePermissionFromRole")
    @ApiOperation(value = "移除某角色的权限")
    public R removePermissionFromRole(Integer permissionId, Integer roleId){
        if(roleId==null || permissionId==null){
            return R.ok("参数不能为空");
        }
        return usersService.removePermissionFromRole(roleId,permissionId)>0?R.ok("修改成功"):R.ok("修改失败");
    }




    @DeleteMapping("/deleteUserInfo")
    @ApiOperation(value = "根据id删除用户")
    public R deleteUserInfo(Integer id){
        return R.ok(usersService.deleteUserInfo(id));
    }

    @PostMapping("/insertRoleInfo")
    @ApiOperation(value = "新增角色")
    public R insertRoleInfo(String roleName){
        RoleInfo pr = usersService.findRoleByRoleName(roleName);
        if(pr!=null){
            return R.ok("该角色名已存在");
        }
        return R.ok(usersService.insertRoleInfo(roleName));
    }

    @PostMapping("/findRoleInfo")
    @ApiOperation(value = "根据角色ID获取角色基本信息")
    public R updateRoleInfo(Integer roleId){
        return R.ok(usersService.findRoleInfoByRoleId(roleId));
    }


    @PostMapping("/updateRoleInfo")
    @ApiOperation(value = "修改角色信息")
    public R updateRoleInfo(RoleInfoDto ptRoleInfoDto){
        return R.ok(usersService.updateRoleInfo(ptRoleInfoDto));
    }

    @DeleteMapping("/deleteRoleInfo")
    @ApiOperation(value = "根据id删除角色")
    public R deleteRoleInfo(Integer id){
        return R.ok(usersService.deleteRoleInfo(id));
    }

    @PostMapping("/insertPermissionInfo")
    @ApiOperation(value = "新增权限信息")
    public R insertPermissionInfo(Integer isEnable,String permissionName,String permissionCode,String pemissionUrl){
        if(StrUtil.isBlankOrUndefined(pemissionUrl) ||
                StrUtil.isBlankOrUndefined(permissionCode)||
                StrUtil.isBlankOrUndefined(permissionName)){
            return R.ok("参数不能为空");
        }
        PermissionInfoDto pt = new PermissionInfoDto();
        pt.setPermissionCode(permissionCode);
        pt.setCreateTime(new Date());
        pt.setPermissionName(permissionName);
        pt.setPermissionUrl(pemissionUrl);
        pt.setIsEnable(isEnable);
        return R.ok(usersService.insertPermissionInfo(pt));
    }



    @GetMapping("/selectPermissionInfo")
    @ApiOperation(value = "根据id查询权限基本信息")
    public R  selectPermissionInfo(Integer id){
        return R.ok(usersService.selectPermissionInfo(id));
    }

    @PostMapping("/updatePermissionInfo")
    @ApiOperation(value = "修改权限基本信息")
    public R updatePermissionInfo(PermissionInfoDto ptPermissionInfoDto){
        return R.ok(usersService.updatePermissionInfo(ptPermissionInfoDto));
    }

    @DeleteMapping("/deletePermissionInfo")
    @ApiOperation(value = "根据id删除权限")
    public R deletePermissionInfo(Integer id){
        return R.ok(usersService.deletePermissionInfo(id));
    }


//    public static void main(String[] args) {
//        String oldPwd = "123456";
//        String saltedPwd = AuthUtil.getSaltedPwd(oldPwd);
//        //加密规则  先用明文md5一下  之后用AuthUtil.getSaltedPwd获取密文  security_login库中
//        //123456 549541a5d6a435b54f941b9d2a4eafb0
//        //123456@jzg  71bf0f01fe3682a061597c1f512f4b15
//        System.err.println(AuthUtil.getSaltedPwd("8fe4a1881a66af8b4ebc5f46476ab8b6"));
////        System.err.println(saltedPwd);
//
//    }
}
