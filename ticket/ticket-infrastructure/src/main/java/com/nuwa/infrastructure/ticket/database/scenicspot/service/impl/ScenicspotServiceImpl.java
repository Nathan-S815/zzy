package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区poi 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-26
 */
@Slf4j
@Service
public class ScenicspotServiceImpl extends SuperServiceImpl<ScenicspotMapper, Scenicspot> implements ScenicspotService {

    @Autowired
    private ScenicspotMapper scenicspotMapper;

}
