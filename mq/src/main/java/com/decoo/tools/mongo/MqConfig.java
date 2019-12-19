package com.decoo.tools.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author ghd
 * @date 2019/12/18 14:08
 */
@Configuration
@RequiredArgsConstructor
public class MqConfig {
    private final AmqpAdmin amqpAdmin;

    @Bean
    public String createDelayQueryDeductQueue() {
        //主要exchange和queue
        String name = "test.ttl";
        TopicExchange exchange = new TopicExchange(name);
        amqpAdmin.declareExchange(exchange);
        String queueName = name;
        HashMap<String, Object> argus = new HashMap<>(2);
        argus.put("x-dead-letter-exchange", "DLX");
        argus.put("x-dead-letter-routing-key", queueName);
        Queue queue = new Queue(queueName, true, false, false, argus);
        amqpAdmin.declareQueue(queue);
             //绑定延迟查询交换机和队列
        Binding binding = BindingBuilder.bind(queue).to(exchange)
                .with("message");
        amqpAdmin.declareBinding(binding);

//
////        死信队列
//        createDlqQueue(queueName, "immediate");
//        argus = new HashMap<>(2);
//        argus.put("x-dead-letter-exchange", "DLX");
//        argus.put("x-dead-letter-routing-key", queueName + ".immediate");
//        Queue dlq = new Queue(queueName + ".immediate", true, false, false, argus);
//        amqpAdmin.declareQueue(dlq);
//        String dlxName = "DLX";
//        final DirectExchange dlx = new DirectExchange(dlxName);
//        amqpAdmin.declareExchange(dlx);
//        Binding immBinding = BindingBuilder.bind(dlq).to(dlx).with(queueName);
//        amqpAdmin.declareBinding(immBinding);
////
        //死信队列
        Queue dlq  = new Queue(queueName + ".dlq", true, false, false, null);
        amqpAdmin.declareQueue(dlq);
        Binding dlqBinding = BindingBuilder.bind(dlq).to(exchange).with(queueName);
        amqpAdmin.declareBinding(dlqBinding);

        return queueName;
    }
}
