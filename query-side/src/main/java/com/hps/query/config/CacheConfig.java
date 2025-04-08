package com.hps.query.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Configuration for Redis caching.
 */
@Configuration
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Configure serialization
        RedisSerializationContext.SerializationPair<String> keySerializer = 
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
        
        RedisSerializationContext.SerializationPair<Object> valueSerializer = 
                RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
        
        // Configure default cache settings
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))  // Cache entries expire after 30 minutes
                .disableCachingNullValues()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(valueSerializer);
        
        // Create the cache manager
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withCacheConfiguration("hotelPrices", defaultCacheConfig.entryTtl(Duration.ofMinutes(15)))
                .withCacheConfiguration("roomTypePrices", defaultCacheConfig.entryTtl(Duration.ofMinutes(15)))
                .build();
    }
} 