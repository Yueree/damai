package com.example.distributedlock.redisson.impl;

import com.example.distributedlock.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @program: distribute-cache
 * @description: 分布式锁(公平锁)
 * @author: lk
 * @create: 2022-05-28
 **/
public class RedissonFairLocker implements DistributedLocker {

    private RedissonClient redissonClient;

    public RedissonFairLocker(RedissonClient redissonClient){
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit ,long leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
