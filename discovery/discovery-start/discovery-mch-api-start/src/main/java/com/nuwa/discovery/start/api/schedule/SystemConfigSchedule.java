package com.nuwa.discovery.start.api.schedule;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuwa.infrastructure.discovery.database.system.entity.SystemConfig;
import com.nuwa.infrastructure.discovery.database.system.mapper.SystemConfigMapper;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SystemConfigSchedule {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Scheduled(cron = "30 59 23 * * ?")
    public void updateBusinessCount() {
        //更新商户累计数
        SystemConfig systemConfig = getSystemConfig("conf_key","business_count");
        int randomInt = RandomUtil.randomInt(1, 5);
        Integer count = Convert.toInt(systemConfig.getConfValue()) + randomInt;
        systemConfig.setConfValue(Convert.toStr(count));
        systemConfig.updateById();

        //更新当月商户新增数
        systemConfig = getSystemConfig("conf_key","business_incr_count");
        count = Convert.toInt(systemConfig.getConfValue()) + randomInt;
        systemConfig.setConfValue(Convert.toStr(count));
        systemConfig.updateById();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void reSetBusinessIncrCount() {
        //重置当月商户新增数
        SystemConfig systemConfig = getSystemConfig("conf_key","business_incr_count");
        systemConfig.setConfValue("0");
        systemConfig.updateById();

    }

    private SystemConfig getSystemConfig(String column, String val){
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,val);
        SystemConfig systemConfig = systemConfigMapper.selectOne(queryWrapper);
        return systemConfig;
    }
}
