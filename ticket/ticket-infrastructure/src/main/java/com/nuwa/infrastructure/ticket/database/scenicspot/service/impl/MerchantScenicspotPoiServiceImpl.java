package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.MerchantScenicspotPoi;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantScenicspotPoiMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.MerchantScenicspotPoiService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户关联景区POI表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-27
 */
@Slf4j
@Service
public class MerchantScenicspotPoiServiceImpl extends SuperServiceImpl<MerchantScenicspotPoiMapper, MerchantScenicspotPoi> implements MerchantScenicspotPoiService {

    @Autowired
    private MerchantScenicspotPoiMapper merchantScenicspotPoiMapper;

}
