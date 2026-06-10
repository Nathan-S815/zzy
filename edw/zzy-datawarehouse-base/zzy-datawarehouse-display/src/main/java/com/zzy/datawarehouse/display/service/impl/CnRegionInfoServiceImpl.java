package com.zzy.datawarehouse.display.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.CnRegionInfo;
import com.zzy.datawarehouse.display.mapper.CnRegionInfoMapper;
import com.zzy.datawarehouse.display.service.CnRegionInfoService;
import org.springframework.stereotype.Service;

@Service("cnRegionInfoService")
public class CnRegionInfoServiceImpl extends ServiceImpl<CnRegionInfoMapper, CnRegionInfo> implements CnRegionInfoService {


}