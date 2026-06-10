package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.CnRegionInfo;
import com.nuwa.infrastructure.zeus.database.base.mapper.CnRegionInfoMapper;
import com.nuwa.infrastructure.zeus.database.base.service.CnRegionInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
