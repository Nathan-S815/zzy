package com.nuwa.infrastructure.ticket.database.activity.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.database.activity.mapper.CultureActivityMapper;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文化活动 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-08
 */
@Slf4j
@Service
public class CultureActivityServiceImpl extends SuperServiceImpl<CultureActivityMapper, CultureActivity> implements CultureActivityService {

    @Autowired
    private CultureActivityMapper cultureActivityMapper;

}
