package com.kylin.spring.utils;


import com.kylin.biz.utils.exception.BizException;
import com.kylin.biz.utils.model.bo.exception.ExceptionResultCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Objects;

/**
 * @author linzhou
 * @version 1.0.0
 * @ClassName Assert.java
 * @Description 断言类 （迁移到result-starter）
 * @createTime 2021年07月14日 14:57:00
 */
public class AssertUtil {
  public static final Logger LOGGER = LoggerFactory.getLogger(AssertUtil.class);


  public static void isBlank(String str, ExceptionResultCode code, Object... org) {
    isTrue(StringUtils.isBlank(str), code, org);
  }

  public static void isNotBlank(String str, ExceptionResultCode code, Object... org) {
    isTrue(StringUtils.isNotBlank(str), code, org);
  }

  public static void isEmpty(String str, ExceptionResultCode code, Object... org) {
    isTrue(StringUtils.isEmpty(str), code, org);
  }

  public static void isNotEmpty(String str, ExceptionResultCode code, Object... org) {
    isTrue(StringUtils.isNotEmpty(str), code, org);
  }

  public static void isNotNull(Object o, ExceptionResultCode code, Object... org) {
    isTrue(o != null, code, org);
  }

  public static void isNull(Object o, ExceptionResultCode code, Object... org) {
    isTrue(o == null, code, org);
  }

  public static void isNotEmpty(Collection<?> c, ExceptionResultCode code, Object... org) {
    isTrue(CollectionUtils.isNotEmpty(c), code, org);
  }

  public static void isEmpty(Collection<?> c, ExceptionResultCode code, Object... org) {
    isTrue(CollectionUtils.isEmpty(c), code, org);
  }

  public static void isNotEmpty(Map<?, ?> m, ExceptionResultCode code, Object... org) {
    isTrue(m != null && !m.isEmpty(), code, org);
  }

  public static void isEmpty(Map<?, ?> m, ExceptionResultCode code, Object... org) {
    isTrue(m == null || m.isEmpty(), code, org);
  }

  public static void isEquals(Object o1, Object o2, ExceptionResultCode code, Object... org) {
    isTrue(Objects.equals(o1, o2), code, org);
  }

  public static void isNotEquals(Object o1, Object o2, ExceptionResultCode code, Object... org) {
    isTrue(!Objects.equals(o1, o2), code, org);
  }


  public static void isTrue(boolean as, ExceptionResultCode code, Object... org) {
    if (!as) {
      BizException bizException = new BizException(code, resolveMessage(code.getMessage(), org));
      LOGGER.warn("", bizException);
      throw bizException;
    }
  }

  public static void throwException(ExceptionResultCode code, Object... org) {
    BizException bizException = new BizException(code, code.getMessage());
    LOGGER.warn("", bizException);
    throw bizException;
  }

  /**
   * 占位符解析
   *
   * @param message <对此参数的描述，可以引用系统设计中的描述>
   * @param params <对此参数的描述，可以引用系统设计中的描述>
   * @return java.lang.String
   * @author Galvin
   * @date 2022/12/29 15:46
   **/
  private static String resolveMessage(String message, Object... params) {
    try {
      return String.format(message, params);
    } catch (IllegalFormatException e) {
      LOGGER.warn("解析错误信息失败", e);
      return message;
    }
  }

}
