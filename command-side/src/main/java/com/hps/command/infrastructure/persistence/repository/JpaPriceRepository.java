package com.hps.command.infrastructure.persistence.repository;

import com.hps.command.infrastructure.persistence.entity.JpaPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for Price entities.
 */
@Repository
public interface JpaPriceRepository extends JpaRepository<JpaPrice, UUID> {
    
    /**
     * Find a price by rate ID and date range.
     *
     * @param rateId The rate ID
     * @param startDate The start date of the range
     * @param endDate The end date of the range
     * @return An Optional containing the price, or empty if not found
     */
    @Query("SELECT p FROM JpaPrice p WHERE p.rateId = :rateId AND p.startDate = :startDate AND p.endDate = :endDate")
    Optional<JpaPrice> findByRateIdAndDateRange(
            @Param("rateId") UUID rateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
} 