package com.kylin.biz.utils.exception;

import com.kylin.biz.utils.model.bo.exception.ExceptionResultCode;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-07-12 17:54
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class BizException extends RuntimeException {


    public BizException(ExceptionResultCode exceptionResultCode, Object... args) {
        super();
    }

    public BizException(Throwable cause, ExceptionResultCode exceptionResultCode, Object... args) {
//        super(message, cause);
    }
}
