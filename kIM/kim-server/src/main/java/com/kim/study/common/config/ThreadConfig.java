package com.kim.study.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @ClassName ThreadConfig
 * @Description ThreadConfig配置类
 * @Author KIM
 * @Date 2022/3/8 16.38
 * @Version 1.0
 */
/**
 * 在插入方法中使用注解`@Async("taskExecutor")`进行异步调用，
 * 使得该方法加入线程池运行，这个值为在是线程池中的方法名称，
 * 需要与`@EnableAsync`一块使用，之前在创建线程池的类中已经开启
 */

@EnableAsync
@Configuration
public class ThreadConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}