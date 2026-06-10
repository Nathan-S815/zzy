package com.nuwa.infrastructure.ticket.database.callcenter.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.TalkManage;
import com.nuwa.infrastructure.ticket.database.callcenter.mapper.TalkManageMapper;
import com.nuwa.infrastructure.ticket.database.callcenter.service.TalkManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会话管理 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-15
 */
@Slf4j
@Service
public class TalkManageServiceImpl extends SuperServiceImpl<TalkManageMapper, TalkManage> implements TalkManageService {

    @Autowired
    private TalkManageMapper talkManageMapper;

}
