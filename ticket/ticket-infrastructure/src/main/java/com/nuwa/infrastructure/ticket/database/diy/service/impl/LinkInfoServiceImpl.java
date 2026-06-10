package com.nuwa.infrastructure.ticket.database.diy.service.impl;

import com.nuwa.infrastructure.ticket.database.diy.entity.LinkInfo;
import com.nuwa.infrastructure.ticket.database.diy.mapper.LinkInfoMapper;
import com.nuwa.infrastructure.ticket.database.diy.service.LinkInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 装修链接 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-16
 */
@Slf4j
@Service
public class LinkInfoServiceImpl extends SuperServiceImpl<LinkInfoMapper, LinkInfo> implements LinkInfoService {

    @Autowired
    private LinkInfoMapper linkInfoMapper;

}
