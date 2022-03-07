package com.kim.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: ServerApplication
 * @Description: auth2单点
 * @Author: KIM
 * @Date: 2022/2/22 18:01
 * @Version: 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication()
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }

}
