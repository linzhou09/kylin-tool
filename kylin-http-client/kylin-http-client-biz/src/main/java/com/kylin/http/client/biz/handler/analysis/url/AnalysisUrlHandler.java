package com.kylin.http.client.biz.handler.analysis.url;

import com.kylin.http.client.biz.context.HttpRequestContext;


/**
 * @author linzhou
 * @ClassName AnalysisUrlParamHandler.java
 * @createTime 2022年02月07日 11:57:00
 * @Description
 */
public interface AnalysisUrlHandler {

    /**
     * 责任链模式调用
     * 解析方法上的参数
     * @param context 上下文
     * @return
     */
    String analysisUrl(HttpRequestContext context) throws Exception;

}
