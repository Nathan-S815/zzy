package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.EventInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 事件任务表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface EventInfoMapper extends BaseMapper<EventInfo> {

    List<Map<String, Object>> selectPageEventList(Map<String, Object> m);

    Map<String, Object> selectEventDetailByeventId(Integer eventId);


    /**
     * 获取本部门的一级上报列表
     * @param m
     * @return
     */
    List<Map<String, Object>> selectDepartmentTaskList(Map<String, Object> m);

    List<Map<String, Object>> selectAssignedTaskList(Map<String, Object> m);

    int insertCatchId(EventInfo eventInfo);

    /**
     * 获取创建事件时间，上报人
     * */
    List<Map<String, Object>> getschedule1(@Param("id") Integer id);
    /**
     * 获取认领时间，部门名称 部门负责人名称
     * */
    List<Map<String, Object>> getschedule2(@Param("id") Integer id);
    /**
     *获取处理时间，处理小队员名字，职位
     * */
    List<Map<String, Object>> getschedule3(@Param("id") Integer id);
    /**
     * 获取反馈时间，审核时间
     * */
    List<Map<String, Object>> getschedule4(@Param("id") Integer id);

    List<Map<String, Object>> selectPageRedoEventList(Map<String, Object> m);
}
