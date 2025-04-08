package com.hps.common.domain.service.impl;

import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationStrategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculation strategy that adjusts the price based on seasonal factors.
 * Different periods of the year can have different multipliers for price adjustment.
 */
public class SeasonalPriceCalculationStrategy implements PriceCalculationStrategy {
    
    public static final String TYPE = "SEASONAL";
    
    private final Map<DateRange, Double> seasonalMultipliers;
    
    public SeasonalPriceCalculationStrategy() {
        this.seasonalMultipliers = new HashMap<>();
    }
    
    public SeasonalPriceCalculationStrategy(Map<DateRange, Double> seasonalMultipliers) {
        this.seasonalMultipliers = new HashMap<>(seasonalMultipliers);
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public Money calculatePrice(Price price, LocalDate date) {
        Double multiplier = getMultiplierForDate(date);
        return price.getBasePrice().multiply(multiplier);
    }
    
    private Double getMultiplierForDate(LocalDate date) {
        return seasonalMultipliers.entrySet().stream()
                .filter(entry -> entry.getKey().contains(date))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(1.0); // Default multiplier if no seasonal rule matches
    }
    
    public void addSeasonalMultiplier(DateRange dateRange, Double multiplier) {
        seasonalMultipliers.put(dateRange, multiplier);
    }
    
    public void removeSeasonalMultiplier(DateRange dateRange) {
        seasonalMultipliers.remove(dateRange);
    }
    
    public Map<DateRange, Double> getSeasonalMultipliers() {
        return new HashMap<>(seasonalMultipliers);
    }
} 