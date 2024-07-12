package com.kylin.lock.model.enums;

import com.kylin.biz.utils.exception.ExceptionResultCode;
import lombok.Getter;

/**
 * CopyRight (c) : www.wwlcargo.com
 * Project :  kylin-tool
 * Comments : ResultInfoEnums
 * JDK version : JDK1.8
 * Create Date : 2021/11/21 14:50
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
@Getter
public enum LockErrorResultCodeEnums implements ExceptionResultCode {

  /**
   * 成功
   */
  FREQUENT("900001", "请求太频繁了,请稍后再试"),
  NOT_FIND_PARAM_VALUE("900002", "分布式锁key获取失败，找不到参数：%s"),
  LOCK_KEY_FORMAT_ERROR_NOT_FIND_PARAM("900003", "字符串格式化失败，找不到参数：%s!"),
  /****************************************************************************/
  ;

  /**
   * 编码
   */
  private String code;

  /**
   * 中文返回信息描述
   */
  private String message;

  LockErrorResultCodeEnums(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * 根据本地化语言返回错误信息
   *
   * @return message
   */
  public String getMessage() {

    return message;
  }

  public String getCode() {
    return code;
  }

  public LockErrorResultCodeEnums setCode(String code) {
    this.code = code;
    return this;
  }

  public String getZhMessage() {
    return message;
  }

  public LockErrorResultCodeEnums setZhMessage(String zhMessage) {
    this.message = zhMessage;
    return this;
  }

}
