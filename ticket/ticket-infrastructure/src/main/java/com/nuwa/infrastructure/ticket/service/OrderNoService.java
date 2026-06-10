package com.nuwa.infrastructure.ticket.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 生成订单号
 *
 * @author hy
 */
@Service
@Slf4j
public class OrderNoService {
    private long workerId = 0;

    @PostConstruct
    void init() {
        workerId = RandomUtil.randomLong(0, 10);
    }

    private final Snowflake snowflake = IdUtil.createSnowflake(workerId, 1);

    /**
     * 获取一个批次号，形如 2019071015301361000101237
     * <p>
     * 数据库使用 char(25) 存储
     *
     * @param tenantId 租户ID，5 位
     * @param module   业务模块ID，2 位
     * @return 返回批次号
     */
    public synchronized String batchId(int tenantId, int module) {
        String prefix = DateTime.now().toString(DatePattern.PURE_DATETIME_MS_PATTERN);
        return prefix + tenantId + module + RandomUtil.randomNumbers(3);
    }

    @Deprecated
    public synchronized String getBatchId(int tenantId, int module) {
        return batchId(tenantId, module);
    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    private synchronized long snowflakeId(long workerId, long dataCenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
        return snowflake.nextId();
    }

    public synchronized long getDateTime(long id) {
        return snowflake.getGenerateDateTime(id);
    }
}
