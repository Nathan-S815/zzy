package com.nuwa.infrastructure.ticket.database.activity.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.database.activity.mapper.CultureActivityCategoryMapper;
import com.nuwa.infrastructure.ticket.database.activity.service.CultureActivityCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动类别 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-08-16
 */
@Slf4j
@Service
public class CultureActivityCategoryServiceImpl extends SuperServiceImpl<CultureActivityCategoryMapper, CultureActivityCategory> implements CultureActivityCategoryService {

    @Autowired
    private CultureActivityCategoryMapper cultureActivityCategoryMapper;

}
