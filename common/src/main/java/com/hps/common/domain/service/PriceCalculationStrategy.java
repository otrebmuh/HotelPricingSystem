package com.hps.common.domain.service;

import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;

import java.time.LocalDate;

/**
 * Strategy interface for calculating the final price based on different algorithms.
 */
public interface PriceCalculationStrategy {
    
    /**
     * Strategy type identifier
     */
    String getType();
    
    /**
     * Calculate the final price for a specific date based on the base price.
     *
     * @param price The price entity containing the base price and strategy configuration
     * @param date The specific date for which to calculate the price
     * @return The calculated final price
     */
    Money calculatePrice(Price price, LocalDate date);
} 