package com.kylin.http.client.biz.handler.http.result.impl;

import com.alibaba.fastjson.JSON;
import com.kylin.http.client.biz.handler.http.result.HttpClientResultHandler;
import com.kylin.http.client.biz.response.HttpClientResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author linzhou
 * @ClassName ObjectResultHandler.java
 * @createTime 2021年12月27日 12:02:00
 * @Description
 */
@Component
@Order
public class ObjectHttpClientResultHandler implements HttpClientResultHandler {

    @Override
    public Object getReturnObject(HttpClientResponse response, Class<?> returnType) throws Exception {
        String body = response.result();
        if (returnType == String.class){
            return body;
        }
        return getObject(response, body);
    }

    private Object getObject(HttpClientResponse response, String result) {

        Type genericReturnType = response.getContext().getMethod().getGenericReturnType();
        return JSON.parseObject(result,genericReturnType);
    }

}
