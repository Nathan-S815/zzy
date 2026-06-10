package com.nuwa.infrastructure.discovery.database.appconfig.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.appconfig.entity.AppConfig;

import org.springframework.stereotype.Repository;


/**
 * 三方app对应配置 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-09-29
 */
@Repository
public interface AppConfigMapper extends SuperMapper<AppConfig> {


}
