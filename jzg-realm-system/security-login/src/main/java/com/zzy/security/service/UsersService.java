package com.zzy.security.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.core.dto.R;
import com.zzy.security.dto.PermissionInfoDto;
import com.zzy.security.dto.RoleInfoDto;
import com.zzy.security.dto.UserInfoDto;
import com.zzy.security.lib.dao.*;
import com.zzy.security.lib.entity.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionInfoMapper ptPermissionInfoMapper;
    @Autowired
    private RoleInfoMapper ptRoleInfoMapper;

    @Autowired
    private RolePermissionMapper ptRolePermissionMapper;

    public int addUser(UserInfo ui) {
        int i = userInfoMapper.insertUser(ui);
        if(i<1) {
            return 0;
        }
        UserRole ur = new UserRole();
        ur.setRoleId(1);
        ur.setUserId(ui.getId());
        ur.setCreationTime(new Date());
        try {
            return userRoleMapper.insert(ur);
        } catch (Exception e) {
            e.printStackTrace();
            userInfoMapper.deleteByPrimaryKey(ui.getId());
            return 0;
        }
    }

    public int addUserReturnId(UserInfo ui){
        return userInfoMapper.insertStudentCacheId(ui);
    }

    public int insertPermissionInfo(PermissionInfoDto ptPermissionInfoDto){
        return ptPermissionInfoMapper.insert(ptPermissionInfoDto);
    }

    public int deletePermissionInfo(Integer id){
        return ptPermissionInfoMapper.deleteByPrimaryKey(id);
    }
    public PermissionInfo selectPermissionInfo(Integer id){
        return ptPermissionInfoMapper.selectByPrimaryKey(id);
    }
    public int updatePermissionInfo(PermissionInfoDto ptPermissionInfoDto){
        return ptPermissionInfoMapper.updateByPrimaryKeySelective(ptPermissionInfoDto);
    }

    public int insertRoleInfo(String roleName){
        RoleInfo pr = new RoleInfo();
        pr.setCreateTime(new Date());
        pr.setRoleName(roleName);
        pr.setIsEnable(1);
        return ptRoleInfoMapper.insertSelective(pr);
    }

    public int deleteRoleInfo(Integer id){
        int i = ptRolePermissionMapper.deleteByRoleId(id);
        i = userRoleMapper.deleteByRoleId(id);
        return ptRoleInfoMapper.deleteByPrimaryKey(id);
    }


    public int updateRoleInfo(RoleInfoDto ptRoleInfoDto){
        return ptRoleInfoMapper.updateByPrimaryKeySelective(ptRoleInfoDto);
    }

    public int insertUserInfo(UserInfoDto ptUserInfoDto){
        if(ptUserInfoDto.getCreateTime()==null){
            ptUserInfoDto.setCreateTime(new Date());
        }
        return userInfoMapper.insert(ptUserInfoDto);
    }

    public int deleteUserInfo(Integer id){
        if(id==null){
            return 0;
        }
        boolean r= userInfoMapper.deleteByPrimaryKey(id)>0;
        if(!r){
            return 0;
        }
        return userRoleMapper.deleteByUserId(id);
    }

    public UserInfo selectUserInfo(Integer id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public int updateUserInfo(UserInfoDto ptUserInfoDto){
        return userInfoMapper.updateByPrimaryKey(ptUserInfoDto);
    }


    public List<Map<String, Object>> findAllSystemUrls(String username) {
        return userInfoMapper.selectAllSystemUrls(username);
    }

    public int updateUserByUserName(UserInfo u) {
        return userInfoMapper.updateUserByNameSelective(u);
    }


    /**
     *
     * @param pageNo
     * @param pageSize
     * @param m{userName,roleId}
     * @return
     */
    public PageInfo<Map<String, Object>> findUserPages(Integer pageNo, Integer pageSize, Map<String, Object> m) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list = userInfoMapper.selectUserPages(m);
        return new PageInfo<>(list);
    }

    public UserInfo findUserByName(String username) {
        return userInfoMapper.selectByUsrename(username);
    }

    public int editUserById(UserInfo u) {
        return userInfoMapper.updateByPrimaryKeySelective(u);
    }

    public List<RoleInfo> findRoleList() {
        return ptRoleInfoMapper.selectRoleList();
    }

    public PageInfo<PermissionInfo> findPermissionList(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<PermissionInfo> lsit = ptPermissionInfoMapper.selectPermissionList();
        return new PageInfo<>(lsit);
    }

    public List<RoleInfo> findUserRoles(Integer userId) {
        return ptRoleInfoMapper.selectRoleByUserId2(userId);
    }

    public int addRoleToUser(Map<String, Object> m) {
        return userRoleMapper.upsert(m);
    }

    public int removeRoleToUser(Map<String, Object> m) {
        return userRoleMapper.deleteByUniqueKey(m);
    }


    public int addPermissionForRole(Map<String, Object> m) {
        return ptRolePermissionMapper.upsert(m);
    }

    public int removePermissionFromRole(int roleId,int permissionId) {
        RolePermissionKey ke = new RolePermissionKey();
        ke.setRoleId(roleId);
        ke.setPermissionId(permissionId);
        return ptRolePermissionMapper.deleteByPrimaryKey(ke);
    }

    public List<Map<String, Object>> findRolePermissions(Integer roleId) {
        return ptRolePermissionMapper.selectRolePermissionsByRoleId(roleId);
    }

    public RoleInfo findRoleInfoByRoleId(Integer roleId) {
        return ptRoleInfoMapper.selectByPrimaryKey(roleId);
    }

    public RoleInfo findRoleByRoleName(String roleName) {
        return ptRoleInfoMapper.selectByRoleName(roleName);
    }

    public PageInfo<PermissionInfo> findPermissionList(Integer pageNo, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNo,pageSize);
        List<PermissionInfo> list = ptPermissionInfoMapper.selectPermissionListByUserId(userId);
        return new PageInfo<>(list);
    }

    public boolean deleteRoleByUserId(String userId) {
        return userRoleMapper.deleteByUserId(Integer.parseInt(userId))>0;
    }

    public int batchInsertUserRole(List<UserRole> l) {
        return userRoleMapper.batchInsert(l);
    }


    public boolean removePermissionByRoleId(Integer roleId) {
        return ptRolePermissionMapper.deleteByRoleId(roleId)>0;
    }

    public boolean batchInsertRolePermission(List<RolePermissionKey> list) {
        return ptRolePermissionMapper.batchInsert(list)>0;
    }

    @Transactional(transactionManager = "secondaryTransactionManager")
    public boolean saveUserWithRole(UserInfo ui, int roleId) {
        userInfoMapper.insertUser(ui);
        UserRole ur = new UserRole();
        ur.setCreationTime(new Date());
        ur.setUserId(ui.getId());
        ur.setRoleId(roleId);
        return userRoleMapper.insert(ur)>0;
    }

    @Transactional(transactionManager = "secondaryTransactionManager")
    public int addUser(UserInfo u, String... roleIds) {
        int i = userInfoMapper.insertUser(u);
        List<UserRole> list = new ArrayList<>();
        UserRole ur = null;
        for (String roleId : roleIds) {
            ur = new UserRole();
            ur.setRoleId(Integer.parseInt(roleId));
            ur.setUserId(u.getId());
            ur.setCreationTime(new Date());
            list.add(ur);

        }
        return userRoleMapper.batchInsert(list);
    }

    public List<RoleInfo> findRoleIdsByRoleNames(List<String> roleNames) {
        return ptRoleInfoMapper.selectByRoleNames(roleNames);
    }


    @Transactional(transactionManager = "secondaryTransactionManager")
    public boolean changeUserRoleList(UserInfo ui, List<Integer> roleIds) {
        boolean r = userRoleMapper.deleteByUserId(ui.getId())>0;
        if(r){
            List<UserRole> l = new ArrayList<>();
            roleIds.forEach(o->l.add(UserRole.build(ui.getId(), o)));
            r = userRoleMapper.batchInsert(l)>0;
        }
        return r;
    }

    public List<RoleInfo> findRolesByUserName(String userName) {
        return ptRoleInfoMapper.selectRolesByUserName(userName);
    }
}
