package com.hps.command.config;

import com.hps.common.domain.service.PriceCalculationStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for price calculation related beans.
 */
@Configuration
public class PriceCalculationConfig {
    
    /**
     * Creates the PriceCalculationStrategyFactory bean.
     *
     * @return A new PriceCalculationStrategyFactory instance
     */
    @Bean
    public PriceCalculationStrategyFactory priceCalculationStrategyFactory() {
        return new PriceCalculationStrategyFactory();
    }
} 