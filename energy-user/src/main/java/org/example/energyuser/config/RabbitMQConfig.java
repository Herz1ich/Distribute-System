package org.example.energyuser.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 监听的队列名称（与发送方保持一致）
    public static final String PRODUCER_QUEUE = "producer-queue";
    public static final String USER_QUEUE = "user-queue";

    // 发送用的交换机和 routing key
    public static final String UPDATE_QUEUE = "update-queue";
    public static final String UPDATE_EXCHANGE = "update-exchange";
    public static final String UPDATE_ROUTING_KEY = "update-routing-key";

    // 接收 producer 的消息队列
    @Bean
    public Queue producerQueue() {
        return new Queue(PRODUCER_QUEUE);
    }

    // 接收 user 的消息队列
    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE);
    }

    // 发送给 current-percentage-service 的更新队列
    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE);
    }

    @Bean
    public TopicExchange updateExchange() {
        return new TopicExchange(UPDATE_EXCHANGE);
    }

    @Bean
    public Binding updateBinding(Queue updateQueue, TopicExchange updateExchange) {
        return BindingBuilder.bind(updateQueue).to(updateExchange).with(UPDATE_ROUTING_KEY);
    }

    // ✅ 使用 JSON 消息格式（核心配置）
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
