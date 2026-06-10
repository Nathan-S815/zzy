package com.nuwa.infrastructure.discovery.database.sms.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsAlert;

import org.springframework.stereotype.Repository;


/**
 * 短信提醒表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-30
 */
@Repository
public interface SmsAlertMapper extends SuperMapper<SmsAlert> {


}
