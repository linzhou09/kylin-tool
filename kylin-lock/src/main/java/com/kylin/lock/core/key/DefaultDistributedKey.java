package com.kylin.lock.core.key;


import com.kylin.biz.utils.common.annotations.KylinParam;
import com.kylin.biz.utils.model.bo.kylin.param.KylinParamInfo;
import com.kylin.biz.utils.reflect.ReflectUtil;
import com.kylin.biz.utils.string.StringFormatUtil;
import com.kylin.lock.annotations.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @date : 2022/5/20 11:27
 * @author: linzhou
 * @description : 默认的分布式锁的key的解析类
 */
@Component
public class DefaultDistributedKey implements DistributedKey {

    /**
     * 读取redisKey中的变量名称的正则表达式
     * "asdfsd{name}dsfds{value}" =[{"name"},{"value"}]
     */
    private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("\\{.*?}");

    @Override
    public List<String> getKey(ProceedingJoinPoint joinPoint, Lock lock) throws IllegalAccessException {
        String[] key = lock.key();
        if (Objects.isNull(key) || key.length == 0) {
            return Collections.emptyList();
        }

        List<String> keyList = Arrays.asList(key);
        //获取key中变量
        Set<String> fieldNames = StringFormatUtil.getParamNamesByKeys(keyList);
        if (fieldNames.isEmpty()) {
            return keyList;
        }

        List<KylinParamInfo> kylinParamInfoList = getParamInfos(joinPoint);

        return StringFormatUtil.buildDistributedKeyList(keyList, kylinParamInfoList);
    }


    /**
     * 解析所有变量
     *
     * @param joinPoint
     * @return
     */
    private List<KylinParamInfo> getParamInfos(ProceedingJoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args) || args.length == 0) {
            return new ArrayList<>();
        }

        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        List<KylinParamInfo> rlt = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (isBasicClass(arg)) {
                KylinParam param = ReflectUtil.getAnnotation(parameterAnnotations[i], KylinParam.class);
                if (Objects.nonNull(param)) {
                    rlt.add(new KylinParamInfo(arg, param.value(), param));
                } else {
                    rlt.add(new KylinParamInfo(arg, "param" + i, null));
                }
            } else {
                List<KylinParamInfo> kylinParamInfos = ReflectUtil.getKylinParamInfos(arg);
                rlt.addAll(kylinParamInfos);
            }
        }
        return rlt;
    }


    /**
     * 是否是基础类型
     *
     * @param o
     * @return
     */
    protected boolean isBasicClass(Object o) {
        if (o instanceof Integer
                || o instanceof String
                || o instanceof Double
                || o instanceof Float
                || o instanceof Long
                || o instanceof BigDecimal) {
            return true;
        }
        return false;
    }
}
