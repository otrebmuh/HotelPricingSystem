package com.hps.common.domain.service.impl;

import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationStrategy;

import java.time.LocalDate;

/**
 * Standard strategy that simply returns the base price without any adjustments.
 * This is the default fallback strategy when no specific calculations are needed.
 */
public class StandardPriceCalculationStrategy implements PriceCalculationStrategy {
    
    public static final String TYPE = "STANDARD";
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public Money calculatePrice(Price price, LocalDate date) {
        // Simply return the base price without any modifications
        return price.getBasePrice();
    }
} 