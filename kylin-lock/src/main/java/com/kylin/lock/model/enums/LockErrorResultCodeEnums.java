package com.kylin.lock.model.enums;

import lombok.Getter;

/**
 * CopyRight (c) : www.wwlcargo.com
 * Project :  zouwu-oms-framework
 * Comments : ResultInfoEnums
 * JDK version : JDK1.8
 * Create Date : 2021/11/21 14:50
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
@Getter
public enum LockErrorResultCodeEnums{

  /**
   * 成功
   */
  FREQUENT("900001", "请求太频繁了,请稍后再试", "success"),
  NOT_FIND_PARAM_VALUE("900002", "分布式锁key获取失败，找不到参数：%s", "success"),
  /****************************************************************************/
  ;

  /**
   * 编码
   */
  private String code;

  /**
   * 中文返回信息描述
   */
  private String zhMessage;

  LockErrorResultCodeEnums(String code, String zhMessage, String enMessage) {
    this.code = code;
    this.zhMessage = zhMessage;
  }

  /**
   * 根据本地化语言返回错误信息
   *
   * @return message
   */
  public String getMessage() {

    return zhMessage;
  }

  public String getCode() {
    return code;
  }

  public LockErrorResultCodeEnums setCode(String code) {
    this.code = code;
    return this;
  }

  public String getZhMessage() {
    return zhMessage;
  }

  public LockErrorResultCodeEnums setZhMessage(String zhMessage) {
    this.zhMessage = zhMessage;
    return this;
  }

}
