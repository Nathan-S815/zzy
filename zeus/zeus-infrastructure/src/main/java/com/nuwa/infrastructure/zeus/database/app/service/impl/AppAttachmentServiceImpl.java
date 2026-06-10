package com.nuwa.infrastructure.zeus.database.app.service.impl;

import com.nuwa.infrastructure.zeus.database.app.entity.AppAttachment;
import com.nuwa.infrastructure.zeus.database.app.mapper.AppAttachmentMapper;
import com.nuwa.infrastructure.zeus.database.app.service.AppAttachmentService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Slf4j
@Service
public class AppAttachmentServiceImpl extends SuperServiceImpl<AppAttachmentMapper, AppAttachment> implements AppAttachmentService {

    @Autowired
    private AppAttachmentMapper appAttachmentMapper;

}
