package com.kylin.biz.utils.model.bo.kylin.param;


import com.kylin.biz.utils.common.annotations.KylinParam;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : 字段解析信息
 * JDK version : JDK1.8
 * Create Date : 2023-06-26 14:46
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class KylinParamInfo {
  private Object value;
  private String paramName;
  private KylinParam kylinParam;

  public KylinParamInfo(Object value, String paramName, KylinParam param) {
    this.value = value;
    this.paramName = paramName;
    this.kylinParam = param;
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

  public KylinParam getKylinParam() {
    return kylinParam;
  }

  public void setKylinParam(KylinParam kylinParam) {
    this.kylinParam = kylinParam;
  }
}
