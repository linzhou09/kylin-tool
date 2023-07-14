package com.kylin.http.client.biz.annotation;



import com.kylin.http.client.biz.interceptor.HttpClientInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HttpClient注解
 *
 * @author linzhou
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpInterceptor {

  /**
   * 添加自定义拦截器
   *
   * @return
   */
  Class<? extends HttpClientInterceptor>[] value() default {};


}
