package com.kylin.biz.utils.exception;

import com.kylin.biz.utils.exception.ExceptionResultCode;

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
public enum BizResultCodeEnums implements ExceptionResultCode {

  /**
   * 成功
   */
  SUCCESS("20000", "请求成功"),




    /************************************字符串替换报错01开头****************************************/
  STRING_FORMAT_ERROR_NOT_FIND_PARAM("010000", "字符串格式化失败，找不到参数：%s"),
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

  BizResultCodeEnums(String code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
