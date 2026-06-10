package com.nuwa.infrastructure.ticket.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁基类
 * 
 * @author hy
 * @date 2020/11/26 13:51
 * @since 1.0.0
 */
public interface DistributedLocker {

  RLock lock(String lockKey);

  RLock lock(String lockKey, int timeout);

  RLock lock(String lockKey, TimeUnit unit, int timeout);

  boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

  void unlock(String lockKey);

  void unlock(RLock lock);

}
