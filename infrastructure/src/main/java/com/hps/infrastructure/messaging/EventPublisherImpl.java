package com.hps.infrastructure.messaging;

import com.hps.common.events.EventPublisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of EventPublisher using RabbitMQ.
 */
@Component
public class EventPublisherImpl implements EventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final String defaultExchange;
    
    public EventPublisherImpl(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.template.exchange:hps-events}") String defaultExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.defaultExchange = defaultExchange;
    }
    
    @Override
    public <T> void publish(T event) {
        String routingKey = getRoutingKeyFromEvent(event);
        publish(routingKey, event);
    }
    
    @Override
    public <T> void publish(String topic, T event) {
        rabbitTemplate.convertAndSend(defaultExchange, topic, event);
    }
    
    /**
     * Determine the routing key based on the event class name.
     * For example, PriceChangedEvent becomes "price.changed"
     */
    private <T> String getRoutingKeyFromEvent(T event) {
        String className = event.getClass().getSimpleName();
        
        // Remove "Event" suffix
        if (className.endsWith("Event")) {
            className = className.substring(0, className.length() - 5);
        }
        
        // Convert camel case to dot-separated lowercase
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                result.append('.');
            }
            result.append(Character.toLowerCase(c));
        }
        
        return result.toString();
    }
} 