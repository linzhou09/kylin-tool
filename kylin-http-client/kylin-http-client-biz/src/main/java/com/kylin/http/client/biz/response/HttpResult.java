package com.kylin.http.client.biz.response;

import lombok.Data;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-04-25 13:44
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Data
public class HttpResult<T> {

    /**
     * http响应码
     */
    public Integer httpCode;

    public String httpUrl;
    /**
     * 返回结果(原始内容)
     */
    public String result;
    /**
     * 返回结果
     */
    public T data;
}
