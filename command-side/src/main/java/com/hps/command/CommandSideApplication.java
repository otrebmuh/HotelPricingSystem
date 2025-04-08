package com.hps.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hps.command", "com.hps.infrastructure"})
@EntityScan(basePackages = {"com.hps.common.domain.entity", "com.hps.command.domain", "com.hps.command.infrastructure.persistence.entity"})
@EnableJpaRepositories(basePackages = {"com.hps.command.infrastructure"})
public class CommandSideApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandSideApplication.class, args);
    }
} 