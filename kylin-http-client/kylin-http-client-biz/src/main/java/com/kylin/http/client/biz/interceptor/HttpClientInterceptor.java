package com.kylin.http.client.biz.interceptor;

import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.http.client.biz.response.BaseHttpClientResponse;
import com.kylin.http.client.biz.response.HttpClientResponse;

/**
 * @author linzhou
 * @ClassName HttpClientHandler.java
 * @createTime 2021年12月08日 14:31:00
 * @Description 拦截器
 */
public interface HttpClientInterceptor {

  /**
   * 参数解析之后,调用http请求之前执行
   * 优先级:所有系统级拦截器执行完后再执行自定义拦截器
   *
   * @param context 解析参数的上下文
   * @return 如果有返回值 则直接返回本方法的返回值当做本次请求的返回值
   */
  default Object runHttpBefore(HttpRequestContext context) {
    return null;
  }

  /**
   * http请求之后调用
   * 优先级:自定义拦截器优先
   *
   * @param response 解析参数的上下文
   * @return 如果有返回值 则直接返回本方法的返回值当做本次请求的返回值
   * @throws Exception
   */
  default Object runHttpAfter(HttpClientResponse response) throws Exception {
    return null;
  }

  /**
   * 解析返回数据之后
   * 优先级:自定义拦截器优先
   *
   * @param response 返回数据
   * @param rlt      原本要返回的值
   * @return 最终要返回值
   * @throws Exception
   */
  default Object returnObjectAfter(HttpClientResponse response, Object rlt) throws Exception {
    return rlt;
  }

  /**
   * http请求发生异常后调用
   * <p>
   * 优先级:所有系统级拦截器执行完后再执行自定义拦截器
   *
   * @param context  请求上下文
   * @param response
   * @param e        异常类
   * @return 返回一个异常时的结果, 如果返回值为null, 则异常不处理
   * @throws Exception
   */
  default Object httpException(HttpRequestContext context, BaseHttpClientResponse response, Throwable e) throws Exception {
    return null;
  }

  /**
   * 是否是自定义拦截器
   * 自定义拦截器:需要接口或者方法级指定才会生效并执行(推荐 性能好)
   * 系统拦截器:全局拦截器,每一个方法都会生效并执行(非必要不推荐,性能差)
   *
   * @return true:自定义拦截器 false:系统拦截器
   */
  default boolean isCustomInterceptor() {
    return false;
  }


}
