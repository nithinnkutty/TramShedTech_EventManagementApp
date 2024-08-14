package com.tramshedtech.eventmanagement.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
@PropertySource("file:/etc/minio/minio.properties")
public class MinioConfiguration {
    //
    private String endpoint;    //connection url
    private String accesskey;   //Username
    private String secretKey;   //Password

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint).credentials(accesskey,secretKey).build();
    }
}
