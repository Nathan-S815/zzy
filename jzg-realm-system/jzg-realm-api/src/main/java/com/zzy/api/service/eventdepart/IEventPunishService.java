package com.zzy.api.service.eventdepart;

import com.zzy.db.entity.eventdepart.EventPunish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
public interface IEventPunishService extends IService<EventPunish> {

    List<Map<String, Object>> getPunishDepartInfoByEventId(Integer eventId);
}
