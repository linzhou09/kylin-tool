package com.kylin.spring.utils.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-07-22 17:18
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class BeanCopyUtil {

    public static <V> V copyBean(Object source, Class<V> targetClass) {
        V result = null;
        try {
            //创建对象
            result = targetClass.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> targetClass) {
        List<V> result = list.stream()
                .map(o -> copyBean(o, targetClass))
                .collect(Collectors.toList());
        return result;
    }
}
