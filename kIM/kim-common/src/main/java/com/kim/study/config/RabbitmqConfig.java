package com.kim.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName RabbitmqConfig
 * @Description rabbitmq配置类
 * @Author KIM
 * @Date 2022/3/5 20:00
 * @Version 1.0
 */

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "kim.rabbitmq",ignoreUnknownFields = true)
public class RabbitmqConfig {
    /**
     * 挂载点
     */
    private String virtualHost;

    /**
     * IP地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 最小的消费者数量
     */
    private int listenerSimpleConcurrency;

    /**
     * 最大的消费者数量
     */
    private int listenerSimpleMaxConcurrency;

    /**
     * 指定一个请求能处理多少个消息，如果有事务的话，必须大于等于transaction数量.
     */
    private int listenerSimplePrefetch;

    @Primary
    @Bean(name = "rabbitmqConnectionFactory")
    public CachingConnectionFactory rabbitmqCachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        //老版本配置,配置消息确认机制确认消息是由交换机路由到队列中,可以进行确认回调
        cachingConnectionFactory.setPublisherReturns(true);
        /*老版本配置,配置消息确认机制确认消息是否到broker,可以进行确认回调
        cachingConnectionFactory.setPublisherConfirms(true);*/
        //老版本配置,配置消息确认机制确认消息是否到broker,可以进行确认回调
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return cachingConnectionFactory;
    }

    /**
     * singleton can't set multi times callback
     * @return
     */
    @Primary
    @Bean(name = "rabbitmqTemplate")
    public RabbitTemplate rabbitmqTemplate(@Qualifier("rabbitmqConnectionFactory")CachingConnectionFactory cachingConnectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("rabbitmq message send succeed:correlationData({}),ack({}),cause({})",correlationData, ack, cause);
            } else {
                log.info("rabbitmq message send failed:correlationData({}),ack({}),cause({})",correlationData, ack, cause);
            }
        });

        //交换机和队列未绑定提示结果
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("rabbitmq message lose:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;

    }


    @Bean(name = "rabbitmqSingleListenerContainer")
    public SimpleRabbitListenerContainerFactory rabbitmqSingleListenerContainer(SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer,
                                                                                 @Qualifier("rabbitmqConnectionFactory")CachingConnectionFactory cachingConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean(name = "rabbitmqMultiListenerContainer")
    public SimpleRabbitListenerContainerFactory rabbitmqMultiListenerContainer(SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer,
                                                                                @Qualifier("rabbitmqConnectionFactory")CachingConnectionFactory cachingConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(listenerSimpleConcurrency);
        factory.setMaxConcurrentConsumers(listenerSimpleMaxConcurrency);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getListenerSimpleConcurrency() {
        return listenerSimpleConcurrency;
    }

    public void setListenerSimpleConcurrency(int listenerSimpleConcurrency) {
        this.listenerSimpleConcurrency = listenerSimpleConcurrency;
    }

    public int getListenerSimpleMaxConcurrency() {
        return listenerSimpleMaxConcurrency;
    }

    public void setListenerSimpleMaxConcurrency(int listenerSimpleMaxConcurrency) {
        this.listenerSimpleMaxConcurrency = listenerSimpleMaxConcurrency;
    }

    public int getListenerSimplePrefetch() {
        return listenerSimplePrefetch;
    }

    public void setListenerSimplePrefetch(int listenerSimplePrefetch) {
        this.listenerSimplePrefetch = listenerSimplePrefetch;
    }
}
