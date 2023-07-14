package com.kylin.http.client.client;


import com.alibaba.fastjson.JSON;
import com.kylin.http.client.biz.client.DownloadFileClient;
import com.kylin.http.client.biz.context.url.HttpRequestUrl;
import com.kylin.http.client.biz.response.HttpResult;
import com.kylin.http.client.controller.DownloadFileParam;
import com.kylin.spring.utils.utils.file.FileUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Log4j2
public class HttpClientTest extends BaseTest {

  public static final String PROPERTY_URL = "http.client.property-url";

  @Autowired
  private HttpTestClient httpTestClient;
  @Autowired
  private HttpPropertyTestClient httpPropertyTestClient;
  @Autowired
  private DownloadFileClient httpDownloadClient;

  /**
   * post 请求
   */
  @Test
  public void postTest() {
    LoginParam p = new LoginParam();
    p.setPassword("12345678");
    p.setName("src/main/resources/httpClient");
    HttpResult<LoginParam> param = httpPropertyTestClient.postLogin(p);
    log.info("rlt1:" + JSON.toJSONString(param));
    String rlt = httpTestClient.postLogin(p);
    log.info("rlt1:" + rlt);
  }

  /**
   * post 请求
   */
  @Test
  public void getDouble() {
    double rlt = httpTestClient.getDouble();
    log.info("rlt1:" + rlt);
  }

  /**
   * get 请求
   */
  @Test
  public void getInt() {

    int rlt = httpTestClient.getInt();
    log.info("rlt1:" + rlt);
  }

  /**
   * post 无参数请求
   */
  @Test
  public void postTest2() {
    String rlt = httpTestClient.post();
    log.info("rlt1:" + rlt);
  }

  /**
   * get 请求
   */
  @Test
  public void getTest() {
    String rlt = httpTestClient.getLogin("src/main/resources/httpClient", "12345678");
    log.info("rlt1:" + rlt);
    LoginParam p = new LoginParam();
    p.setPassword("12345678");
    p.setName("src/main/resources/httpClient");
    rlt = httpTestClient.getLogin(p);
    log.info("rlt1:" + rlt);
  }

  /**
   * 文件上传
   *
   * @throws FileNotFoundException
   */
  @Test
  public void uploadFile() throws FileNotFoundException {
    File file = FileUtil.getFile("src/test/resources/file/fileTest.txt");
    String rlt = httpTestClient.uploadFile(file, "uploadFile");
    log.info("rlt1:" + rlt);
  }

  /**
   * 文件下载
   */
  @Test
  public void downloadFileTest() throws Exception {
    HttpRequestUrl httpUrl = new HttpRequestUrl("http://127.0.0.1:8080/file/downloadFile");

    File file = httpDownloadClient.downloadFile(httpUrl, null, new DownloadFileParam("123"));
    String s = new String(getByte(new FileInputStream(file)), StandardCharsets.UTF_8);
    log.info("downloadFileTest test:" + s);
  }

  public byte[] getByte(InputStream inputStream) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = inputStream.read(buffer))) {
      output.write(buffer, 0, n);
    }
    return output.toByteArray();
  }

}
