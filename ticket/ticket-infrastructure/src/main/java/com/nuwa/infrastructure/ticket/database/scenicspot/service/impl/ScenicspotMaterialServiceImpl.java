package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotMaterial;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotMaterialMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotMaterialService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区图文表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Slf4j
@Service
public class ScenicspotMaterialServiceImpl extends SuperServiceImpl<ScenicspotMaterialMapper, ScenicspotMaterial> implements ScenicspotMaterialService {

    @Autowired
    private ScenicspotMaterialMapper scenicspotMaterialMapper;

}
