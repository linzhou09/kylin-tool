package com.kylin.biz.utils.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 长度单位枚举
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public enum LengthEnum {
  CM("CM",1.0),
  M("M",100.0),
  /**
   * 英寸
   */
  IN("IN",2.54),
  /**
   * 英尺
   */
  FT("FT",30.48),
  ;

  /**
   * 单位名称
   */
  private final String name;
  /**
   * 同CM的转换比例
   */
  private final Double convertCoefficient;

  LengthEnum(String name, Double convertCoefficient) {
    this.name = name;
    this.convertCoefficient = convertCoefficient;
  }

  public String getName() {
    return name;
  }

  public Double getConvertCoefficient() {
    return convertCoefficient;
  }

  /**
   * 长度转换转换
   *
   * @param length 长度
   * @param from   当前长度的单位
   * @param to     需要返回的长度单位
   * @return
   */
  public static BigDecimal convert(BigDecimal length, LengthEnum from, LengthEnum to) {
    return length.multiply(BigDecimal.valueOf(from.getConvertCoefficient())).divide(BigDecimal.valueOf(to.getConvertCoefficient()),4, RoundingMode.HALF_UP);
  }

  public static LengthEnum getByName(String name) {
    for (LengthEnum value : LengthEnum.values()) {
      if (value.getName().equalsIgnoreCase(name)) {
        return value;
      }
    }
    return null;
  }
}
