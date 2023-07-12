package com.kylin.lock.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linzhou
 * @ClassName Lock.java
 * @createTime 2021年12月17日 15:23:00
 * @Description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    /**
     * 分布式锁的key
     *
     * @return
     */
    String[] key();

    /**
     * 获取分布式锁等待时间 单位秒
     *
     * @return
     */
    long waitLockTime() default 2;

    /**
     * 加锁方式 默认使用redis分布式锁
     *
     * @return
     */
    Class<? extends DistributedLock> lockType() default RedisDistributedLock.class;

    /**
     * 获取分布式锁key的规则
     *
     * @return
     */
    Class<? extends DistributedKey> lockKeyType() default DefaultDistributedKey.class;

}
