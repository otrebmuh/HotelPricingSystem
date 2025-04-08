package com.hps.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hps.query", "com.hps.infrastructure"})
@EntityScan(basePackages = {"com.hps.common.domain.entity", "com.hps.query.readmodel"})
@EnableJpaRepositories(basePackages = {"com.hps.query.infrastructure"})
@EnableCaching
public class QuerySideApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuerySideApplication.class, args);
    }
} 