package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.eventdepart.EventDepartMember;

/**
 * <p>
 * 事件部员关联 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IEventDepartMemberService extends IService<EventDepartMember> {

    EventDepartMember getByDepartIdWithEventId(Integer eventId, Integer departId);
}
