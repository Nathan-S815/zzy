package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotBaseType;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotBaseTypeMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotBaseTypeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区类型表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Slf4j
@Service
public class ScenicspotBaseTypeServiceImpl extends SuperServiceImpl<ScenicspotBaseTypeMapper, ScenicspotBaseType> implements ScenicspotBaseTypeService {

    @Autowired
    private ScenicspotBaseTypeMapper scenicspotBaseTypeMapper;

}
