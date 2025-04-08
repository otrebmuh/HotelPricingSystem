package com.hps.command.domain.repository;

import com.hps.common.domain.model.Rate;
import com.hps.common.domain.model.RoomType;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Rate entities.
 */
public interface RateRepository {
    
    /**
     * Find a rate by its ID.
     *
     * @param id The rate ID
     * @return An Optional containing the rate, or empty if not found
     */
    Optional<Rate> findById(UUID id);
    
    /**
     * Find a room type by its ID.
     *
     * @param id The room type ID
     * @return An Optional containing the room type, or empty if not found
     */
    Optional<RoomType> findRoomTypeById(UUID id);
    
    /**
     * Save a rate.
     *
     * @param rate The rate to save
     * @return The saved rate
     */
    Rate save(Rate rate);
} 