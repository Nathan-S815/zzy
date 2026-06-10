package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.HotelCommentInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HotelCommentInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(HotelCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(HotelCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    HotelCommentInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(HotelCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(HotelCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HotelCommentInfo record);

    int batchInsertInfo(List<HotelCommentInfo> li2);

    Set<String> selectUnGetCommentsByMap(Map<String, Object> m);

    Set<String> selectUnGetCommentsLvmama(Map<String, Object> m);

    Set<String> selectBaseNamesByMap(Map<String, Object> m);

    int batchInsertPutuoInfo(List<HotelCommentInfo> li2);

    HotelCommentInfo selectNewestCommentInfo();
}