package com.kylin.lock.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @date : 2022/5/25 16:50
 * @author: linzhou
 * @description : Param
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockParam {
  /**
   * 参数名称
   * @return
   */
  String value() default "";
}
