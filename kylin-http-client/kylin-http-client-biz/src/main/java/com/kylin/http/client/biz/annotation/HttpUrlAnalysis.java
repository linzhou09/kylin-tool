package com.kylin.http.client.biz.annotation;



import com.kylin.http.client.biz.handler.analysis.url.AnalysisUrlHandler;
import com.kylin.http.client.biz.handler.analysis.url.impl.DefaultAnalysisUrlHandler;

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
public @interface HttpUrlAnalysis {

  /**
   * 添加自定义拦截器
   *
   * @return
   */
  Class<? extends AnalysisUrlHandler> value() default DefaultAnalysisUrlHandler.class;


}
