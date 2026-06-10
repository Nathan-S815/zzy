package com.zzy.api.service.eventdepart.impl;

import com.zzy.db.entity.eventdepart.EventDepartMember;
import com.zzy.db.dao.eventdepart.EventDepartMemberMapper;
import com.zzy.api.service.eventdepart.IEventDepartMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 事件部员关联 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class EventDepartMemberServiceImpl extends ServiceImpl<EventDepartMemberMapper, EventDepartMember> implements IEventDepartMemberService {


    @Autowired
    EventDepartMemberMapper eventDepartMemberMapper;

    @Override
    public EventDepartMember getByDepartIdWithEventId(Integer eventId, Integer departId) {
        Map<String,Integer> m = new HashMap<>();
        m.put("eventId", eventId);
        m.put("departId", eventId);
        return eventDepartMemberMapper.selectByDepartIdWithEventId(m);
    }
}
