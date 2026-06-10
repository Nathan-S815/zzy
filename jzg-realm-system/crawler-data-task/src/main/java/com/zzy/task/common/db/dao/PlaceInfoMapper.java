package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlaceInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(PlaceInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PlaceInfo record);

    /**
     *
     * @mbg.generated
     */
    PlaceInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlaceInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlaceInfo record);

    int batchInsertInfo(List<PlaceInfo> l);


    /**
     *    key_word = #{keyWord,jdbcType=VARCHAR}
     *         and place_type = #{placeType,jdbcType=VARCHAR}
     *         and place_source = #{placeSource,jdbcType=VARCHAR}
     * @param m
     * @return
     */
    List<PlaceInfo> selectHrefPlaceByMap(Map<String, Object> m);

    Set<String> selectPlaceNames(Map<String, Object> p);
}