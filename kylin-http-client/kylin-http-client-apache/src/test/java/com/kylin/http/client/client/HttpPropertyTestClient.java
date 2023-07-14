package com.kylin.http.client.client;


import com.kylin.http.client.biz.annotation.HttpClient;
import com.kylin.http.client.biz.annotation.HttpFile;
import com.kylin.http.client.biz.annotation.HttpParam;
import com.kylin.http.client.biz.enums.HttpRequestMethod;
import com.kylin.http.client.biz.response.HttpResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@HttpClient(url = HttpClientTest.PROPERTY_URL, pathMethodName = false)
public interface HttpPropertyTestClient {
  /**
   * 普通get请求
   *
   * @param name
   * @param password
   * @return
   */
  @HttpClient(path = "getLogin")
  String getLogin(@HttpParam("name") String name, @HttpParam("password") String password);

  /**
   * post请求
   *
   * @param param
   * @return
   */
  @HttpClient(path = "postLogin", method = HttpRequestMethod.POST)
  HttpResult<LoginParam> postLogin(LoginParam param);

  /**
   * 无参的get请求
   *
   * @return
   */
  @HttpClient(path = "getInt", method = HttpRequestMethod.GET)
  int getInt();

  /**
   * 无参的post请求
   *
   * @return
   */
  @HttpClient(path = "getDouble", method = HttpRequestMethod.POST)
  double getDouble();

  /**
   * 无参的post请求
   *
   * @return
   */
  @HttpClient(path = "post", method = HttpRequestMethod.POST)
  String post();

  /**
   * 文件上传
   *
   * @param file
   * @param param
   * @return
   */
  @HttpClient(url = "http://127.0.0.1:8080/file", path = "uploadFile", method = HttpRequestMethod.POST)
  String uploadMultipartFile(@HttpFile("file") MultipartFile file, @HttpParam("param") String param);

  /**
   * 文件上传
   *
   * @param file
   * @param param
   * @return
   */
  @HttpClient(url = "http://127.0.0.1:8080/file", path = "uploadFile", method = HttpRequestMethod.POST)
  String uploadFile(@HttpFile("file") File file, @HttpParam("param") String param);
}
