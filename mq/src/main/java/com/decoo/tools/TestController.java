package com.decoo.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author ghd
 * @date 2019/12/18 14:15
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/test")
    public void test() {
        send();
    }

    public void send() {
        for (int i = 5; i > 0; i--) {
            long expireTime = 120000 * i;
//            if(expireTime == 0) {
//                expireTime= 120000;
//            }

            String msg = "--第" + (i+1) + "*******测试*********";
            var message = MessageBuilder.withBody(msg.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding(StandardCharsets.UTF_8.toString())
                    .build();
            MessageProperties messageProperties = message.getMessageProperties();

            messageProperties.setExpiration(String.valueOf(expireTime));
            Message queryMessage = new Message(message.getBody(), messageProperties);
            rabbitTemplate.convertAndSend("vpc.deduct", "AllInPay", queryMessage);
        }


    }
}
