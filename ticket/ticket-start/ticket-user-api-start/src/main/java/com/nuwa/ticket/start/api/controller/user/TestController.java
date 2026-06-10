package com.nuwa.ticket.start.api.controller.user;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.delay.redis.RedisDelayListWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(tags = {"test"})
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisDelayListWrapper redisDelayListWrapper;

    @ApiOperation(value = "publish")
    @GetMapping(value = "/publish")
    public SingleResponse<?> publish() {
        int delayTime;
        for (int i = 0; i < 10; i++) {
            delayTime = RandomUtil.randomInt(1000 * 10, 1000 * 30);
            String queue = "testQueue";
            String msg = queue + "#" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
            redisDelayListWrapper.publish(queue, msg, delayTime);
        }
        String test = redisDelayListWrapper.fetchOne("testQueue");
        System.out.println(test);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "consumer")
    @GetMapping(value = "/consumer")
    public SingleResponse<?> consumer() {
        for (int i = 0; i < 10; i++) {
            String test = redisDelayListWrapper.fetchOne("testQueue");
            System.out.println(test);
        }
        return SingleResponse.buildSuccess();
    }
}
