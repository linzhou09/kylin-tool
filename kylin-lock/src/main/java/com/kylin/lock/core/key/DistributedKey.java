package com.kylin.lock.core.key;

import com.kylin.lock.annotations.Lock;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * @date : 2022/5/20 11:27
 * @author: linzhou
 * @description : 分布式锁的key的解析类
 */
public interface DistributedKey{

  /**
   * 获取分布式锁的key
   *
   * @param joinPoint
   * @param lock
   * @return 分布式锁的key
   */
  List<String> getKey(ProceedingJoinPoint joinPoint, Lock lock) throws IllegalAccessException;
}
