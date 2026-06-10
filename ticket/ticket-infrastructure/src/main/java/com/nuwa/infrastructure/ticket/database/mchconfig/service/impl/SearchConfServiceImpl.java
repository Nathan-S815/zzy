package com.nuwa.infrastructure.ticket.database.mchconfig.service.impl;

import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.mapper.SearchConfMapper;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchConfService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 搜索配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-09-06
 */
@Slf4j
@Service
public class SearchConfServiceImpl extends SuperServiceImpl<SearchConfMapper, SearchConf> implements SearchConfService {

    @Autowired
    private SearchConfMapper searchConfMapper;

}
