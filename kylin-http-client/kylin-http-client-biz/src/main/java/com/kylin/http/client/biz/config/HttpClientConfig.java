package com.kylin.http.client.biz.config;


import com.kylin.biz.utils.date.DateUtil;
import com.kylin.http.client.biz.constant.HttpClientConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 全局配置类
 *
 * @author linzhou
 */
@Component
@ConfigurationProperties(prefix = "httpclient")
@ComponentScan(basePackages = "com.kylin.http.client.biz")
@Data
public class HttpClientConfig {

  /**
   * 全局默认的动态代理
   */
  private String baseUrl;
  /**
   * 默认的文件下载根路径
   */
  private String downLoadFileBasePath;

  /**
   * 获取文件下载路径
   *
   * @return
   */
  public String getDownLoadFilePath() {
    String basePath = downLoadFileBasePath;

    if (Objects.isNull(basePath) || basePath.lastIndexOf(HttpClientConstant.FILE_PATH_SPLIT) != basePath.length() - 1) {
      basePath = basePath + HttpClientConstant.FILE_PATH_SPLIT;
    }

    Date now = new Date();
    return basePath + DateUtil.toString(now, DateUtil.YYYY_MM_DD) + '/' + now.getTime();
  }
}
