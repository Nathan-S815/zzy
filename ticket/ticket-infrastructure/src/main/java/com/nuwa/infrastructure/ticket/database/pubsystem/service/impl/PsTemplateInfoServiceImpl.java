package com.nuwa.infrastructure.ticket.database.pubsystem.service.impl;

import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.mapper.PsTemplateInfoMapper;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝小程序模板 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-25
 */
@Slf4j
@Service
public class PsTemplateInfoServiceImpl extends SuperServiceImpl<PsTemplateInfoMapper, PsTemplateInfo> implements PsTemplateInfoService {

    @Autowired
    private PsTemplateInfoMapper psTemplateInfoMapper;

}
