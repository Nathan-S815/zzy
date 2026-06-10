package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallAccessTokenConfig;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallAccessTokenConfigMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallAccessTokenConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallAccessTokenConfigServiceImpl extends SuperServiceImpl<MallAccessTokenConfigMapper, MallAccessTokenConfig> implements MallAccessTokenConfigService {

    @Autowired
    private MallAccessTokenConfigMapper mallAccessTokenConfigMapper;

}
