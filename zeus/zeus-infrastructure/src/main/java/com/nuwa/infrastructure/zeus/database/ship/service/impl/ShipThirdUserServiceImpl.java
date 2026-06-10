package com.nuwa.infrastructure.zeus.database.ship.service.impl;

import com.nuwa.infrastructure.zeus.database.ship.entity.ShipThirdUser;
import com.nuwa.infrastructure.zeus.database.ship.mapper.ShipThirdUserMapper;
import com.nuwa.infrastructure.zeus.database.ship.service.ShipThirdUserService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 跳转第三方商户用户信息表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-10
 */
@Slf4j
@Service
public class ShipThirdUserServiceImpl extends SuperServiceImpl<ShipThirdUserMapper, ShipThirdUser> implements ShipThirdUserService {

    @Autowired
    private ShipThirdUserMapper shipThirdUserMapper;

}
