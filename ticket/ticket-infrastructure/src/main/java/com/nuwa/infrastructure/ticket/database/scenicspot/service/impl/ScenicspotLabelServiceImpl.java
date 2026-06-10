package com.nuwa.infrastructure.ticket.database.scenicspot.service.impl;

import com.nuwa.infrastructure.ticket.database.scenicspot.entity.ScenicspotLabel;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotLabelMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotLabelService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区标签表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Slf4j
@Service
public class ScenicspotLabelServiceImpl extends SuperServiceImpl<ScenicspotLabelMapper, ScenicspotLabel> implements ScenicspotLabelService {

    @Autowired
    private ScenicspotLabelMapper scenicspotLabelMapper;

}
