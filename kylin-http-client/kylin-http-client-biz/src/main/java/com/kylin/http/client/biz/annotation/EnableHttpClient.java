package com.kylin.http.client.biz.annotation;



import com.kylin.http.client.biz.handler.analysis.url.AnalysisUrlHandler;
import com.kylin.http.client.biz.handler.analysis.url.impl.DefaultAnalysisUrlHandler;
import com.kylin.http.client.biz.proxy.AbstractHttpProxy;
import com.kylin.http.client.biz.registrar.HttpClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * httpClient启动注解
 *
 * @author linzhou
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HttpClientRegistrar.class)
public @interface EnableHttpClient {

    /**
     * 设置需要扫描的httpclient的包名
     *
     * @return
     */
    String[] basePackages() default {};

    /**
     * 设置全局默认代理类,优先级高于配置文件中的设置
     *
     *  @return 动态代理类
     */
    Class<?extends AbstractHttpProxy> defaultProxy() ;

    /**
     * 全局默认的url解析器
     * @return
     */
    Class<?extends AnalysisUrlHandler> defaultAnalysisUrlHandler() default DefaultAnalysisUrlHandler.class;
}
