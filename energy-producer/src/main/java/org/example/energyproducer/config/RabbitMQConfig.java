package org.example.energyproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${producer.exchange}")
    private String exchange;

    @Value("${producer.routing-key}")
    private String routingKey;

    @Bean
    public Queue producerQueue() {
        return new Queue("producer-queue");
    }

    @Bean
    public DirectExchange producerExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding producerBinding(Queue producerQueue, DirectExchange producerExchange) {
        return BindingBuilder.bind(producerQueue).to(producerExchange).with(routingKey);
    }

    // ✅ JSON converter
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







