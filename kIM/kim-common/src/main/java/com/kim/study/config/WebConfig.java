
package com.kim.study.config;

import com.kim.study.interceptor.GlobalInterseptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/3 20:12
 * @Version 1.0
 */

@Configuration
@RefreshScope
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private GlobalInterseptor globalInterseptor;

    @Value("${kim.unAuthorization.url}")
    private List<String> unAuthorizationUrl;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(globalInterseptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(unAuthorizationUrl);
    }
}

