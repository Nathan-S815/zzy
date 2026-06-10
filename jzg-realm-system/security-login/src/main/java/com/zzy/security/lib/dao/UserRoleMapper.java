package com.zzy.security.lib.dao;


import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(UserRole record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(UserRole record);

    /**
     *
     * @mbg.generated
     */
    UserRole selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserRole record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserRole record);


    List<String> selectRoleByUserId(Integer id);

    int upsert(Map<String, Object> m);

    int deleteByUniqueKey(Map<String, Object> m);

    int deleteByRoleId(Integer id);

    int deleteByUserId(int parseInt);

    int batchInsert(List<UserRole> l);

    List<RoleInfo> selectRolesByUserId(Integer id);
}