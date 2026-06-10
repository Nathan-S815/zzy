package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneClientLink;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneClientLinkMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneClientLinkService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通功能链接配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-26
 */
@Slf4j
@Service
public class OneClientLinkServiceImpl extends SuperServiceImpl<OneClientLinkMapper, OneClientLink> implements OneClientLinkService {

    @Autowired
    private OneClientLinkMapper oneClientLinkMapper;

}
