package com.kylin.biz.utils.math;

import java.util.Objects;

/**
 * @author linzhou
 * @ClassName MathUtil.java
 * @createTime 2021年11月18日 13:55:00
 * @Description
 */
public class MathUtil {
    public static int add(Integer... a) {
        if (Objects.isNull(a)) {
            return 0;
        }
        int sum = 0;
        for (Integer integer : a) {
            if (Objects.isNull(integer)) {
                integer = 0;
            }
            sum += integer;
        }
        return sum;
    }

    public static double add(Double... a) {
        if (Objects.isNull(a)) {
            return 0;
        }
        double sum = 0;
        for (Double integer : a) {
            if (Objects.isNull(integer)) {
                integer = 0D;
            }
            sum += integer;
        }
        return sum;
    }

    public static float add(Float... a) {
        if (Objects.isNull(a)) {
            return 0;
        }
        float sum = 0;
        for (Float integer : a) {
            if (Objects.isNull(integer)) {
                integer = 0F;
            }
            sum += integer;
        }
        return sum;
    }

    public static long add(Long... a) {
        if (Objects.isNull(a)) {
            return 0;
        }
        long sum = 0;
        for (Long integer : a) {
            if (Objects.isNull(integer)) {
                integer = 0L;
            }
            sum += integer;
        }
        return sum;
    }
}
