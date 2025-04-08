package com.hps.common.domain.service;

import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain service responsible for calculating prices using the appropriate strategy.
 */
public class PriceCalculationService {
    
    private final PriceCalculationStrategyFactory strategyFactory;
    
    public PriceCalculationService(PriceCalculationStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }
    
    /**
     * Calculate the price for a specific date using the strategy defined in the price entity.
     *
     * @param price The price entity containing the base price and strategy information
     * @param date The date for which to calculate the price
     * @return The calculated price
     */
    public Money calculatePrice(Price price, LocalDate date) {
        if (!price.getDateRange().contains(date)) {
            throw new IllegalArgumentException("Date " + date + " is outside the price's date range: " + price.getDateRange());
        }
        
        PriceCalculationStrategy strategy = strategyFactory.getStrategy(price.getCalculationStrategy());
        return strategy.calculatePrice(price, date);
    }
    
    /**
     * Calculate prices for a range of dates using the strategy defined in the price entity.
     *
     * @param price The price entity containing the base price and strategy information
     * @param dateRange The date range for which to calculate prices
     * @return A map of dates to calculated prices
     */
    public Map<LocalDate, Money> calculatePrices(Price price, DateRange dateRange) {
        DateRange intersection = price.getDateRange().intersection(dateRange);
        Map<LocalDate, Money> result = new HashMap<>();
        
        for (LocalDate date : intersection) {
            Money calculatedPrice = calculatePrice(price, date);
            result.put(date, calculatedPrice);
        }
        
        return result;
    }
} 