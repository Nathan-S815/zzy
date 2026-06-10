package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.entity.CnRegionInfo;
import com.nuwa.infrastructure.discovery.database.user.mapper.CnRegionInfoMapper;
import com.nuwa.infrastructure.discovery.database.user.service.CnRegionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 中国地区信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-09
 */
@Slf4j
@Service
public class CnRegionInfoServiceImpl extends SuperServiceImpl<CnRegionInfoMapper, CnRegionInfo> implements CnRegionInfoService {

    @Autowired
    private CnRegionInfoMapper cnRegionInfoMapper;

}
