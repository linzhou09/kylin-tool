package com.kylin.http.client.interceptor;

import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.http.client.biz.interceptor.HttpClientCustomInterceptor;
import com.kylin.http.client.biz.response.BaseHttpClientResponse;
import com.kylin.http.client.biz.response.HttpClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-02-02 09:58
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Component
@Slf4j
public class MyHttpClientCustomInterceptor extends HttpClientCustomInterceptor {


  @Override
  public Object runHttpBefore(HttpRequestContext context) {
    log.info("MyHttpClientCustomInterceptor runHttpBefore");
    return super.runHttpBefore(context);
  }

  @Override
  public Object runHttpAfter(HttpClientResponse response) throws Exception {
    log.info("MyHttpClientCustomInterceptor runHttpAfter");

    return super.runHttpAfter(response);
  }

  @Override
  public Object returnObjectAfter(HttpClientResponse response, Object rlt) throws Exception {
    log.info("MyHttpClientCustomInterceptor returnObjectAfter");

    return super.returnObjectAfter(response, rlt);
  }

  @Override
  public Object httpException(HttpRequestContext context, BaseHttpClientResponse response, Throwable e) throws Exception {
    log.info("MyHttpClientCustomInterceptor httpException");

    return super.httpException(context, response, e);
  }
}
