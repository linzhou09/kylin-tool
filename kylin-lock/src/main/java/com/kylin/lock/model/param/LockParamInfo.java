package com.kylin.lock.model.param;

/**
 * CopyRight : <company domain>
 * Project :  lock-tool
 * Comments : 字段解析信息
 * JDK version : JDK1.8
 * Create Date
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class LockParamInfo {
    private Object value;
    private String paramName;

    public LockParamInfo(String paramName, Object value) {
        this.value = value;
        this.paramName = paramName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
