package com.hps.common.domain.service.impl;

import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Calculation strategy that combines multiple strategies in sequence.
 * Each strategy is applied in order, with each one modifying the price from the previous strategy.
 */
public class CombinedPriceCalculationStrategy implements PriceCalculationStrategy {
    
    public static final String TYPE = "COMBINED";
    
    private final List<PriceCalculationStrategy> strategies;
    
    public CombinedPriceCalculationStrategy(List<PriceCalculationStrategy> strategies) {
        this.strategies = new ArrayList<>(strategies);
    }
    
    public CombinedPriceCalculationStrategy(PriceCalculationStrategy... strategies) {
        this.strategies = Arrays.asList(strategies);
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public Money calculatePrice(Price price, LocalDate date) {
        if (strategies.isEmpty()) {
            return price.getBasePrice();
        }
        
        // We need to create a temporary price object for each step in the calculation
        // because each strategy expects a Price object
        Price currentPrice = price;
        Money result = price.getBasePrice();
        
        for (PriceCalculationStrategy strategy : strategies) {
            result = strategy.calculatePrice(currentPrice, date);
            
            // Create a new Price with the adjusted result for the next strategy
            final Money finalResult = result;
            currentPrice = new Price(
                    price.getId(),
                    price.getRateId(),
                    price.getDateRange(),
                    finalResult,
                    price.getCalculationStrategy(),
                    price.getLastModified()
            );
        }
        
        return result;
    }
    
    public List<PriceCalculationStrategy> getStrategies() {
        return new ArrayList<>(strategies);
    }
    
    public void addStrategy(PriceCalculationStrategy strategy) {
        this.strategies.add(strategy);
    }
} 