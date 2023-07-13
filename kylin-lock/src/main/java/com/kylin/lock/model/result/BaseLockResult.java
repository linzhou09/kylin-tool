package com.kylin.lock.model.result;



import com.kylin.lock.model.param.LockContext;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @date : 2022/5/20 11:30
 * @author: linzhou
 * @description : BaseLockBo
 */
public class BaseLockResult implements Closeable {

  private LockContext lockContext;

  private Long threadId;

  private String threadName;

  public LockContext getLockContext() {
    return lockContext;
  }

  public void setLockContext(LockContext lockContext) {
    this.lockContext = lockContext;
  }

  public Long getThreadId() {
    return threadId;
  }

  public void setThreadId(Long threadId) {
    this.threadId = threadId;
  }

  public String getThreadName() {
    return threadName;
  }

  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  @Override
  public void close() throws IOException {
    throw new UnsupportedOperationException();
  }
}
