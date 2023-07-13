package com.kylin.lock.core.lock;


import com.kylin.lock.model.param.LockContext;
import com.kylin.lock.model.result.RedisLockResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @date : 2022/5/26 16:55
 * @author: linzhou
 * @description : RedisDistributedLock
 */
@Component
public class RedisDistributedLock implements DistributedLock<RedisLockResult> {

    @Resource
    private RedissonClient redissonClient;

    public RedisDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RedisLockResult lock(LockContext lockContext) {
        try {
            RLock lock = redissonClient.getLock(lockContext.getDistributedLockKey());
            if (!lock.tryLock(lockContext.getWaitLockTime(), TimeUnit.SECONDS)) {
                return null;
            }
            return buildRedisLockResult(lockContext, lock);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 构建分布式锁返回类
     *
     * @param lockContext
     * @param lock
     * @return
     */
    private RedisLockResult buildRedisLockResult(LockContext lockContext, RLock lock) {
        RedisLockResult redisLockResult = new RedisLockResult();
        redisLockResult.setLockContext(lockContext);
        Thread thread = Thread.currentThread();
        redisLockResult.setThreadId(thread.getId());
        redisLockResult.setThreadName(thread.getName());
        redisLockResult.setLock(lock);
        return redisLockResult;
    }

    @Override
    public void unLock(RedisLockResult lockResult) {
        if (Objects.isNull(lockResult) || Objects.isNull(lockResult.getLock()) ||
                !(lockResult.getLock().isLocked() && lockResult.getLock().isHeldByCurrentThread())) {
            return;
        }
        lockResult.getLock().unlock();
    }
}
