package com.zzy.task.common.db.dao;

import com.zzy.task.common.db.entity.PlaceCommentContentKeyWord;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlaceCommentContentKeyWordMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(PlaceCommentContentKeyWord record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PlaceCommentContentKeyWord record);

    /**
     *
     * @mbg.generated
     */
    PlaceCommentContentKeyWord selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlaceCommentContentKeyWord record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlaceCommentContentKeyWord record);

    int batchInsertInfo(List<PlaceCommentContentKeyWord> li2);

    Set<String> selectBaseNamesByMap(Map<String, Object> m);
}