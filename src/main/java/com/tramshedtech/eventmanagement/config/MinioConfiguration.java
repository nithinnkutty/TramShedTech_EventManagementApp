package com.tramshedtech.eventmanagement.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
//@ConfigurationProperties可以将指定前缀开始的yml中配置信息自动赋值给对应的属性
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {
    //
    private String endpoint;    //连接url
    private String accesskey;   //用户名
    private String secretKey;   //密码

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint).credentials(accesskey,secretKey).build();
    }
}
