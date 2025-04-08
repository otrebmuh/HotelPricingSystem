package com.hps.query.application;

import com.hps.query.api.dto.PriceDTO;
import com.hps.query.infrastructure.repository.PriceViewRepository;
import com.hps.query.readmodel.PriceView;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for handling price queries.
 */
@Service
public class PriceQueryService {
    
    private final PriceViewRepository priceViewRepository;
    
    public PriceQueryService(PriceViewRepository priceViewRepository) {
        this.priceViewRepository = priceViewRepository;
    }
    
    /**
     * Get prices for a hotel on a specific date.
     *
     * @param hotelId The hotel ID
     * @param date The date
     * @return A list of price DTOs
     */
    @Cacheable(value = "hotelPrices", key = "#hotelId + '_' + #date")
    public List<PriceDTO> getPricesForHotelAndDate(UUID hotelId, LocalDate date) {
        List<PriceView> priceViews = priceViewRepository.findByHotelIdAndDate(hotelId, date);
        return convertToDTOs(priceViews);
    }
    
    /**
     * Get prices for a hotel for a date range.
     *
     * @param hotelId The hotel ID
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of price DTOs
     */
    public List<PriceDTO> getPricesForHotelAndDateRange(UUID hotelId, LocalDate startDate, LocalDate endDate) {
        List<PriceView> priceViews = priceViewRepository.findByHotelIdAndDateRange(hotelId, startDate, endDate);
        return convertToDTOs(priceViews);
    }
    
    /**
     * Get prices for a room type on a specific date.
     *
     * @param roomTypeId The room type ID
     * @param date The date
     * @return A list of price DTOs
     */
    @Cacheable(value = "roomTypePrices", key = "#roomTypeId + '_' + #date")
    public List<PriceDTO> getPricesForRoomTypeAndDate(UUID roomTypeId, LocalDate date) {
        List<PriceView> priceViews = priceViewRepository.findByRoomTypeIdAndDate(roomTypeId, date);
        return convertToDTOs(priceViews);
    }
    
    private List<PriceDTO> convertToDTOs(List<PriceView> priceViews) {
        return priceViews.stream()
                .map(view -> new PriceDTO(
                        view.getId(),
                        view.getRateId(),
                        view.getRoomTypeId(),
                        view.getHotelId(),
                        view.getDate(),
                        view.getCalculatedAmount(),
                        view.getCurrencyCode()
                ))
                .collect(Collectors.toList());
    }
} 