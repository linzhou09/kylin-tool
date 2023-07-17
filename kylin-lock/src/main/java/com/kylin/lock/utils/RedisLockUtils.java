package com.kylin.lock.utils;

import com.kylin.biz.utils.model.bo.kylin.param.KylinParamInfo;
import com.kylin.biz.utils.string.StringFormatUtil;
import com.kylin.lock.core.lock.RedisDistributedLock;
import com.kylin.lock.model.param.LockContext;
import com.kylin.lock.model.result.RedisLockResult;
import com.kylin.spring.utils.utils.SpringUtil;

import java.util.List;
import java.util.Objects;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-07-17 14:45
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class RedisLockUtils {

    private static RedisDistributedLock redisDistributedLock;


    /**
     * 分布式加锁
     *
     * @param keyTemplateList    分布式锁的key模板
     * @param kylinParamInfoList 分布式锁的key模板中的参数
     * @return
     */
    public static RedisLockResult lock(List<String> keyTemplateList, List<KylinParamInfo> kylinParamInfoList) {

        return lock(keyTemplateList, kylinParamInfoList, 3L);
    }

    /**
     * 分布式加锁
     *
     * @param keyTemplateList    分布式锁的key模板
     * @param kylinParamInfoList 分布式锁的key模板中的参数
     * @return
     */
    public static RedisLockResult lock(List<String> keyTemplateList, List<KylinParamInfo> kylinParamInfoList, long waitLockTime) {

        List<String> keyList = StringFormatUtil.buildDistributedKeyList(keyTemplateList, kylinParamInfoList);

        RedisDistributedLock redisDistributedLock = getRedisDistributedLock();

        LockContext lockContext = new LockContext(keyList, waitLockTime);

        return redisDistributedLock.lock(lockContext);
    }

    /**
     * 分布式解锁
     *
     * @param lockResult
     */
    public static void unLock(RedisLockResult lockResult) {

        RedisDistributedLock redisDistributedLock = getRedisDistributedLock();
        redisDistributedLock.unLock(lockResult);

    }

    private static RedisDistributedLock getRedisDistributedLock() {
        if (Objects.isNull(redisDistributedLock)) {
            synchronized (RedisLockUtils.class) {
                if (Objects.isNull(redisDistributedLock)) {
                    redisDistributedLock = SpringUtil.getBean(RedisDistributedLock.class);
                }
            }
        }
        return redisDistributedLock;
    }


}
