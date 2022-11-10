package com.kim.study.common.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @ClassName MinioClientConfig
 * @Description
 * @Author KIM
 * @Date 2022/9/8 14:05
 * @Version 1.0
 */

@Configuration
@Slf4j
@DependsOn(value = "dospMultiAnalysisProperties")
public class MinioClientConfig {

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(NacosPropertiesConfig.get().getMinioEndpoint())
                .credentials(
                        NacosPropertiesConfig.get().getMinioUserName(),
                        NacosPropertiesConfig.get().getMinioPassword())
                .build();
    }
}
