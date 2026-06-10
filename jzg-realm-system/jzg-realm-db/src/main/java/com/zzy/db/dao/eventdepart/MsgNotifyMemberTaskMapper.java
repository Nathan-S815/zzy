package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.MsgNotifyMemberTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
public interface MsgNotifyMemberTaskMapper extends BaseMapper<MsgNotifyMemberTask> {

    List<Map<String, Object>> selectEventListByMsgTaskMember(Map<String, Object> m);

    int batchInsert(List<MsgNotifyMemberTask> ms);

    List<Map<String, Object>> selectMemberByEventWithDepartId(Map<String, Object> m);
}
