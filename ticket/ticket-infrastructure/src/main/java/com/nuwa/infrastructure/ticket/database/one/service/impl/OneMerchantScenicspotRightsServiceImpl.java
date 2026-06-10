package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantScenicspotRights;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneMerchantScenicspotRightsMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantScenicspotRightsService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通商户景区权益配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Slf4j
@Service
public class OneMerchantScenicspotRightsServiceImpl extends SuperServiceImpl<OneMerchantScenicspotRightsMapper, OneMerchantScenicspotRights> implements OneMerchantScenicspotRightsService {

    @Autowired
    private OneMerchantScenicspotRightsMapper oneMerchantScenicspotRightsMapper;

}
