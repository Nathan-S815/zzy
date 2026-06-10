package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.TouristInfo;
import com.nuwa.infrastructure.ticket.database.order.mapper.TouristInfoMapper;
import com.nuwa.infrastructure.ticket.database.order.service.TouristInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 游客信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-15
 */
@Slf4j
@Service
public class TouristInfoServiceImpl extends SuperServiceImpl<TouristInfoMapper, TouristInfo> implements TouristInfoService {

    @Autowired
    private TouristInfoMapper touristInfoMapper;

}
