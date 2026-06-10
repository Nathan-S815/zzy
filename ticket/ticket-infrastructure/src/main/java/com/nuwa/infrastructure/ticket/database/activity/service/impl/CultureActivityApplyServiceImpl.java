package com.nuwa.infrastructure.ticket.database.activity.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import com.nuwa.infrastructure.ticket.database.activity.mapper.CultureActivityApplyMapper;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文化活动报名 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-08
 */
@Slf4j
@Service
public class CultureActivityApplyServiceImpl extends SuperServiceImpl<CultureActivityApplyMapper, CultureActivityApply> implements CultureActivityApplyService {

    @Autowired
    private CultureActivityApplyMapper cultureActivityApplyMapper;

}
