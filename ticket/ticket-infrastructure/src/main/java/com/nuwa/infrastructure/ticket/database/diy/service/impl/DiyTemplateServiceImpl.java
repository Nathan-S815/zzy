package com.nuwa.infrastructure.ticket.database.diy.service.impl;

import com.nuwa.infrastructure.ticket.database.diy.entity.DiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.mapper.DiyTemplateMapper;
import com.nuwa.infrastructure.ticket.database.diy.service.DiyTemplateService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 装修模板 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-11
 */
@Slf4j
@Service
public class DiyTemplateServiceImpl extends SuperServiceImpl<DiyTemplateMapper, DiyTemplate> implements DiyTemplateService {

    @Autowired
    private DiyTemplateMapper diyTemplateMapper;

}
