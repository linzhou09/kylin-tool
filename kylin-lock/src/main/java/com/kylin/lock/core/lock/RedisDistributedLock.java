package com.kylin.lock.core.lock;


import com.kylin.lock.model.param.LockContext;
import com.kylin.lock.model.result.RedisLockResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @date : 2022/5/26 16:55
 * @author: linzhou
 * @description : RedisDistributedLock
 */
@Component
@Slf4j
public class RedisDistributedLock implements DistributedLock<RedisLockResult> {

    @Resource
    private RedissonClient redissonClient;

    public RedisDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RedisLockResult lock(LockContext lockContext) {
        try {
            if (Objects.isNull(lockContext)|| CollectionUtils.isEmpty(lockContext.getDistributedLockKeyList())) {
                return null;
            }

            RLock lock = getLock(lockContext);
            if (Objects.isNull(lock)) {
                return null;
            }
            if (!lock.tryLock(lockContext.getWaitLockTime(), TimeUnit.SECONDS)) {
                return null;
            }
            return buildRedisLockResult(lockContext, lock);
        } catch (Exception e) {
            log.error("RedisDistributedLock lock error,keys:{}", lockContext.getDistributedLockKeyList(), e);
            return null;
        }
    }

    private RLock getLock(LockContext lockContext) {
        List<String> distributedLockKeyList = lockContext.getDistributedLockKeyList();

        RLock[] lockList = new RLock[distributedLockKeyList.size()];

        for (int i = 0; i < distributedLockKeyList.size(); i++) {
            RLock lock = redissonClient.getLock(distributedLockKeyList.get(i));
            lockList[i] = lock;
        }

        if (lockList.length == 1) {
            return lockList[0];
        }

        return redissonClient.getMultiLock(lockList);
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
    public boolean unLock(RedisLockResult lockResult) {
        RLock rLock = Optional.ofNullable(lockResult)
                .map(RedisLockResult::getLock)
                .orElse(null);
        if (Objects.isNull(rLock) || rLock.isHeldByCurrentThread()) {
            log.error("RedisDistributedLock unLock non execution,lockResult:{},isHeldByCurrentThread:{}", lockResult, Objects.nonNull(rLock) ? rLock.isHeldByCurrentThread() : null);
            return false;
        }
        rLock.unlock();
        return true;
    }
}
