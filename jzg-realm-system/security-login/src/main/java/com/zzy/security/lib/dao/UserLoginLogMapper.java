package com.zzy.security.lib.dao;

import com.zzy.security.dto.UserLoginInfo;
import com.zzy.security.lib.entity.UserLoginLog;

import java.util.List;
import java.util.Map;

public interface UserLoginLogMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(UserLoginLog record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(UserLoginLog record);

    /**
     *
     * @mbg.generated
     */
    UserLoginLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserLoginLog record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserLoginLog record);

    UserLoginLog selectByUserName(String username);

    int upsertSelective(UserLoginLog ua);

    UserLoginInfo selectUserWithDepartmentInfoH5ByUserName(String userName);

    UserLoginInfo selectUserWithDepartmentInfoPcByUserName(String userName);

    List<String> selectMerchantTypeCodeByRoleName(Map<String, Object> m);

    Map<String,Object> selectBaseInfoIdByNameAndUserId(Map<String, Object> m);
}