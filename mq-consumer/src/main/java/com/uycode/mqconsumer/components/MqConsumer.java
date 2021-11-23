package com.uycode.mqconsumer.components;

import com.uycode.mqconsumer.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ahmatjan(UyCode)
 * @email Hyper-Hack@outlook.com
 * @since 11/22/2021
 */
@Component
@Slf4j
public class MqConsumer {

    @RabbitListener(queues = "queue.A")
    public void queueA(Message messages) {
        log.info("received message from queue A -> {0}: " + messages);
    }

    @RabbitListener(queues = "queue.B")
    public void queueB(Message messages) {
        log.info("received message from queue B -> {0}: " + messages);
    }

    @RabbitListener(queues = "queue.all")
    public void queueAll(Message messages) {
        log.info("received message from queue All -> {0}: " + messages);
    }

    @RabbitListener(queues = "queue.A")
    public void headerA(String messages) {
        log.info("received message from queue A -> {0}: " + messages);
    }

    @RabbitListener(queues = "queue.B")
    public void headerB(String messages) {
        log.info("received message from queue B -> {0}: " + messages);
    }

    @RabbitListener(queues = "queue.all")
    public void headerAll(String messages) {
        log.info("received message from queue All -> {0}: " + messages);
    }

}
