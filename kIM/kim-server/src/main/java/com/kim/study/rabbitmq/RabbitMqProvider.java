/*
package com.kim.study.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @ClassName RabbitMqProvider
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/4 15:34
 * @Version 1.0
 *//*




@RestController
@RequestMapping("/springboot-mq")
public class RabbitMqProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public String send(@RequestParam String str){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",str);
        rabbitTemplate.convertAndSend("amq.topic","#.duilie.#",jsonObject);
        return str;
    }
}
*/
