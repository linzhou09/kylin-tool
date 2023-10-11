package com.kylin.biz.utils.exception;

import java.util.IllegalFormatException;

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

    /**
     * 错误枚举
     */
    private ExceptionResultCode resultCode;
    /**
     * 异常变量
     */
    private Object[] params;





    public BizException(ExceptionResultCode exceptionResultCode,  Object... params) {
        super(resolveMessage(exceptionResultCode, params));
        resultCode = exceptionResultCode;
        this.params = params;
    }

    public BizException(Throwable cause, ExceptionResultCode exceptionResultCode, Object... params) {
        super(resolveMessage(exceptionResultCode,params),cause);
        resultCode = exceptionResultCode;
        this.params = params;
    }


    public ExceptionResultCode getResultCode() {
        return resultCode;
    }

    public static String resolveMessage(ExceptionResultCode exceptionResultCode, Object... params) {
        return resolveMessage(exceptionResultCode.getMessage(), params);
    }

    /**
     * 占位符解析
     *
     * @param message <对此参数的描述，可以引用系统设计中的描述>
     * @param params <对此参数的描述，可以引用系统设计中的描述>
     * @return java.lang.String
     * @author Galvin
     * @date 2022/12/29 15:46
     **/
    private static String resolveMessage(String message, Object... params) {
        try {
            return String.format(message, params);
        } catch (IllegalFormatException e) {
            return message;
        }
    }
}
