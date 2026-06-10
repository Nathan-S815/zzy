package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotType;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotTypeMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotTypeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区分类表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Slf4j
@Service
public class ScenicspotTypeServiceImpl extends SuperServiceImpl<ScenicspotTypeMapper, ScenicspotType> implements ScenicspotTypeService {

    @Autowired
    private ScenicspotTypeMapper scenicspotTypeMapper;

}
