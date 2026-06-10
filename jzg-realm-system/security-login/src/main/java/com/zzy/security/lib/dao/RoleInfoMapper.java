package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(RoleInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(RoleInfo record);

    /**
     *
     * @mbg.generated
     */
    RoleInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RoleInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> selectRoleList();

    List<RoleInfo> selectRoleByUserId2(Integer id);

    RoleInfo selectByRoleName(String roleName);

    int insertRoleInfo(RoleInfo ri);

    List<RoleInfo> selectByRoleNames(@Param("roleNames") List<String> roleNames);

    List<RoleInfo> selectRolesByUserName(String userName);
}