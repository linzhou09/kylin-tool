package com.kylin.http.client.client;



import com.kylin.http.client.apache.proxy.ApacheHttpProxy;
import com.kylin.http.client.biz.annotation.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableHttpClient(defaultProxy = ApacheHttpProxy.class)
@ComponentScan(basePackages = {"com.kylin.http.client"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
