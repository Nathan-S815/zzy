package com.zzy.api.service.eventdepart.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.db.dao.eventdepart.DepartmentMemberMapper;
import com.zzy.api.service.eventdepart.IDepartmentMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.security.lib.dao.UserInfoMapper;
import com.zzy.security.lib.dao.UserRoleMapper;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.lib.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部员表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class DepartmentMemberServiceImpl extends ServiceImpl<DepartmentMemberMapper, DepartmentMember> implements IDepartmentMemberService {


    @Autowired
    DepartmentMemberMapper departmentMemberMapper;


    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<Map<String, Object>> getAllMemberByDepartId(Integer departmentId) {
        return departmentMemberMapper.selectAllMemeberByDepartId(departmentId);
    }

    @Override
    public PageInfo<Map<String, Object>> getPageDepartmentMembers(Integer pageNo, Integer pageSize, Map<String, Object> para) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list = departmentMemberMapper.selectPageMembers(para);
        return new PageInfo<>(list);
    }


    @Transactional(transactionManager = "secondaryTransactionManager")
    @Override
    public boolean batchAddMemberLoginUsers(List<UserInfo> list, int roleId) {
        int  res = 0;
        res  = userInfoMapper.batchInsert(list);
        if(res>0){
            List<String> names = list.stream().map(o->o.getUserName()).collect(Collectors.toList());
            list = userInfoMapper.selectByUsrenames(names);
            UserRole ur = null;
            List<UserRole> l = new ArrayList<>();
            for (UserInfo info : list) {
                ur = new UserRole();
                ur.setUserId(info.getId());
                ur.setRoleId(roleId);
                l.add(ur);
            }
            res = userRoleMapper.batchInsert(l);
        }
        return res>0;
    }



    @Override
    public boolean updateLoginEnableById(int isLoginEnable, List<Integer> ids) {
        Map<String,Object> m = new HashMap<>();
        m.put("isLoginEnable", isLoginEnable);
        m.put("ids", ids);
        departmentMemberMapper.updateLoginEnableById(m);
        return false;
    }



    @Transactional(transactionManager = "primaryTransactionManager")
    @Override
    public boolean updateBatchById(Collection<DepartmentMember> entityList) {
        if(entityList==null || entityList.isEmpty()) return true;
        return super.updateBatchById(entityList);
    }
}
