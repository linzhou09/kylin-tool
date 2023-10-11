package com.kylin.biz.utils.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 重量单位
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public enum WeightEnum {
  G("G", 1.0),
  KG("KG", 1000.0),
  T("T", 1000000.0),
  /**
   * 英镑
   */
  LB("LB",453.59237),
  /**
   * 盎司
   */
  OZ("OZ",28.3495),

  ;

  /**
   * 单位名称
   */
  private final String name;
  /**
   * 同克的转换比例
   */
  private final Double convertCoefficient;

  WeightEnum(String name, Double convertCoefficient) {
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
   * 重量转换
   *
   * @param amount 重量
   * @param from   当前重量的单位
   * @param to     需要返回的重量单位
   * @return
   */
  public static BigDecimal convert(BigDecimal amount, WeightEnum from, WeightEnum to) {
    return amount.multiply(BigDecimal.valueOf(from.getConvertCoefficient())).divide(BigDecimal.valueOf(to.getConvertCoefficient()),4, RoundingMode.HALF_UP);
  }

  public static WeightEnum getByName(String name) {
    for (WeightEnum value : WeightEnum.values()) {
      if (value.getName().equalsIgnoreCase(name)) {
        return value;
      }
    }
    return null;
  }
}
