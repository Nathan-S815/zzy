package com.nuwa.infrastructure.attract.database.common.service.impl;

import com.nuwa.infrastructure.attract.database.common.entity.CnRegionInfo;
import com.nuwa.infrastructure.attract.database.common.mapper.CnRegionInfoMapper;
import com.nuwa.infrastructure.attract.database.common.service.CnRegionInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 中国地区信息 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-13
 */
@Slf4j
@Service
public class CnRegionInfoServiceImpl extends SuperServiceImpl<CnRegionInfoMapper, CnRegionInfo> implements CnRegionInfoService {

    @Autowired
    private CnRegionInfoMapper cnRegionInfoMapper;

}
