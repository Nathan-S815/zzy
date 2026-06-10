package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.ScenicCommentInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ScenicCommentInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(ScenicCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(ScenicCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    ScenicCommentInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ScenicCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(ScenicCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ScenicCommentInfo record);

    int batchInsertInfo(List<ScenicCommentInfo> li2);

//    Set<String> selectUnGetCommentsByMap(Map<String, Object> m);

    Set<String> selectBaseNamesByMap(Map<String, Object> m);

    Set<String> selectUncommentPlaces(Map<String, Object> m);

    int batchInsertPutuoInfo(List<ScenicCommentInfo> li2);

    ScenicCommentInfo selectNewestCommentInfo();
}