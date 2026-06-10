package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallMessage;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallMessageMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallMessageServiceImpl extends SuperServiceImpl<MallMessageMapper, MallMessage> implements MallMessageService {

    @Autowired
    private MallMessageMapper mallMessageMapper;

}
