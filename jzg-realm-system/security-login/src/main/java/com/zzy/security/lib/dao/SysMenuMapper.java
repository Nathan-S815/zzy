package com.zzy.security.lib.dao;

import com.zzy.security.dto.SysMenuDto;
import com.zzy.security.lib.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(SysMenu record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(SysMenu record);

    /**
     *
     * @mbg.generated
     */
    SysMenu selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysMenu record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysMenu record);

    List<SysMenuDto> selectListByUserId(Map<String, Object> m);

    List<SysMenuDto> selectAllList(Map<String, Object> map);

    List<SysMenu> selectMenuChildByParentId(Integer menuId);
}