package com.zzy.api.controller.security;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zzy.api.dto.AccountDetails;
import com.zzy.api.service.base.IMerchantTypeService;
import com.zzy.api.service.eventdepart.IDepartmentInfoService;
import com.zzy.api.service.eventdepart.IDepartmentMemberService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.AuthUtil;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.dto.MerchantInfo;
import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("security")
@Api(tags = "账户资料相关")
public class AccountController {

    @Autowired
    private UsersService usersService;


    @Autowired
    private IMerchantTypeService merchantTypeService;

    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @Autowired
    private IDepartmentMemberService departmentMemberService;


    @PostMapping("editDepartAdmin")
    @ApiOperation(value = "修改部门管理员")
    public R editDepartAdmin(Integer userId, String username){
        if(StrUtil.isBlankOrUndefined(username) || userId==null){
            return R.nullValueError();
        }
        if(usersService.findUserByName(username)!=null){
            return R.error("已存在相同用户名");
        }
        UserInfo ui = new UserInfo();
        ui.setUserName(username);
        ui.setId(userId);
        return usersService.editUserById(ui)>0?R.success():R.fail();
    }


    @RequiredPermission(hasRole = "ROOT")
    @PostMapping("createDepartAdmin")
    @ApiOperation(value = "创建部门管理员账户")
    public R createDepartAdmin(String username,String pwd, String headIcon){
        if(StrUtil.isBlankOrUndefined(username) || StrUtil.isBlankOrUndefined(pwd)){
            return R.nullValueError();
        }
        if(usersService.findUserByName(username)!=null){
            return R.error("已存在相同用户名");
        }
        UserInfo ui = new UserInfo();
        ui.setUserName(username);
        ui.setPassWord(AuthUtil.getSaltedPwd(pwd));
        ui.setIsEnable(1);
        ui.setHeadIcon(headIcon);
        ui.setIsDelete(0);
        ui.setCreateTime(new Date());
        return usersService.saveUserWithRole(ui,2)?R.success():R.fail();
    }


    @GetMapping("getDepartAdminList")
    @ApiOperation(value = "获取部门管理员账户列表")
    public R createDepartAdmin(String username,Integer pageNo,Integer pageSize){
        if(pageNo==null){ pageNo=1; }
        if(pageSize==null){ pageSize=10; }
        Map<String,Object> m = new HashMap<>();
        if(!StrUtil.isBlankOrUndefined(username)){
            m.put("userName", username);
        }
        m.put("roleId", 2); //部门管理员
        return R.ok(usersService.findUserPages(pageNo,pageSize,m));
    }



    @GetMapping("getAccountDetails")
    @ApiOperation(value = "获取登录账户详情")
    public R getAccountDetails(HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        List<RoleInfo> roles = usersService.findRolesByUserName(cu.getUsername());
        if(roles.size()<1){
            return R.ok("账户未设置角色");
        }
        Map<String, Object> m = new HashMap<>();
        m.put("userId", cu.getUserId());
        m.put("roles", cu.getAuths());
        Set<String> names = roles.stream().map(o -> o.getRoleName()).collect(Collectors.toSet());
        Set<String> roleCodes = roles.stream().map(o -> o.getRoleCode()).collect(Collectors.toSet());
        List<String> list = merchantTypeService.selectMerchantTypeCodeByRoleName(m);
        AccountDetails jo = new AccountDetails();
        if((roleCodes!=null && roleCodes.contains("ADMIN")) || (cu.getAuths().contains("ROOT"))){
            UserInfo ui = usersService.selectUserInfo(cu.getUserId());
            jo.setHeadIcon(ui.getHeadIcon());
            jo.setIsDepartAdmin(true);
        }else{
            jo.setIsDepartAdmin(false);
            DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,cu.getUserId()).one();
            if(di==null){
                DepartmentMember dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getUserId,cu.getUserId()).one();
                if(dm!=null){
                    jo.setDepartId(dm.getDepartmentId());
                    jo.setDepartMemberId(dm.getId());
                    jo.setHeadIcon(dm.getHeadIcon());
                    jo.setIsDepartLeader(false);
                }
            }else{
                jo.setIsDepartLeader(true);
                DepartmentMember dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getId,di.getLeader()).one();
                if(dm!=null){
                    jo.setDepartMemberId(di.getLeader());
                    jo.setHeadIcon(dm.getHeadIcon());
                    jo.setDepartId(di.getId());
                }
            }
        }
        jo.setUserName(cu.getUsername());
        jo.setUserId(cu.getUserId());
        jo.setRoleNames(names);
        if (list != null && list.size() > 0) {
            MerchantInfo mi = null;
            List<MerchantInfo> l = new ArrayList<>();
            Map<String, Object> baseId = null;
            for (String s : list) {
                mi = new MerchantInfo();
                mi.setMerchantTypeName(s);
                m.put("tableName", s);
                baseId = merchantTypeService.selectBaseInfoIdByNameAndUserId(m);
                if(baseId==null){
                    continue;
                }
                mi.setBaseId(Integer.parseInt(String.valueOf(baseId.get("id"))));
                mi.setAuditState(Integer.parseInt(String.valueOf(baseId.get("auditState"))));
                mi.setFillBase(!StrUtil.isBlankOrUndefined(String.valueOf(baseId.get("baseName"))));
                mi.setMerchantName(String.valueOf(baseId.get("baseName")).replace("null", ""));
                l.add(mi);
            }
            jo.setMerchantInfo(l);
        }
        return R.ok(jo);
    }


}
