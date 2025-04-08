package com.hps.common.domain.service.impl;

import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationStrategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculation strategy that adjusts the price based on demand for specific dates.
 * Higher demand dates have higher multipliers.
 */
public class DemandBasedPriceCalculationStrategy implements PriceCalculationStrategy {
    
    public static final String TYPE = "DEMAND_BASED";
    
    // Different demand levels and their corresponding multipliers
    public enum DemandLevel {
        LOW(0.9),      // 10% discount
        NORMAL(1.0),   // standard price
        HIGH(1.15),    // 15% premium
        VERY_HIGH(1.3); // 30% premium
        
        private final double multiplier;
        
        DemandLevel(double multiplier) {
            this.multiplier = multiplier;
        }
        
        public double getMultiplier() {
            return multiplier;
        }
    }
    
    private final Map<LocalDate, DemandLevel> demandLevels;
    
    public DemandBasedPriceCalculationStrategy() {
        this.demandLevels = new HashMap<>();
    }
    
    public DemandBasedPriceCalculationStrategy(Map<LocalDate, DemandLevel> demandLevels) {
        this.demandLevels = new HashMap<>(demandLevels);
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public Money calculatePrice(Price price, LocalDate date) {
        DemandLevel demandLevel = demandLevels.getOrDefault(date, DemandLevel.NORMAL);
        return price.getBasePrice().multiply(demandLevel.getMultiplier());
    }
    
    public void setDemandLevel(LocalDate date, DemandLevel level) {
        demandLevels.put(date, level);
    }
    
    public void removeDemandLevel(LocalDate date) {
        demandLevels.remove(date);
    }
    
    public Map<LocalDate, DemandLevel> getDemandLevels() {
        return new HashMap<>(demandLevels);
    }
} 