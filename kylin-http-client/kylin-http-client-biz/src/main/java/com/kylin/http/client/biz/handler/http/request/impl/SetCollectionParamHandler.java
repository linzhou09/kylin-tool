package com.kylin.http.client.biz.handler.http.request.impl;

import com.kylin.http.client.biz.bo.HttpClientRequest;
import com.kylin.http.client.biz.handler.http.request.SetHttpParamHandler;
import com.kylin.http.client.biz.handler.http.request.SetHttpParamHandlerManager;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author linzhou
 * @ClassName SetCollectionParamHander.java
 * @createTime 2021年12月15日 12:08:00
 * @Description
 */
@Component
public class SetCollectionParamHandler implements SetHttpParamHandler {
    @Override
    public boolean setHttpParam(HttpClientRequest request, Object o) {
        if (o instanceof Collection) {
            Collection<?> collection = (Collection<?>) o;
            for (Object param : collection) {
                SetHttpParamHandlerManager.setHttpParam(request,param );
            }
            return true;
        }
        return false;
    }
}
