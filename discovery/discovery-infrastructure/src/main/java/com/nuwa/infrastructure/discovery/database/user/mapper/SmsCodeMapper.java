package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.SmsCode;

import org.springframework.stereotype.Repository;


/**
 * 短信验证码表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-13
 */
@Repository
public interface SmsCodeMapper extends SuperMapper<SmsCode> {


}
