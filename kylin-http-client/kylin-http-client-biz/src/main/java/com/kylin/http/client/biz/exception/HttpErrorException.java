package com.kylin.http.client.biz.exception;

/**
 * 异常类
 * @author linzhou
 */
public class HttpErrorException extends RuntimeException{

    public HttpErrorException(String message) {
        super(message);
    }
}
