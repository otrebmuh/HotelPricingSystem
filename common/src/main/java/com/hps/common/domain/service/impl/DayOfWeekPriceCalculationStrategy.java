package com.hps.common.domain.service.impl;

import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Calculation strategy that adjusts the price based on the day of the week.
 * Weekends (Friday, Saturday) typically have higher rates.
 */
public class DayOfWeekPriceCalculationStrategy implements PriceCalculationStrategy {
    
    public static final String TYPE = "DAY_OF_WEEK";
    
    private static final Map<DayOfWeek, Double> DEFAULT_MULTIPLIERS = new ConcurrentHashMap<>();
    
    static {
        // Standard multipliers - weekends are more expensive
        DEFAULT_MULTIPLIERS.put(DayOfWeek.MONDAY, 1.0);
        DEFAULT_MULTIPLIERS.put(DayOfWeek.TUESDAY, 1.0);
        DEFAULT_MULTIPLIERS.put(DayOfWeek.WEDNESDAY, 1.0);
        DEFAULT_MULTIPLIERS.put(DayOfWeek.THURSDAY, 1.0);
        DEFAULT_MULTIPLIERS.put(DayOfWeek.FRIDAY, 1.25);    // 25% more on Fridays
        DEFAULT_MULTIPLIERS.put(DayOfWeek.SATURDAY, 1.25);  // 25% more on Saturdays
        DEFAULT_MULTIPLIERS.put(DayOfWeek.SUNDAY, 1.1);     // 10% more on Sundays
    }
    
    private final Map<DayOfWeek, Double> multipliers;
    
    public DayOfWeekPriceCalculationStrategy() {
        this.multipliers = DEFAULT_MULTIPLIERS;
    }
    
    public DayOfWeekPriceCalculationStrategy(Map<DayOfWeek, Double> multipliers) {
        this.multipliers = multipliers;
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public Money calculatePrice(Price price, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Double multiplier = multipliers.getOrDefault(dayOfWeek, 1.0);
        
        return price.getBasePrice().multiply(multiplier);
    }
    
    public Map<DayOfWeek, Double> getMultipliers() {
        return new ConcurrentHashMap<>(multipliers);
    }
} 