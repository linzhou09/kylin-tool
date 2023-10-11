package com.kylin.biz.utils.exception;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : 返回信息
 * JDK version : JDK1.8
 * Create Date : 2021-10-22 13:35
 *
 * @author : linzhou
 * @version : 0.1.0
 * @since : 0.1.0
 */
public interface ExceptionResultCode {

  /**
   * code
   * @return 结果编码
   */
  String getCode();

  /**
   * 信息
   * @return 结果信息
   */
  String getMessage();
}
