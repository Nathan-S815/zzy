package com.zzy.api.service.eventdepart.impl;

import com.zzy.db.entity.eventdepart.EventPunish;
import com.zzy.db.dao.eventdepart.EventPunishMapper;
import com.zzy.api.service.eventdepart.IEventPunishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
@Service
public class EventPunishServiceImpl extends ServiceImpl<EventPunishMapper, EventPunish> implements IEventPunishService {


    @Autowired
    private EventPunishMapper eventPunishMapper;

    @Override
    public List<Map<String, Object>> getPunishDepartInfoByEventId(Integer eventId) {
        return eventPunishMapper.selectPunishDepartInfoByEventId(eventId);
    }
}
