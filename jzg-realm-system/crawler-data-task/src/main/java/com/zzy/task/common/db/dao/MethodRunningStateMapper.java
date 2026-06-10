package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.MethodRunningState;

public interface MethodRunningStateMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(MethodRunningState record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(MethodRunningState record);

    /**
     *
     * @mbg.generated
     */
    MethodRunningState selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MethodRunningState record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MethodRunningState record);

    MethodRunningState selectBySource(String meituan);

    int updateBySource(MethodRunningState tmp);
}