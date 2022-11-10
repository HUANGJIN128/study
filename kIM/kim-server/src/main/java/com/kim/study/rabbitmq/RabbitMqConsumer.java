/*
package com.kim.study.rabbitmq;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

*/
/**
 * @ClassName RabbitMqConsumer
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/5 20:00
 * @Version 1.0
 *//*

@Service
@Slf4j
public class RabbitMqConsumer {



    @RabbitHandler
    @RabbitListener(queues = {"huangjin.duilie"})
    private void comsumer(Object data, Channel channel) {


        Message message=(Message) data;
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //Message message=(Message)data;
            byte[] body = message.getBody();
            String s = new String(body, "UTF-8");
            Map map = JSONObject.parseObject(s, Map.class);
            //int i=1/0;
            System.out.println("消费消息成功"+s);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            if(e instanceof JSONException){
                try {
                    channel.basicNack(deliveryTag,false,false);
                } catch (IOException ex) {
                    log.error("消息消费失败,消息格式错误",e);
                    log.error("消息格式错误，失败原因:{}",e.getMessage(),e);
                }
            }
            if(e instanceof NullPointerException||e instanceof ClassCastException||e instanceof ArithmeticException||e instanceof NumberFormatException){

                try {
                    channel.basicNack(deliveryTag,false,true);
                } catch (IOException ex) {
                    log.error("消息格式错误，失败原因:{}",e.getMessage());
                }
            }


        }
    }
}
*/
