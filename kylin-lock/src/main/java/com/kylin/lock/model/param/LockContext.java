package com.kylin.lock.model.param;

import java.util.List;

/**
 * @date : 2022/5/20 11:37
 * @author: linzhou
 * @description : LockParam
 */
public class LockContext {
  /**
   * 分布式锁key
   */
  private List<String> distributedLockKeyList;

  /**
   * 加锁等待时间
   */
  private long waitLockTime;

  public LockContext(List<String> distributedLockKeyList, long waitLockTime) {
    this.distributedLockKeyList = distributedLockKeyList;
    this.waitLockTime = waitLockTime;
  }

  public List<String> getDistributedLockKeyList() {
    return distributedLockKeyList;
  }

  public void setDistributedLockKeyList(List<String> distributedLockKeyList) {
    this.distributedLockKeyList = distributedLockKeyList;
  }

  public long getWaitLockTime() {
    return waitLockTime;
  }

  public void setWaitLockTime(long waitLockTime) {
    this.waitLockTime = waitLockTime;
  }
}
