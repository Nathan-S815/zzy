package com.nuwa.infrastructure.zeus.database.app.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.zeus.database.app.entity.AppUpdateLog;

import org.springframework.stereotype.Repository;


/**
 * app升级日志 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-06-27
 */
@Repository
public interface AppUpdateLogMapper extends SuperMapper<AppUpdateLog> {


}
