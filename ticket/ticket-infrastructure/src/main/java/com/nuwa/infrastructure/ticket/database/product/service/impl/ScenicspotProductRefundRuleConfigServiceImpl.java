package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProductRefundRuleConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.ScenicspotProductRefundRuleConfigMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductRefundRuleConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区产品退款规则配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class ScenicspotProductRefundRuleConfigServiceImpl extends SuperServiceImpl<ScenicspotProductRefundRuleConfigMapper, ScenicspotProductRefundRuleConfig> implements ScenicspotProductRefundRuleConfigService {

    @Autowired
    private ScenicspotProductRefundRuleConfigMapper scenicspotProductRefundRuleConfigMapper;

}
