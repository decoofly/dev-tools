package com.decoo.tools.mongo;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author ghd
 * @date 2019/12/18 14:43
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqMonitor implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {


        log.info("消息为： 【{}】", message);
    }

    @Override
    public void onMessage(Message message) {

        log.info("消息为： 【{}】", message);
    }
}
