package com.tramshedtech.eventmanagement.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
// @ConfigurationProperties Configuration information in the yml starting with the specified prefix can be automatically assigned to the corresponding attribute.
@ConfigurationProperties(prefix = "minio")
//@PropertySource("file:/etc/minio/minio.properties")
public class MinioConfiguration {
    //
    private String endpoint;    // Connection url
    private String accesskey;   // user ID
    private String secretKey;   // cryptographic

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint).credentials(accesskey,secretKey).build();
    }
}
