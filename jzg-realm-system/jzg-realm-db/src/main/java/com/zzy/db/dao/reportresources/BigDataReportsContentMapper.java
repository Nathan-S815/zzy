package com.zzy.db.dao.reportresources;


import com.zzy.db.entity.eventdepart.reportresources.BigDataReportsContent;

public interface BigDataReportsContentMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer ptBigDataReportsId);

    /**
     *
     * @mbg.generated
     */
    int insert(BigDataReportsContent record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(BigDataReportsContent record);

    /**
     *
     * @mbg.generated
     */
    BigDataReportsContent selectByPrimaryKey(Integer ptBigDataReportsId);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BigDataReportsContent record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(BigDataReportsContent record);
}