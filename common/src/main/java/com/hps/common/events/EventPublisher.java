package com.hps.common.events;

/**
 * Interface for event publishing infrastructure.
 * This is used to abstract the implementation details of event publishing.
 */
public interface EventPublisher {
    
    /**
     * Publish an event to be consumed by other services.
     *
     * @param event The event to publish
     * @param <T> The type of event
     */
    <T> void publish(T event);
    
    /**
     * Publish an event to a specific topic.
     *
     * @param topic The topic to publish to
     * @param event The event to publish
     * @param <T> The type of event
     */
    <T> void publish(String topic, T event);
} 