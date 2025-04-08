package com.hps.query.infrastructure.repository;

import com.hps.query.readmodel.PriceView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository for price view queries.
 */
@Repository
public interface PriceViewRepository extends JpaRepository<PriceView, UUID> {
    
    /**
     * Find all prices for a specific hotel on a specific date.
     *
     * @param hotelId The hotel ID
     * @param date The date
     * @return A list of price views
     */
    List<PriceView> findByHotelIdAndDate(UUID hotelId, LocalDate date);
    
    /**
     * Find all prices for a specific room type on a specific date.
     *
     * @param roomTypeId The room type ID
     * @param date The date
     * @return A list of price views
     */
    List<PriceView> findByRoomTypeIdAndDate(UUID roomTypeId, LocalDate date);
    
    /**
     * Find all prices for a specific hotel between two dates.
     *
     * @param hotelId The hotel ID
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @return A list of price views
     */
    @Query("SELECT p FROM PriceView p WHERE p.hotelId = :hotelId AND p.date BETWEEN :startDate AND :endDate")
    List<PriceView> findByHotelIdAndDateRange(
            @Param("hotelId") UUID hotelId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
            
    /**
     * Find all prices for a specific rate.
     *
     * @param rateId The rate ID
     * @return A list of price views
     */
    List<PriceView> findByRateId(UUID rateId);
    
    /**
     * Delete all prices for a specific price ID.
     *
     * @param priceId The price ID
     */
    void deleteByPriceId(UUID priceId);
} 