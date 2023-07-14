package com.kylin.http.client.biz.interceptor;

import com.kylin.http.client.biz.annotation.HttpInterceptor;
import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.http.client.biz.response.BaseHttpClientResponse;
import com.kylin.spring.utils.utils.SpringUtil;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * CopyRight : <company domain>
 * Project :  http-client
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-10-19 15:24
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class HttpClientInterceptorManager {

  /**
   * 系统级拦截器集合
   * 所有请求都会执行的拦截器
   */
  private static List<HttpClientInterceptor> httpClientSystemInterceptorList;

  /**
   * 自定义拦截器
   * 只有在Client接口或者方法上指定的拦截器才会在该方法执行的时候才会生效
   */
  private static Map<Class<? extends HttpClientInterceptor>, HttpClientInterceptor> httpClientCustomInterceptorMap;

  /**
   * 获取系统级拦截器
   *
   * @return
   */
  private static List<HttpClientInterceptor> getHttpClientSystemInterceptorList() {

    if (Objects.isNull(httpClientSystemInterceptorList)) {
      //如果系统拦截器集合为null,则初始化加载系统拦截器信息
      initHttpClientInterceptor();
    }
    return httpClientSystemInterceptorList;
  }


  /**
   * 获取自定义拦截器
   *
   * @param customInterceptors 自定义拦截器的类型
   * @return
   */
  private static List<HttpClientInterceptor> getHttpClientCustomInterceptorList(Class<? extends HttpClientInterceptor>... customInterceptors) {

    if (Objects.isNull(customInterceptors) || customInterceptors.length == 0) {
      return Collections.emptyList();
    }

    if (Objects.isNull(httpClientCustomInterceptorMap)) {
      //如果自定义拦截器map为null,则初始化加载系统拦截器信息
      initHttpClientInterceptor();
    }
    List<HttpClientInterceptor> httpClientCustomInterceptorList = new ArrayList<>(customInterceptors.length);
    for (Class<? extends HttpClientInterceptor> customInterceptor : customInterceptors) {
      HttpClientInterceptor httpClientCustomInterceptor = httpClientCustomInterceptorMap.get(customInterceptor);
      httpClientCustomInterceptorList.add(httpClientCustomInterceptor);
    }

    return httpClientCustomInterceptorList;
  }


  /**
   * 初始化,加载拦截器
   */
  private static synchronized void initHttpClientInterceptor() {
    if (Objects.nonNull(httpClientSystemInterceptorList) && Objects.nonNull(httpClientCustomInterceptorMap)) {
      return;
    }
    httpClientSystemInterceptorList = new ArrayList<>();
    httpClientCustomInterceptorMap = new HashMap<>();
    //获取所有拦截器
    List<HttpClientInterceptor> httpClientInterceptorList = SpringUtil.getBeanList(HttpClientInterceptor.class);

    for (HttpClientInterceptor httpClientInterceptor : httpClientInterceptorList) {
      if (httpClientInterceptor.isCustomInterceptor()) {
        //如果是自定义拦截器,则加入自定义拦截器集合
        Class<?> userClass = ClassUtils.getUserClass(httpClientInterceptor);
        httpClientCustomInterceptorMap.put((Class<? extends HttpClientInterceptor>) userClass, httpClientInterceptor);
      } else {
        //不是自定义拦截器,则加入系统拦截器
        httpClientSystemInterceptorList.add(httpClientInterceptor);
      }
    }
  }


  /**
   * 执行httpBefore方法
   *
   * @param context
   */
  public static Object runHttpBefore(HttpRequestContext context) {
    //执行系统级拦截器
    Object rlt = runSystemInterceptorHttpBefore(context);
    if (Objects.nonNull(rlt)) {
      return rlt;
    }
    //执行自定义拦截器
    return runCustomInterceptorHttpBefore(context);
  }

  /**
   * 执行系统级拦截器HttpBefore方法
   *
   * @param context
   * @return
   */
  private static Object runSystemInterceptorHttpBefore(HttpRequestContext context) {
    List<HttpClientInterceptor> httpClientSystemInterceptors = getHttpClientSystemInterceptorList();
    return runHttpClientInterceptorHttpBefore(context, httpClientSystemInterceptors);
  }

  /**
   * 执行自定义拦截器HttpBefore方法
   *
   * @param context
   * @return
   */
  private static Object runCustomInterceptorHttpBefore(HttpRequestContext context) {
    HttpInterceptor httpInterceptor = context.getMethodOrInterfaceAnnotation(HttpInterceptor.class);
    Class<? extends HttpClientInterceptor>[] customInterceptors = null;
    if (Objects.nonNull(httpInterceptor)) {
      customInterceptors = httpInterceptor.value();
    }
    List<HttpClientInterceptor> httpClientCustomInterceptorList = getHttpClientCustomInterceptorList(customInterceptors);
    return runHttpClientInterceptorHttpBefore(context, httpClientCustomInterceptorList);
  }

  /**
   * 执行指定的拦截器
   *
   * @param context
   * @param httpClientSystemInterceptors
   * @return
   */
  private static Object runHttpClientInterceptorHttpBefore(HttpRequestContext context, List<HttpClientInterceptor> httpClientSystemInterceptors) {
    for (HttpClientInterceptor httpClientInterceptor : httpClientSystemInterceptors) {
      Object rlt = httpClientInterceptor.runHttpBefore(context);
      if (Objects.nonNull(rlt)) {
        return rlt;
      }
    }
    return null;
  }

  /**
   * 执行runHttpAfter方法
   *
   * @param response
   */
  public static Object runHttpAfter(BaseHttpClientResponse response) throws Exception {
    //执行自定义拦截器的HttpAfter方法
    Object rlt = runCustomInterceptorHttpAfter(response);

    if (Objects.nonNull(rlt)) {
      return rlt;
    }
    //执行系统级拦截器的HttpAfter方法
    return runSystemInterceptorHttpAfter(response);
  }

  /**
   * 执行系统级拦截器的HttpAfter方法
   *
   * @param response
   * @return
   * @throws Exception
   */
  private static Object runSystemInterceptorHttpAfter(BaseHttpClientResponse response) throws Exception {
    List<HttpClientInterceptor> httpClientSystemInterceptors = getHttpClientSystemInterceptorList();
    return runHttpClientInterceptorHttpAfter(response, httpClientSystemInterceptors);
  }


  /**
   * 执行自定义拦截器的HttpAfter方法
   *
   * @param response
   * @return
   * @throws Exception
   */
  private static Object runCustomInterceptorHttpAfter(BaseHttpClientResponse response) throws Exception {

    Class<? extends HttpClientInterceptor>[] customInterceptors = getHttpClientCustomInterceptorClass(response);
    List<HttpClientInterceptor> httpClientCustomInterceptorList = getHttpClientCustomInterceptorList(customInterceptors);
    return runHttpClientInterceptorHttpAfter(response, httpClientCustomInterceptorList);
  }

  /**
   * 执行指定拦截器的HttpAfter方法
   *
   * @param response
   * @param httpClientSystemInterceptors
   * @return
   * @throws Exception
   */
  private static Object runHttpClientInterceptorHttpAfter(BaseHttpClientResponse response,
                                                          List<HttpClientInterceptor> httpClientSystemInterceptors) throws Exception {
    for (HttpClientInterceptor httpClientInterceptor : httpClientSystemInterceptors) {
      Object rlt = httpClientInterceptor.runHttpAfter(response);
      if (Objects.nonNull(rlt)) {
        return rlt;
      }
    }
    return null;
  }


  /**
   * 执行returnObjectAfter方法
   *
   * @param response
   */
  public static Object returnObjectAfter(BaseHttpClientResponse response, Object rlt) throws Exception {
    //执行自定义拦截器的returnObjectAfter方法
    rlt = runCustomInterceptorObjectAfter(response, rlt);
    //执行系统拦截器的returnObjectAfter方法
    List<HttpClientInterceptor> httpClientSystemInterceptors = getHttpClientSystemInterceptorList();
    return runHttpClientInterceptorObjectAfter(response, rlt, httpClientSystemInterceptors);
  }

  /**
   * 执行自定义拦截器的returnObjectAfter方法
   *
   * @param response
   * @param rlt
   * @return
   * @throws Exception
   */
  private static Object runCustomInterceptorObjectAfter(BaseHttpClientResponse response, Object rlt) throws Exception {
    Class<? extends HttpClientInterceptor>[] customInterceptors = getHttpClientCustomInterceptorClass(response);
    List<HttpClientInterceptor> httpClientCustomInterceptors = getHttpClientCustomInterceptorList(customInterceptors);
    return runHttpClientInterceptorObjectAfter(response, rlt, httpClientCustomInterceptors);
  }

  /**
   * 获取需要指定执行的自定义拦截器
   *
   * @param response
   * @return
   */
  private static Class<? extends HttpClientInterceptor>[] getHttpClientCustomInterceptorClass(BaseHttpClientResponse response) {
    HttpRequestContext context = response.getContext();
    return getHttpClientCustomInterceptorClass(context);
  }

  private static Class<? extends HttpClientInterceptor>[] getHttpClientCustomInterceptorClass(HttpRequestContext context) {
    HttpInterceptor httpInterceptor = context.getMethodOrInterfaceAnnotation(HttpInterceptor.class);
    Class<? extends HttpClientInterceptor>[] customInterceptors = null;
    if (Objects.nonNull(httpInterceptor)) {
      customInterceptors = httpInterceptor.value();
    }
    return customInterceptors;
  }

  /**
   * 执行指定returnObjectAfter方法
   *
   * @param response                     http请求返回
   * @param rlt                          返回的结果
   * @param httpClientSystemInterceptors 需要执行的拦截器
   * @return
   * @throws Exception
   */
  private static Object runHttpClientInterceptorObjectAfter(BaseHttpClientResponse response,
                                                            Object rlt,
                                                            List<HttpClientInterceptor> httpClientSystemInterceptors) throws Exception {
    for (HttpClientInterceptor httpClientInterceptor : httpClientSystemInterceptors) {
      rlt = httpClientInterceptor.returnObjectAfter(response, rlt);
    }
    return rlt;
  }

  /**
   * 执行httpAfter方法
   *
   * @param context
   * @param response
   * @param e
   */
  public static Object runHttpException(HttpRequestContext context, BaseHttpClientResponse response, Throwable e) throws Exception {
    //执行系统拦截器捕获请求
    List<HttpClientInterceptor> httpClientSystemInterceptors = getHttpClientSystemInterceptorList();
    Object rlt = runHttpClientInterceptorHttpException(context, response, e, null, httpClientSystemInterceptors);
    //执行自定义拦截器请求
    Class<? extends HttpClientInterceptor>[] customInterceptors = getHttpClientCustomInterceptorClass(context);
    List<HttpClientInterceptor> httpClientCustomInterceptors = getHttpClientCustomInterceptorList(customInterceptors);
    return runHttpClientInterceptorHttpException(context, response, e, rlt, httpClientCustomInterceptors);
  }

  /**
   * 执行指定拦截器的异常捕获方法
   *
   * @param context
   * @param response
   * @param e
   * @param rlt
   * @param httpClientSystemInterceptors
   * @return
   * @throws Exception
   */
  private static Object runHttpClientInterceptorHttpException(HttpRequestContext context,
                                                              BaseHttpClientResponse response,
                                                              Throwable e,
                                                              Object rlt,
                                                              List<HttpClientInterceptor> httpClientSystemInterceptors) throws Exception {
    for (HttpClientInterceptor httpClientInterceptor : httpClientSystemInterceptors) {
      rlt = httpClientInterceptor.httpException(context, response, e);
    }
    return rlt;
  }


}
