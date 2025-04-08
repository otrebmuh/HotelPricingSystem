package com.hps.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ messaging.
 */
@Configuration
public class RabbitMQConfig {
    
    @Value("${spring.rabbitmq.template.exchange:hps-events}")
    private String exchangeName;
    
    @Value("${spring.rabbitmq.template.default-receive-queue:hps-events-queue}")
    private String queueName;
    
    @Value("${spring.rabbitmq.template.routing-key:price.#}")
    private String routingKey;
    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }
    
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }
    
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
} 