package com.uycode.mqconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmatjan(UyCode)
 * @email Hyper-Hack@outlook.com
 * @since 11/22/2021
 */

@Configuration
public class RabbitMqConfig {

    public static final String ROUTING_A = "routing.A";
    public static final String ROUTING_B = "routing.B";

    @Bean
    public Queue queueA() {
        return new Queue("queue.A", false);
    }

    @Bean
    public Queue queueB() {
        return new Queue("queue.B", false);
    }

    @Bean
    public Queue queueAll() {
        return new Queue("queue.all", false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange.fanout");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("exchange.topic");
    }

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("exchange.header");
    }

    @Bean
    Binding bindingDirectA(Queue queueA, DirectExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .with(ROUTING_A);
    }

    @Bean
    Binding bindingDirectB(Queue queueB, DirectExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .with(ROUTING_B);
    }

    @Bean
    Binding bindingFanoutA(Queue queueA, FanoutExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange);
    }

    @Bean
    Binding bindingFanoutB(Queue queueB, FanoutExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange);
    }

    @Bean
    Binding bindingTopicA(Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with(ROUTING_A);
    }

    @Bean
    Binding bindingTopicB(Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with(ROUTING_B);
    }

    @Bean
    Binding bindingAll(Queue queueAll, TopicExchange exchange) {
        return BindingBuilder.bind(queueAll).to(exchange).with("routing.all");
    }

    @Bean
    Binding bindingHeaderA(Queue queueA, HeadersExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .where("color")
                .matches("red");
    }

    @Bean
    Binding bindingHeaderB(Queue queueB, HeadersExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .where("color")
                .matches("blue");
    }

    @Bean
    Binding bindingHeaderAll(Queue queueAll, HeadersExchange exchange) {
        return BindingBuilder.bind(queueAll)
                .to(exchange)
                .where("color")
                .matches("others");
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
