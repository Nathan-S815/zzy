package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.EventDepartMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 事件部员关联 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface EventDepartMemberMapper extends BaseMapper<EventDepartMember> {

    boolean batchInsert(List<EventDepartMember> list);

    EventDepartMember selectByPk(Map<String, Integer> m);

    EventDepartMember selectByDepartIdWithEventId(Map<String, Integer> m);
}
