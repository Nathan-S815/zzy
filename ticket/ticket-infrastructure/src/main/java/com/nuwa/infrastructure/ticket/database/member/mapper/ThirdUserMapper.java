package com.nuwa.infrastructure.ticket.database.member.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;

import org.springframework.stereotype.Repository;


/**
 * 三方账户表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-12-13
 */
@Repository
public interface ThirdUserMapper extends SuperMapper<ThirdUser> {


}
