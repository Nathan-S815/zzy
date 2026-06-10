package com.nuwa.zeus.start.api.schedule;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 应用服务器周期过期处理
 *
 * @author hy
 */
@Component
@Slf4j
public class AppServerSchedule {

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @Scheduled(fixedDelay = 5 * 1000)
    public void updateAppServerStatus() {
        log.info(">>>> updateAppServerStatus");
        merchantAppServerService.lambdaUpdate()
                .set(MerchantAppServer::getStatus, 0)
                .le(MerchantAppServer::getEndTime, DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -1)))
                .update();
        log.info("<<<< updateAppServerStatus");
    }
}
