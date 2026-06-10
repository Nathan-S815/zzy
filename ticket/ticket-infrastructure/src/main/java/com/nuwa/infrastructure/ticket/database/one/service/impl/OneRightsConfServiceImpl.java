package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneRightsConf;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneRightsConfMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneRightsConfService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通商户端权益配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Slf4j
@Service
public class OneRightsConfServiceImpl extends SuperServiceImpl<OneRightsConfMapper, OneRightsConf> implements OneRightsConfService {

    @Autowired
    private OneRightsConfMapper oneRightsConfMapper;

}
