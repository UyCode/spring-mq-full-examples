package com.uycode.mqproducer.controller;

import com.uycode.mqproducer.model.Message;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ahmatjan(UyCode)
 * @email Hyper-Hack@outlook.com
 * @since 11/23/2021
 */
@RestController
public class MqProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private FanoutExchange fanoutExchange;

    @Autowired
    private TopicExchange topicExchange;

    @PostMapping("/directa")
    public String directA(@RequestBody Message message) {
        template.convertAndSend(directExchange.getName(), "routing.A", message);
        return "message send!";
    }

    @PostMapping("/directb")
    public String directB(@RequestBody Message message) {
        template.convertAndSend(directExchange.getName(), "routing.B", message);
        return "message send!";
    }

    @PostMapping("/fanout")
    public String fanoutA(@RequestBody Message message) {
        template.convertAndSend(fanoutExchange.getName(), "", message);
        return "message send!";
    }

   /**
    * 这个会把消息绑定到A队列以及all对了里
    * @params message
    * @return java.lang.String
    */
    @PostMapping("/topica")
    public String topicA(@RequestBody Message message) {
        template.convertAndSend(topicExchange.getName(), "routing.A", message);
        return "message send!";
    }

    @PostMapping("/header/{color}")
    public String topicA(@PathVariable(value = "color") String color) {

        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", color);
        MessageConverter converter = new SimpleMessageConverter();
        //这里写包名是因为amqp的Message类跟我自己创建的Message类名冲突了，因此使用类的真名避免冲突。
        // 小小的知识：其实一个类真正的名字是它的包（路径）名 + 类本身的名
        org.springframework.amqp.core.Message message = converter.toMessage(color, properties);
        template.send(message);
        return "message send!";
    }

    /**
     * defaultTemplate 默认配置，只需提供路由键即可（即路由键就是队列的名字）
     * @params message
     * @return java.lang.String
     */
    @PostMapping("/default")
    public String defaultTemplate(@RequestBody Message message) {
        template.convertAndSend("queue.A", message);
        return "message send!";
    }

}
