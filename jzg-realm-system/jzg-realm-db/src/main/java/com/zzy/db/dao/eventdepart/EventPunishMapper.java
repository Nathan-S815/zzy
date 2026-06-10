package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.EventPunish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
public interface EventPunishMapper extends BaseMapper<EventPunish> {

    List<Map<String, Object>> selectPunishDepartInfoByEventId(Integer eventId);
}
