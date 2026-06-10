package com.zzy.api.service.eventdepart.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.core.utils.AuthUtil;
import com.zzy.db.dto.DepartmentInfoMemberInfo;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.zzy.db.dao.eventdepart.DepartmentInfoMapper;
import com.zzy.api.service.eventdepart.IDepartmentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.security.lib.dao.UserInfoMapper;
import com.zzy.security.lib.dao.UserRoleMapper;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.lib.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class DepartmentInfoServiceImpl extends ServiceImpl<DepartmentInfoMapper, DepartmentInfo> implements IDepartmentInfoService {


    @Autowired
    DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public List<Map<String, Object>> getAllDepartments() {
        return departmentInfoMapper.selectAllDepartments();
    }

    @Override
    public PageInfo<Map<String, Object>> getPageDepartments(Integer pageNo, Integer pageSize, Map<String, Object> para) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = departmentInfoMapper.selectPageList(para);
        return new PageInfo<>(list);
    }


    @Override
    public boolean leaderSetWithLoginAccount(DepartmentInfo di, Integer departId, Integer memberId, String userName, String pwd) {
            UserInfo ui = new UserInfo();
            ui.setUserName(di.getLeaderLoginName());
            ui.setPassWord(AuthUtil.getSaltedPwd(pwd));
            ui.setCreateTime(new Date());
            ui.setIsEnable(1);
            ui.setIsDelete(0);
            boolean r = userInfoMapper.insertUser(ui)>0;
            if(r){
                UserRole ur = new UserRole();
                ur.setUserId(ui.getId());
                ur.setRoleId(4); //部门负责人
                ur.setCreationTime(new Date());
                r = userRoleMapper.insert(ur)>0;
                if(r){
                    di.setLeaderUserId(ui.getId());
                    r = departmentInfoMapper.updateById(di) > 0;
                }
            }
            return r;
    }

    @Override
    public List<DepartmentInfoMemberInfo> getLeaderInfoByMemberId(Set<Integer> ids) {
        return departmentInfoMapper.selectleaderInfoByMemberIds(ids);
    }


}
