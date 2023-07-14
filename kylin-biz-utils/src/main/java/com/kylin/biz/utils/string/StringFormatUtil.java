package com.kylin.biz.utils.string;


import com.kylin.biz.utils.model.bo.kylin.param.KylinParamInfo;
import com.kylin.biz.utils.exception.BizException;
import com.kylin.biz.utils.model.enums.exception.BizResultCodeEnums;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * CopyRight : <company domain>
 * Project :  kylin-tool
 * Comments : 字符串格式化工具类
 * JDK version : JDK1.8
 * Create Date : 2023-06-26 14:43
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class StringFormatUtil {

  /**
   * 读取redisKey中的变量名称的正则表达式
   * "asdfsd{name}dsfds{value}" =[{"name"},{"value"}]
   */
  public static final Pattern FIELD_NAME_PATTERN = Pattern.compile("\\{.*?}");


  public static List<String> buildDistributedKeyList(List<String> keyList, List<KylinParamInfo> kylinParamInfoList){

    List<String> distributedKeyList = new ArrayList<>();
    for (String key : keyList) {
      distributedKeyList.add(buildDistributedKey(key, kylinParamInfoList));
    }
    return distributedKeyList;
  }

  public static String buildDistributedKey(String key, List<KylinParamInfo> kylinParamInfoList) {
    //获取key中变量
    Set<String> fieldNames = getParamNamesByKey(key);
    if (fieldNames.isEmpty()) {
      return key;
    }

    Map<String, KylinParamInfo> paramMap = kylinParamInfoList.stream().filter(o -> Objects.nonNull(o.getKylinParam())).collect(Collectors.toMap(KylinParamInfo::getParamName, o -> o, (o1, o2) -> o1));

    Map<String, KylinParamInfo> map = kylinParamInfoList.stream().filter(o -> Objects.isNull(o.getKylinParam())).collect(Collectors.toMap(KylinParamInfo::getParamName, o -> o, (o1, o2) -> o1));

    String distributedLockKey = key;
    Iterator<String> it = fieldNames.iterator();
    while (it.hasNext()) {

      String fieldName = it.next();
      KylinParamInfo reflectField = paramMap.get(fieldName);
      if (Objects.isNull(reflectField)) {
        reflectField = map.get(fieldName);
      }

      if (Objects.nonNull(reflectField)) {
        distributedLockKey = distributedLockKey.replace("{" + fieldName + "}", "{" + reflectField.getValue() + "}");
        it.remove();
      }
    }
    if (fieldNames.size() > 0) {
      throw new BizException(BizResultCodeEnums.STRING_FORMAT_ERROR_NOT_FIND_PARAM, String.join(",", fieldNames));
    }
    return distributedLockKey;
  }


  public static  Set<String> getParamNamesByKeys(List<String> keys) {
    Set<String> fieldNames = new HashSet<>();

    for (String key : keys) {
      fieldNames.addAll(getParamNamesByKey(key));
    }

    return fieldNames;
  }


  /**
   * 获取key中变量
   *
   * @param key
   * @return
   */
  public static Set<String> getParamNamesByKey(String key) {
    Set<String> fieldNames = new HashSet<>();

    //生成匹配器，输入待匹配字符序列
    Matcher m = FIELD_NAME_PATTERN.matcher(key);
    //注意！find()一次，就按顺序扫描到了一个匹配的字符串，此时group()返回的就是该串。
    while (m.find()) {
      //打印匹配的子串
      String group = m.group();

      if (StringUtils.isNotBlank(group) && group.length() > 2) {
        group = group.substring(1, group.length() - 1);
        fieldNames.add(group);
      }
    }

    return fieldNames;
  }
}
