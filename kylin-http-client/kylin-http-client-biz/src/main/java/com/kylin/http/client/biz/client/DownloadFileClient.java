package com.kylin.http.client.biz.client;



import com.kylin.http.client.biz.annotation.HttpClient;
import com.kylin.http.client.biz.annotation.HttpParam;
import com.kylin.http.client.biz.context.header.HttpHeader;
import com.kylin.http.client.biz.context.url.HttpRequestUrl;
import com.kylin.http.client.biz.enums.HttpRequestMethod;

import java.io.File;

@HttpClient(method = HttpRequestMethod.POST)
public interface DownloadFileClient {

    /**
     * 下载文件
     * @param httpUrl
     * @param param
     * @return
     */
    File downloadFile(HttpRequestUrl httpUrl, HttpHeader header, @HttpParam Object param);

}
