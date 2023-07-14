package com.kylin.http.client.biz.interceptor;

/**
 * @author linzhou
 * @ClassName HttpClientHandler.java
 * @createTime 2021年12月08日 14:31:00
 * @Description 自定义拦截器,用于接口或方法级指定执行的拦截器,这类拦截器即使已经注册到容器中,如果没有指定也不会执行
 */
public class HttpClientCustomInterceptor implements HttpClientInterceptor{

  @Override
  public final boolean isCustomInterceptor() {
    return true;
  }
}
