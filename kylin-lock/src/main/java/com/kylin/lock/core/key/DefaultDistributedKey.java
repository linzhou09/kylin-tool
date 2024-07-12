package com.kylin.lock.core.key;



import com.kylin.lock.annotations.Lock;
import com.kylin.lock.model.param.LockParamInfo;
import com.kylin.lock.utils.LockKeyFormatUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    private BeanResolver beanResolver;
    public DefaultDistributedKey(BeanFactory beanFactory) {
        this.beanResolver = new BeanFactoryResolver(beanFactory);
    }

    @Override
    public List<String> getKey(ProceedingJoinPoint joinPoint, Lock lock) throws IllegalAccessException {
        String[] key = lock.key();
        if (Objects.isNull(key) || key.length == 0) {
            return Collections.emptyList();
        }

        List<String> keyList = Arrays.asList(key);

        List<LockParamInfo> lockParamInfoList = getParamInfos(joinPoint,lock);

        return LockKeyFormatUtil.buildDistributedKeyList(keyList, lockParamInfoList);
    }


    /**
     * 解析所有变量
     *
     * @param joinPoint
     * @return
     */
    private List<LockParamInfo> getParamInfos(ProceedingJoinPoint joinPoint, Lock lock) {
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Object rootObject = joinPoint.getThis();

        StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject, method, args, NAME_DISCOVERER);
        context.setBeanResolver(beanResolver);

        String[] param = lock.param();

        List<LockParamInfo> rlt = new ArrayList<>();

        if (Objects.nonNull(param)){

            for (String paramName : param) {
                if (paramName != null && !paramName.isEmpty()) {


                    String[] split = paramName.split(":");

                    String keyName = split[0];
                    String pName = keyName;
                    if (split.length > 1){
                        pName = split[1];
                    }

                    String value = PARSER.parseExpression(pName).getValue(context, String.class);



                    rlt.add(new LockParamInfo(keyName, value));
                }
            }
        }
        return rlt;
    }
}
