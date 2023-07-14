package com.kylin.http.client.biz.handler.http.request.impl;

import com.kylin.http.client.biz.bo.HttpClientRequest;
import com.kylin.http.client.biz.context.body.DefaultBody;
import com.kylin.http.client.biz.handler.http.request.SetHttpParamHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author linzhou
 * @ClassName SetBodyHandler.java
 * @createTime 2021年12月15日 12:05:00
 * @Description
 */
@Component
@Order
public class SetBodyHandler implements SetHttpParamHandler {
    @Override
    public boolean setHttpParam(HttpClientRequest request, Object o) {

        if (o instanceof String){
            request.setBody(new DefaultBody((String) o));
            return true;
        }

        return false;
    }
}
