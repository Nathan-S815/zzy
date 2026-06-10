package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.EventFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 事件执法反馈 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface EventFeedbackMapper extends BaseMapper<EventFeedback> {

    Map<String, Object> selectFeedbackDetailByIds(Map<String, Object> m);

    Map<String, Object> selectCheckedFeedbackDetailByEventId(Map<String, Object> m);
}
