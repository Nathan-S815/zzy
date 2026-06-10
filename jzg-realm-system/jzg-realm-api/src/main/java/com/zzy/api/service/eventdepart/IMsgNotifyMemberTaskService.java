package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.eventdepart.MsgNotifyMemberTask;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
public interface IMsgNotifyMemberTaskService extends IService<MsgNotifyMemberTask> {

    PageInfo<Map<String, Object>> getEventListByMsgTaskMember(Integer pageNo, Integer pageSize, Map<String, Object> m);

    boolean batchInsert(List<MsgNotifyMemberTask> ms);

    List<Map<String, Object>> getMemberByEventWithDepartId(Integer eventId, String departIds);
}
