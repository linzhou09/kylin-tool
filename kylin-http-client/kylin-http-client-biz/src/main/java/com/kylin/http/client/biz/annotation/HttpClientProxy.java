package com.kylin.http.client.biz.annotation;



import com.kylin.http.client.biz.proxy.AbstractHttpProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对httpclient设置动态代理对象注解
 *
 * @author linzhou
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpClientProxy {

    /**
     * 设置代理类
     *
     *  @return 动态代理类
     */
    Class<?extends AbstractHttpProxy> value();
}
