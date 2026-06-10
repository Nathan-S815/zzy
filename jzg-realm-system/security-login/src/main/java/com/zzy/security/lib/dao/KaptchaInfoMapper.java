package com.zzy.security.lib.dao;

import com.zzy.security.lib.entity.KaptchaInfo;

public interface KaptchaInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(KaptchaInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(KaptchaInfo record);

    /**
     *
     * @mbg.generated
     */
    KaptchaInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(KaptchaInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(KaptchaInfo record);

    KaptchaInfo selectByToken(String token);
}