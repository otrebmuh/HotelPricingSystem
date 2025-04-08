package com.hps.command.application;

import com.hps.command.domain.repository.PriceRepository;
import com.hps.command.domain.repository.RateRepository;
import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.model.Rate;
import com.hps.common.domain.model.RoomType;
import com.hps.common.domain.service.PriceCalculationStrategyFactory;
import com.hps.common.dto.UpdatePriceCommand;
import com.hps.common.events.EventPublisher;
import com.hps.common.events.PriceChangedEvent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.UUID;

/**
 * Application service for handling price commands.
 */
@Service
public class PriceCommandService {
    
    private final RateRepository rateRepository;
    private final PriceRepository priceRepository;
    private final PriceCalculationStrategyFactory strategyFactory;
    private final EventPublisher eventPublisher;
    
    public PriceCommandService(
            RateRepository rateRepository,
            PriceRepository priceRepository,
            PriceCalculationStrategyFactory strategyFactory,
            EventPublisher eventPublisher) {
        this.rateRepository = rateRepository;
        this.priceRepository = priceRepository;
        this.strategyFactory = strategyFactory;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Handle a command to update a price.
     *
     * @param command The command to update a price
     * @return The ID of the created or updated price
     */
    @Transactional
    public UUID handleUpdatePrice(UpdatePriceCommand command) {
        // Validate calculation strategy
        validateCalculationStrategy(command.getCalculationStrategy());
        
        // Find the rate
        Rate rate = rateRepository.findById(command.getRateId())
                .orElseThrow(() -> new IllegalArgumentException("Rate not found with ID: " + command.getRateId()));
        
        // Create the domain objects
        DateRange dateRange = DateRange.of(command.getStartDate(), command.getEndDate());
        Money basePrice = Money.of(command.getAmount(), Currency.getInstance(command.getCurrencyCode()));
        
        // Create or update the price
        Price price = priceRepository.findByRateIdAndDateRange(rate.getId(), dateRange)
                .orElse(Price.create(rate.getId(), dateRange, basePrice, command.getCalculationStrategy()));
        
        // If the price already exists, update its properties
        if (price.getId() != null) {
            price.setBasePrice(basePrice);
            price.setCalculationStrategy(command.getCalculationStrategy());
        }
        
        // Add the price to the rate
        rate.addPrice(price);
        
        // Save the price
        Price savedPrice = priceRepository.save(price);
        
        // Publish the event
        publishPriceChangedEvent(savedPrice, rate.getRoomTypeId());
        
        return savedPrice.getId();
    }
    
    private void validateCalculationStrategy(String strategyType) {
        if (!strategyFactory.hasStrategy(strategyType)) {
            throw new IllegalArgumentException("Unsupported calculation strategy: " + strategyType);
        }
    }
    
    private void publishPriceChangedEvent(Price price, UUID roomTypeId) {
        // Get the hotel ID through the room type
        RoomType roomType = rateRepository.findRoomTypeById(roomTypeId)
                .orElseThrow(() -> new IllegalStateException("Room type not found with ID: " + roomTypeId));
        
        PriceChangedEvent event = PriceChangedEvent.create(
                price.getId(),
                price.getRateId(),
                roomTypeId,
                roomType.getHotelId(),
                price.getDateRange().getStartDate(),
                price.getDateRange().getEndDate(),
                price.getBasePrice().getAmount(),
                price.getBasePrice().getCurrency().getCurrencyCode(),
                price.getCalculationStrategy()
        );
        
        eventPublisher.publish(event);
    }
} 