package com.zzy.db.dao.reportresources;


import com.zzy.db.entity.eventdepart.reportresources.TableViewShare;

import java.util.List;
import java.util.Map;

public interface TableViewShareMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(TableViewShare record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(TableViewShare record);

    /**
     *
     * @mbg.generated
     */
    TableViewShare selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TableViewShare record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TableViewShare record);

    List<Map<String, Object>> selectTabWithNum();

    List<Map<String, Object>> selectAllRowColumn(Map<String, Object> m);

    List<Map<String, Object>> selectTableDetails(Map<String, Object> m);

    List<Map<String, Object>> selectTableRecords(Map<String, Object> m);

    Map<String, Object> selectFeildNumWithTableNum(Map<String, Object> m);

    Map<String, Object> selectFieldCnsName(Map<String, Object> m);

    int selectTableTypeCount();
}