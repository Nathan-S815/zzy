package com.nuwa.infrastructure.ticket.database.mchconfig.service.impl;

import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchTip;
import com.nuwa.infrastructure.ticket.database.mchconfig.mapper.SearchTipMapper;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchTipService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 搜索提示配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-09-15
 */
@Slf4j
@Service
public class SearchTipServiceImpl extends SuperServiceImpl<SearchTipMapper, SearchTip> implements SearchTipService {

    @Autowired
    private SearchTipMapper searchTipMapper;

}
