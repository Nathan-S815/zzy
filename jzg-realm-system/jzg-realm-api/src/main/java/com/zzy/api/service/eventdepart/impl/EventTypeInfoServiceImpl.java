package com.zzy.api.service.eventdepart.impl;

import com.zzy.db.entity.eventdepart.EventTypeInfo;
import com.zzy.db.dao.eventdepart.EventTypeInfoMapper;
import com.zzy.api.service.eventdepart.IEventTypeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事件类型 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class EventTypeInfoServiceImpl extends ServiceImpl<EventTypeInfoMapper, EventTypeInfo> implements IEventTypeInfoService {

}
