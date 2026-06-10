package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallClassification;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallClassificationMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商城分类 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallClassificationServiceImpl extends SuperServiceImpl<MallClassificationMapper, MallClassification> implements MallClassificationService {

    @Autowired
    private MallClassificationMapper mallClassificationMapper;

}
