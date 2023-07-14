package com.kylin.http.client.biz.context;


import com.kylin.http.client.biz.annotation.HttpClient;
import com.kylin.http.client.biz.bo.HttpClientRequest;
import com.kylin.http.client.biz.context.body.Body;
import com.kylin.http.client.biz.context.body.file.FileBody;
import com.kylin.http.client.biz.context.form.Form;
import com.kylin.http.client.biz.context.header.HttpHeader;
import com.kylin.http.client.biz.enums.HttpRequestMethod;
import com.kylin.http.client.biz.factorybean.HttpFactoryBean;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 请求上下文
 *
 * @author linzhou
 */
@Data
public class HttpRequestContext {
  private Object proxy;
  /**
   * 执行方法对象
   */
  private Method method;

  /**
   * 方法参数的上的注解对象集合
   */
  private Annotation[][] parameterAnnotations;
  private HttpFactoryBean httpFactoryBean;
  /**
   * 参数
   */
  private Object[] args;
  /**
   * 请求类型
   */
  private HttpRequestMethod httpRequestMethod;
  /**
   * http请求参数
   */
  private HttpClientRequest httpClientRequest;
  /**
   * http请求地址
   */
  private String httpUrl;

  /**
   * 类型
   */
  private Class<?> type;
  /**
   * 接口上的注解
   */
  private HttpClient interfaceHttpClient;
  /**
   * 方法上的注解对象
   */
  private HttpClient methodHttpClient;

  public HttpRequestContext(HttpFactoryBean httpFactoryBean, Class<?> type, HttpClient interfaceHttpClient, Object proxy, Method method, Object[] args) {
    this.method = method;
    this.httpFactoryBean = httpFactoryBean;
    this.proxy = proxy;
    this.args = args;
    this.type = type;
    this.interfaceHttpClient = interfaceHttpClient;
    //方法的注解
    this.methodHttpClient = method.getAnnotation(HttpClient.class);
    //参数的注解
    this.parameterAnnotations = method.getParameterAnnotations();
    this.httpRequestMethod = getHttpRequestMethod();

  }


  public boolean isPostEntity() {
    if (httpRequestMethod != HttpRequestMethod.POST) {
      return false;
    }

    return Objects.nonNull(httpClientRequest.getBody()) && StringUtils.isNotBlank(httpClientRequest.getBody().getBody());
  }

  /**
   * 获取请求方式
   *
   * @return
   */
  public HttpRequestMethod getHttpRequestMethod() {
    if (httpRequestMethod != null) {
      return httpRequestMethod;
    }
    //从方法注解中获取请求类型
    HttpRequestMethod httpRequestMethod = Objects.nonNull(methodHttpClient) ? methodHttpClient.method() : null;
    if (Objects.isNull(httpRequestMethod) || Objects.equals(httpRequestMethod, HttpRequestMethod.NULL)) {
      //如果方法注解中没有获取到请求类型则从类注解中获取请求类型
      httpRequestMethod = interfaceHttpClient.method();
    }
    if (Objects.equals(httpRequestMethod, HttpRequestMethod.NULL)) {
      //如果方法注解和类注解中都没有标注请求类型,则默认get
      httpRequestMethod = HttpRequestMethod.GET;
    }
    return httpRequestMethod;
  }


  public List<Form> getNameValueParams() {

    if (Objects.nonNull(httpClientRequest)) {
      return httpClientRequest.getNameValueParams();
    }
    return Collections.emptyList();
  }

  public List<FileBody> getUploadFiles() {
    if (Objects.nonNull(httpClientRequest)) {
      return httpClientRequest.getUploadFiles();
    }
    return Collections.emptyList();
  }

  public HttpHeader getHttpHeader() {
    if (Objects.nonNull(httpClientRequest)) {
      return httpClientRequest.getHttpHeader();
    }
    return null;
  }

  public Body getBody() {
    if (Objects.nonNull(httpClientRequest)) {
      return httpClientRequest.getBody();
    }
    return null;
  }

  public Class<?> getReturnType() {
    return getMethod().getReturnType();
  }

  /**
   * 获取指定注解
   * 方法级优先返回
   *
   * @param annotationClass 注解类型
   * @param <T>
   * @return
   */
  public <T extends Annotation> T getMethodOrInterfaceAnnotation(Class<T> annotationClass) {
    T annotation = method.getAnnotation(annotationClass);

    if (Objects.nonNull(annotation)) {
      return annotation;
    }

    return type.getAnnotation(annotationClass);
  }
}
