package com.hps.query.application.eventhandler;

import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;
import com.hps.common.domain.service.PriceCalculationService;
import com.hps.common.domain.service.PriceCalculationStrategyFactory;
import com.hps.common.events.PriceChangedEvent;
import com.hps.query.infrastructure.repository.PriceViewRepository;
import com.hps.query.readmodel.PriceView;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;

/**
 * Event handler for price changed events.
 */
@Component
public class PriceChangedEventHandler {
    
    private final PriceViewRepository priceViewRepository;
    private final PriceCalculationService priceCalculationService;
    private final CacheManager cacheManager;
    
    public PriceChangedEventHandler(
            PriceViewRepository priceViewRepository,
            PriceCalculationStrategyFactory strategyFactory,
            CacheManager cacheManager) {
        this.priceViewRepository = priceViewRepository;
        this.priceCalculationService = new PriceCalculationService(strategyFactory);
        this.cacheManager = cacheManager;
    }
    
    /**
     * Handle price changed events by updating the price view read model.
     */
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    @Transactional
    public void handlePriceChangedEvent(PriceChangedEvent event) {
        // First, remove any existing prices for this price ID
        priceViewRepository.deleteByPriceId(event.getPriceId());
        
        // Create domain objects for price calculation
        DateRange dateRange = DateRange.of(event.getStartDate(), event.getEndDate());
        Money basePrice = Money.of(event.getAmount(), Currency.getInstance(event.getCurrencyCode()));
        
        // Create a price object for calculation
        Price price = new Price(
                event.getPriceId(),
                event.getRateId(),
                dateRange,
                basePrice,
                event.getCalculationStrategy(),
                event.getModifiedAt()
        );
        
        // Calculate prices for each date in the range
        Map<LocalDate, Money> calculatedPrices = priceCalculationService.calculatePrices(price, dateRange);
        
        // Create a price view entity for each date
        for (Map.Entry<LocalDate, Money> entry : calculatedPrices.entrySet()) {
            LocalDate date = entry.getKey();
            Money calculatedPrice = entry.getValue();
            
            PriceView priceView = new PriceView(
                    UUID.randomUUID(),    // Each date gets a unique view ID
                    event.getPriceId(),
                    event.getRateId(),
                    event.getRoomTypeId(),
                    event.getHotelId(),
                    date,
                    basePrice.getAmount(),
                    calculatedPrice.getAmount(),
                    calculatedPrice.getCurrency().getCurrencyCode(),
                    event.getCalculationStrategy(),
                    LocalDateTime.now()
            );
            
            priceViewRepository.save(priceView);
        }
        
        // Evict relevant caches
        evictCaches(event.getHotelId(), event.getRoomTypeId());
    }
    
    private void evictCaches(UUID hotelId, UUID roomTypeId) {
        // Clear caches related to this hotel and room type
        cacheManager.getCache("hotelPrices").evict(hotelId);
        cacheManager.getCache("roomTypePrices").evict(roomTypeId);
    }
} 