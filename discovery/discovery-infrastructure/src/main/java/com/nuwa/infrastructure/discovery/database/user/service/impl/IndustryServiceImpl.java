package com.nuwa.infrastructure.discovery.database.user.service.impl;


import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.mapper.IndustryMapper;
import com.nuwa.infrastructure.discovery.database.user.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/17
 * @Description: TODO
 */

@Service
public class IndustryServiceImpl extends SuperServiceImpl<IndustryMapper, Industry> implements IndustryService {

    @Autowired
    private IndustryMapper industryMapper;
}
