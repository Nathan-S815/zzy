package com.nuwa.infrastructure.zeus.database.base.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;

import org.springframework.stereotype.Repository;


/**
 * 登录日志 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Repository
public interface LoginLogMapper extends SuperMapper<LoginLog> {


}
