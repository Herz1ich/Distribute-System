package org.example.usageservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PRODUCER_QUEUE = "producer-queue";
    public static final String USER_QUEUE = "user-queue";

    public static final String UPDATE_QUEUE = "update-queue";
    public static final String UPDATE_EXCHANGE = "update-exchange";
    public static final String UPDATE_ROUTING_KEY = "update-routing-key";

    @Bean
    public Queue producerQueue() {
        return new Queue(PRODUCER_QUEUE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE);
    }

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

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
