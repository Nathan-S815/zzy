package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.RestaurantCommentInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RestaurantCommentInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(RestaurantCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(RestaurantCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    RestaurantCommentInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RestaurantCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(RestaurantCommentInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RestaurantCommentInfo record);

    int batchInsertInfo(List<RestaurantCommentInfo> li2);

    Set<String> selectUnGetCommentsByMap(Map<String, Object> m);

    Set<String> selectBaseNamesByMap(Map<String, Object> m);

    int batchInsertPutuoInfo(List<RestaurantCommentInfo> li2);

    RestaurantCommentInfo selectNewestCommentInfo();
}