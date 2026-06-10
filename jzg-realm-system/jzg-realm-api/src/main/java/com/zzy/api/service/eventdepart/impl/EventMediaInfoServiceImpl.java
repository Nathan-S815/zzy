package com.zzy.api.service.eventdepart.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.eventdepart.IEventMediaInfoService;
import com.zzy.db.dao.eventdepart.EventMediaInfoMapper;
import com.zzy.db.entity.eventdepart.EventMediaInfo;
import org.springframework.stereotype.Service;

@Service
public class EventMediaInfoServiceImpl extends ServiceImpl<EventMediaInfoMapper, EventMediaInfo> implements IEventMediaInfoService {
}
