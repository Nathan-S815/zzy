package com.nuwa.infrastructure.zeus.database.sms.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;

import org.springframework.stereotype.Repository;


/**
 * 短信验证码表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Repository
public interface SmsCodeMapper extends SuperMapper<SmsCode> {


}
