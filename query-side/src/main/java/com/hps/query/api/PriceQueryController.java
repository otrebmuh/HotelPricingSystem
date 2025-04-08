package com.hps.query.api;

import com.hps.query.api.dto.PriceDTO;
import com.hps.query.application.PriceQueryService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for price queries.
 */
@RestController
@RequestMapping("/api/v1/prices")
public class PriceQueryController {
    
    private final PriceQueryService priceQueryService;
    
    public PriceQueryController(PriceQueryService priceQueryService) {
        this.priceQueryService = priceQueryService;
    }
    
    /**
     * Get prices for a hotel on a specific date.
     *
     * @param hotelId The hotel ID
     * @param date The date (ISO format: yyyy-MM-dd)
     * @return A list of price DTOs
     */
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<PriceDTO>> getPricesForHotel(
            @PathVariable UUID hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<PriceDTO> prices = priceQueryService.getPricesForHotelAndDate(hotelId, date);
        return ResponseEntity.ok(prices);
    }
    
    /**
     * Get prices for a hotel for a date range.
     *
     * @param hotelId The hotel ID
     * @param startDate The start date (ISO format: yyyy-MM-dd)
     * @param endDate The end date (ISO format: yyyy-MM-dd)
     * @return A list of price DTOs
     */
    @GetMapping("/hotels/{hotelId}/range")
    public ResponseEntity<List<PriceDTO>> getPricesForHotelAndDateRange(
            @PathVariable UUID hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PriceDTO> prices = priceQueryService.getPricesForHotelAndDateRange(hotelId, startDate, endDate);
        return ResponseEntity.ok(prices);
    }
    
    /**
     * Get prices for a room type on a specific date.
     *
     * @param roomTypeId The room type ID
     * @param date The date (ISO format: yyyy-MM-dd)
     * @return A list of price DTOs
     */
    @GetMapping("/room-types/{roomTypeId}")
    public ResponseEntity<List<PriceDTO>> getPricesForRoomType(
            @PathVariable UUID roomTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<PriceDTO> prices = priceQueryService.getPricesForRoomTypeAndDate(roomTypeId, date);
        return ResponseEntity.ok(prices);
    }
} 