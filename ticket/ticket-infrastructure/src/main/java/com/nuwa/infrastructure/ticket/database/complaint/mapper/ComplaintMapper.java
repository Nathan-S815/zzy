package com.nuwa.infrastructure.ticket.database.complaint.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import org.springframework.stereotype.Repository;


/**
 * 用户投诉 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Repository
public interface ComplaintMapper extends SuperMapper<Complaint> {


}
