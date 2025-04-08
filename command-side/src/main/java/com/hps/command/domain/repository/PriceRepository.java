package com.hps.command.domain.repository;

import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Price;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Price entities.
 */
public interface PriceRepository {
    
    /**
     * Find a price by its ID.
     *
     * @param id The price ID
     * @return An Optional containing the price, or empty if not found
     */
    Optional<Price> findById(UUID id);
    
    /**
     * Find a price by rate ID and date range.
     *
     * @param rateId The rate ID
     * @param dateRange The date range
     * @return An Optional containing the price, or empty if not found
     */
    Optional<Price> findByRateIdAndDateRange(UUID rateId, DateRange dateRange);
    
    /**
     * Save a price.
     *
     * @param price The price to save
     * @return The saved price
     */
    Price save(Price price);
} 