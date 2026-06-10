package com.zzy.api.service.eventdepart.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.eventdepart.IMsgNotifyMemberTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.eventdepart.MsgNotifyMemberTaskMapper;
import com.zzy.db.entity.eventdepart.MsgNotifyMemberTask;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
@Service
public class MsgNotifyMemberTaskServiceImpl extends ServiceImpl<MsgNotifyMemberTaskMapper, MsgNotifyMemberTask> implements IMsgNotifyMemberTaskService {


    @Autowired
    MsgNotifyMemberTaskMapper msgNotifyMemberTaskMapper;


    @Override
    public PageInfo<Map<String, Object>> getEventListByMsgTaskMember(Integer pageNo, Integer pageSize, Map<String, Object> m) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> l = msgNotifyMemberTaskMapper.selectEventListByMsgTaskMember(m);
        return new PageInfo<>(l);
    }


    @Transactional(transactionManager = "primaryTransactionManager")
    @Override
    public boolean batchInsert(List<MsgNotifyMemberTask> ms) {
        return msgNotifyMemberTaskMapper.batchInsert(ms)>0;
    }

    @Override
    public List<Map<String, Object>> getMemberByEventWithDepartId(Integer eventId, String departId) {
        String[] departIds = departId.split(",");
        Set<Integer> sts = Arrays.asList(departIds).stream().map(o-> Integer.parseInt(o)).collect(Collectors.toSet());
        Map<String,Object> m = new HashMap<>();
        m.put("eventId",eventId);
        m.put("departId",sts);
        return msgNotifyMemberTaskMapper.selectMemberByEventWithDepartId(m);
    }
}
