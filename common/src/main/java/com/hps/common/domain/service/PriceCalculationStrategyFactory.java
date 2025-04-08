package com.hps.common.domain.service;

import com.hps.common.domain.service.impl.CombinedPriceCalculationStrategy;
import com.hps.common.domain.service.impl.DayOfWeekPriceCalculationStrategy;
import com.hps.common.domain.service.impl.DemandBasedPriceCalculationStrategy;
import com.hps.common.domain.service.impl.SeasonalPriceCalculationStrategy;
import com.hps.common.domain.service.impl.StandardPriceCalculationStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating price calculation strategy instances based on the strategy type.
 */
public class PriceCalculationStrategyFactory {
    
    private final Map<String, PriceCalculationStrategy> strategies = new HashMap<>();
    
    public PriceCalculationStrategyFactory() {
        // Register default strategies
        registerStrategy(new StandardPriceCalculationStrategy());
        registerStrategy(new DayOfWeekPriceCalculationStrategy());
        registerStrategy(new SeasonalPriceCalculationStrategy());
        registerStrategy(new DemandBasedPriceCalculationStrategy());
        
        // Register a default combined strategy that uses day of week and seasonal pricing
        registerStrategy(new CombinedPriceCalculationStrategy(
                new DayOfWeekPriceCalculationStrategy(),
                new SeasonalPriceCalculationStrategy()
        ));
    }
    
    public void registerStrategy(PriceCalculationStrategy strategy) {
        strategies.put(strategy.getType(), strategy);
    }
    
    public PriceCalculationStrategy getStrategy(String type) {
        PriceCalculationStrategy strategy = strategies.get(type);
        
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown price calculation strategy type: " + type);
        }
        
        return strategy;
    }
    
    public boolean hasStrategy(String type) {
        return strategies.containsKey(type);
    }
} 