package com.kylin.http.client.controller;


import com.alibaba.fastjson.JSONObject;
import com.kylin.spring.utils.utils.file.FileUtil;
import com.kylin.spring.utils.utils.http.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {
    private static final String FILE_NAME = "downloadTestFile.txt";
    private static final String FILE_PATH = "src/test/resources/download/downloadTestFile.txt";



    @PostMapping("uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile file,String param) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url","uploadFile");
        jsonObject.put("param",param);
        FileUtil.getFile(file,"upload");
        return jsonObject.toJSONString();
    }
    @PostMapping("downloadFile")
    public void uploadFile(String param, HttpServletResponse response) throws IOException {
        if ("123".equals(param)){
            ResponseUtil.downloadFile(response,FILE_PATH,FILE_NAME);
            return;
        }
        throw new IllegalArgumentException("param error");

    }
}
