package com.nuwa.infrastructure.ticket.delayqueue.listener;

import com.nuwa.infrastructure.ticket.SpringContextKit;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayBucketHandler;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayQueue;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yang WenJie
 * @date 2018/1/27 下午10:15
 */
public class ApplicationStartup implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool((int) DelayQueue.DELAY_BUCKET_NUM);
        for (int i = 0; i < DelayQueue.DELAY_BUCKET_NUM; i++) {
            executorService.execute(new DelayBucketHandler(DelayQueue.DELAY_BUCKET_KEY_PREFIX + i));
        }
    }
}
