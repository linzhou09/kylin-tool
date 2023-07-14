package com.kylin.http.client.biz.interceptor.impl;


import com.alibaba.fastjson.JSON;
import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.http.client.biz.context.body.Body;
import com.kylin.http.client.biz.context.form.Form;
import com.kylin.http.client.biz.interceptor.HttpClientInterceptor;
import com.kylin.http.client.biz.response.BaseHttpClientResponse;
import com.kylin.http.client.biz.response.HttpClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: linzhou
 * @create: 2021-12-31 17:04
 **/
@Component
public class LogInterceptor implements HttpClientInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);


    @Override
    public Object returnObjectAfter(HttpClientResponse response, Object rlt) throws Exception {
        String httpUrl = response.getHttpUrl();
        HttpRequestContext context = response.getContext();
        Body body = context.getBody();
        List<Form> nameValueParams = context.getNameValueParams();

        String result = getResult(response);

        logger.info("httpClientLog======>httpCode:{} url:{}, body:{}, form:{}, result:{}", response.getCode(),
                httpUrl, Objects.nonNull(body) ? body.getBody() : "null", JSON.toJSONString(nameValueParams), result);

        return rlt;
    }

    @Override
    public Object httpException(HttpRequestContext context, BaseHttpClientResponse response, Throwable e) throws Exception {
        String httpUrl = context.getHttpUrl();
        Body body = context.getBody();
        List<Form> nameValueParams = context.getNameValueParams();
        String result = getResult(response);
        if (Objects.isNull(response)) {
            logger.info("httpClientLog======>error response is null url:{}, body:{}, form:{}, result:{}",
                    httpUrl, Objects.nonNull(body) ? body.getBody() : "null", JSON.toJSONString(nameValueParams), result, e);
        } else {
            logger.info("httpClientLog======>error httpCode:{} url:{}, body:{}, form:{}, result:{}", response.getCode(),
                    httpUrl, Objects.nonNull(body) ? body.getBody() : "null", JSON.toJSONString(nameValueParams), result, e);
        }
        return null;
    }

    private String getResult(HttpClientResponse response) throws IOException {
        if (Objects.isNull(response)) {
            return "null";
        }

        return response.isFile() ? "is file" : response.result();
    }


}
