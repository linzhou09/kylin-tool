package com.kylin.lock.model.bo;


import com.kylin.lock.annotations.LockParam;

/**
 * CopyRight : <company domain>
 * Project :  zouwu-oms-framework
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-06-26 14:46
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class ParamInfo {
  private Object value;
  private String paramName;
  private LockParam param;

  public ParamInfo(Object value, String paramName, LockParam param) {
    this.value = value;
    this.paramName = paramName;
    this.param = param;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public LockParam getParam() {
    return param;
  }

  public void setParam(LockParam param) {
    this.param = param;
  }
}
