package com.nuwa.infrastructure.zeus.database.other.service.impl;

import com.nuwa.infrastructure.zeus.database.other.entity.AppApply;
import com.nuwa.infrastructure.zeus.database.other.mapper.AppApplyMapper;
import com.nuwa.infrastructure.zeus.database.other.service.AppApplyService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * app试用申请 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-07-27
 */
@Slf4j
@Service
public class AppApplyServiceImpl extends SuperServiceImpl<AppApplyMapper, AppApply> implements AppApplyService {

    @Autowired
    private AppApplyMapper appApplyMapper;

}
