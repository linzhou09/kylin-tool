package com.kylin.http.client.biz.interceptor.impl;

import com.kylin.http.client.biz.interceptor.HttpClientInterceptor;
import com.kylin.http.client.biz.response.HttpClientResponse;
import com.kylin.http.client.biz.response.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-05-05 10:04
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Component
public class HttpResultInterceptor implements HttpClientInterceptor {

    @Override
    public Object runHttpAfter(HttpClientResponse response) throws Exception {

        Class<?> returnType = response.getContext().getReturnType();
        if (returnType == HttpResult.class) {
            String result = buildHttpResultBody(response);
            response.result(result);
        }


        return HttpClientInterceptor.super.runHttpAfter(response);
    }

    private String buildHttpResultBody(HttpClientResponse response) throws IOException {
        String result = response.result();
        StringBuilder rlt = new StringBuilder();
        rlt.append("{\"httpCode\":").append(response.getCode()).append(",")
                .append("\"httpUrl\":\"").append(response.getHttpUrl()).append("\",")
                .append("\"result\":\"").append(result.replace("\"","\\\"")).append("\"");
        if (StringUtils.isNotBlank(result)){
            rlt.append(",\"data\":").append(result);
        }
        rlt.append("}");
        return rlt.toString();
    }
}
