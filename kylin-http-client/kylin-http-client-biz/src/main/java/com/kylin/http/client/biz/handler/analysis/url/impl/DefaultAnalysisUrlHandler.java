package com.kylin.http.client.biz.handler.analysis.url.impl;


import com.kylin.http.client.biz.annotation.HttpClient;
import com.kylin.http.client.biz.config.HttpClientConfig;
import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.http.client.biz.enums.HttpRequestMethod;
import com.kylin.http.client.biz.exception.ParamException;
import com.kylin.http.client.biz.handler.analysis.url.AnalysisUrlHandler;
import com.kylin.http.client.biz.utils.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author linzhou
 * @ClassName DefaultAnalysisUrlHandler.java
 * @createTime 2022年02月07日 12:07:00
 * @Description
 */
@Component
public class DefaultAnalysisUrlHandler implements AnalysisUrlHandler {

    @Autowired
    private HttpClientConfig httpClientConfig;

    @Override
    public String analysisUrl(HttpRequestContext context) throws Exception {
        return getHttpUrl(context);
    }

    /**
     * 获取请求地址
     *
     * @return
     */
    public String getHttpUrl(HttpRequestContext context) {
        if (StringUtils.isBlank(context.getHttpUrl())) {
            if (Objects.isNull(context.getHttpClientRequest()) || Objects.isNull(context.getHttpRequestMethod())) {
                throw new ParamException("数据异常,methodParamResult or httpRequestMethod is null");
            }
            String baseUrl = getUrl(context, httpClientConfig.getBaseUrl());
            if (isGet(context)
                    || context.isPostEntity()) {
                return UrlUtil.getParamUrl(baseUrl, context.getNameValueParams());
            } else {
                return baseUrl;
            }
        }
        return null;
    }


    /**
     * 是否是get方法
     *
     * @param context
     * @return
     */
    protected boolean isGet(HttpRequestContext context) {
        HttpRequestMethod httpRequestMethod = context.getHttpRequestMethod();
        return HttpRequestMethod.POST != httpRequestMethod;
    }

    public String getUrl(HttpRequestContext context, String defaultBaseUrl) {
        if (Objects.nonNull(context.getHttpClientRequest().getHttpUrl())) {
            return context.getHttpClientRequest().getHttpUrl().getUrl();
        }
        HttpClient interfaceHttpClient = context.getInterfaceHttpClient();
        HttpClient methodHttpClient = context.getMethodHttpClient();
        Method method = context.getMethod();

        String url = getBasePath(context, defaultBaseUrl);

        //拼接类上的根路径
        String basePath = interfaceHttpClient.path();
        if (StringUtils.isNotBlank(basePath)) {
            url = UrlUtil.splicingUrl(url, basePath);
        }
        //如果方法没有注解,则取方法名称作为路径
        String path = "";


        if (Objects.nonNull(methodHttpClient)) {
            path = methodHttpClient.path();
            if (StringUtils.isBlank(path) && methodHttpClient.pathMethodName()) {
                //如果path为空,但是注解标注使用方法名,则path使用方法名称
                path = method.getName();
            }
        } else if (interfaceHttpClient.pathMethodName()) {
            //如果类注解上标注了使用方法名称
            path = method.getName();
        }
        return UrlUtil.splicingUrl(url, path);
    }

    private String getBasePath(HttpRequestContext context, String defaultBaseUrl) {
        HttpClient interfaceHttpClient = context.getInterfaceHttpClient();
        HttpClient methodHttpClient = context.getMethodHttpClient();
        String url = Objects.isNull(methodHttpClient) ? null : UrlUtil.getUrl(methodHttpClient);
        if (StringUtils.isBlank(url)) {
            url = UrlUtil.getUrl(interfaceHttpClient);
        }
        if (StringUtils.isBlank(url)) {
            if (StringUtils.isNotBlank(defaultBaseUrl)) {
                url = defaultBaseUrl;
            } else {
                throw new IllegalArgumentException("url:error,url is blank");
            }
        }
        if (!url.contains("://")) {
            url = "https://" + url;
        }
        return url;
    }
}
