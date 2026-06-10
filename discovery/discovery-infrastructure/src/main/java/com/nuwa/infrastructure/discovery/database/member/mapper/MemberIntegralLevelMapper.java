package com.nuwa.infrastructure.discovery.database.member.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;

import org.springframework.stereotype.Repository;


/**
 * 达人积分等级表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Repository
public interface MemberIntegralLevelMapper extends SuperMapper<MemberIntegralLevel> {

    public void incrCount (Long id);

    public void decrCount (Long id);
}
