package com.kylin.http.client.biz.handler.http.request.impl;

import com.kylin.http.client.biz.bo.HttpClientRequest;
import com.kylin.http.client.biz.context.form.Form;
import com.kylin.http.client.biz.handler.http.request.SetHttpParamHandler;
import org.springframework.stereotype.Component;

/**
 * @author linzhou
 * @ClassName SetBodyHandler.java
 * @createTime 2021年12月15日 12:05:00
 * @Description
 */
@Component
public class SetFromHandler implements SetHttpParamHandler {
    @Override
    public boolean setHttpParam(HttpClientRequest request, Object o) {
        if (o instanceof Form) {
            //处理表单参数
            request.addNameValueParam((Form) o);
            return true;
        }

        return false;
    }
}
