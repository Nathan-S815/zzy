package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.SysRoleMenu;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated
     */
    int insert(SysRoleMenu record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(SysRoleMenu record);

    /**
     *
     * @mbg.generated
     */
    SysRoleMenu selectByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysRoleMenu record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysRoleMenu record);

    int batchInsert(List<SysRoleMenu> li);

    int deleteByMenuIdsAndRoleId(Map<String, Object> m);
}