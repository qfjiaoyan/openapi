package com.qianfeng.openapi.cache.service.impl;

import com.qianfeng.openapi.cache.service.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("redissonLocker")
public class RedissonLockerImpl implements DistributedLocker {
    private final static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private RedissonClient redissonClient;

    /**
     *  尝试加锁，最多等待waitTime，上锁以后leaseTime自动解锁
     * @param lockKey   锁key
     * @param unit      锁时间单位
     * @param waitTime  等到最大时间
     * @param leaseTime 锁失效时间
     * @return 如果获取成功，则返回true，如果获取失败（即锁已被其他线程获取），则返回false
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        log.info("lock thread:{}",Thread.currentThread().getId());
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 释放锁
     * @param lockKey 锁key
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

}

