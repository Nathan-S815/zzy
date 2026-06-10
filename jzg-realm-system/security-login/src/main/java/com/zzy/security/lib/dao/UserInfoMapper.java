package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(UserInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(UserInfo record);

    /**
     *
     * @mbg.generated
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUsrename(String username);

    int insertUser(UserInfo ui);

    List<Map<String, Object>> selectAllSystemUrls(String username);

    List<Map<String, Object>> selectUserPages(Map<String, Object> m);

    int updateUserByNameSelective(UserInfo u);

    int batchInsert(List<UserInfo> list);

    List<UserInfo> selectByUsrenames(@Param("names") List<String> names);

    int insertStudentCacheId(UserInfo u);
}