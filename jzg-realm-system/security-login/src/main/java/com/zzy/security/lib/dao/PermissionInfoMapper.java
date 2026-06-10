package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.PermissionInfo;

import java.util.List;

public interface PermissionInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(PermissionInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PermissionInfo record);

    /**
     *
     * @mbg.generated
     */
    PermissionInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PermissionInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PermissionInfo record);

    List<PermissionInfo> selectPermissionByUserName(String userName);

    List<PermissionInfo> selectPermissionList();

    List<PermissionInfo> selectPermissionListByUserId(Integer userId);
}