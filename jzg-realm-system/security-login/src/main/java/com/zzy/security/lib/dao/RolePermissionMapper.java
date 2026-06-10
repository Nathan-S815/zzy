package com.zzy.security.lib.dao;


import com.zzy.security.lib.entity.RolePermissionKey;

import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RolePermissionKey key);

    /**
     *
     * @mbg.generated
     */
    int insert(RolePermissionKey record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(RolePermissionKey record);

    int upsert(Map<String, Object> m);

    List<Map<String, Object>> selectRolePermissionsByRoleId(Integer roleId);

    int deleteByRoleId(Integer id);

    int batchInsert(List<RolePermissionKey> list);
}