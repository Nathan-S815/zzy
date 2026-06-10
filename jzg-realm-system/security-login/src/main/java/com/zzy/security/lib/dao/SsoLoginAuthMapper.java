package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.SsoLoginAuth;

public interface SsoLoginAuthMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String userName);

    /**
     *
     * @mbg.generated
     */
    int insert(SsoLoginAuth record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(SsoLoginAuth record);

    /**
     *
     * @mbg.generated
     */
    SsoLoginAuth selectByPrimaryKey(String userName);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SsoLoginAuth record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SsoLoginAuth record);
}