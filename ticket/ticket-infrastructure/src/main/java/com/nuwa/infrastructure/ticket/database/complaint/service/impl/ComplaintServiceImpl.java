package com.nuwa.infrastructure.ticket.database.complaint.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.mapper.ComplaintMapper;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户投诉 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Slf4j
@Service
public class ComplaintServiceImpl extends SuperServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

}
