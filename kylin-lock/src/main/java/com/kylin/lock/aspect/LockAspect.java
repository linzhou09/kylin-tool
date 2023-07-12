package com.kylin.lock.aspect;



import com.kylin.biz.utils.exception.BizException;
import com.kylin.lock.annotations.Lock;
import com.kylin.lock.model.enums.LockErrorResultCodeEnums;
import com.kylin.lock.model.result.BaseLockResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @date : 2022/5/20 13:51
 * @author: linzhou
 * @description : LockAspect
 */
@Aspect
@Order(2)
@Slf4j
public class LockAspect {
    @Resource
    private ApplicationContext applicationContext;

    @Around("@annotation(lock)")
    public Object lock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        if (Objects.isNull(lock)) {
            return joinPoint.proceed();
        }
        //获取分布式锁的key
        String[] redisKey = getRedisKey(joinPoint, lock);
        if (StringUtils.isEmpty(redisKey)) {
            log.error("未获取到分布式锁key,跳过加锁逻辑，redisKey：{}", redisKey);
            return joinPoint.proceed();
        }


        DistributedLock distributedLock = applicationContext.getBean(lock.lockType());

        LockContext lockContext = new LockContext(redisKey, lock.waitLockTime());
        BaseLockResult lockResultBo = distributedLock.lock(lockContext);
        try {
            if (Objects.isNull(lockResultBo)) {
                log.error("分布式锁获取失败，redisKey：{}", redisKey);
                throw new BizException(LockErrorResultCodeEnums.FREQUENT);
            } else {
                log.info("分布式锁获取成功，redisKey：{}", redisKey);
            }
            return joinPoint.proceed();
        } finally {
            if (Objects.nonNull(lockResultBo)) {
                distributedLock.unLock(lockResultBo);
                log.info("分布式锁释放成功，redisKey：{}", redisKey);
            }
        }
    }

    /**
     * 获取分布式锁的key
     *
     * @param joinPoint
     * @param lock
     * @return
     * @throws Throwable
     */
    private String[] getRedisKey(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        String[] key = lock.key();
        if (StringUtils.isEmpty(key)) {
            return key;
        }

        DistributedKey distributedKey = applicationContext.getBean(lock.lockKeyType());

        return distributedKey.getKey(joinPoint, lock);
    }

}
