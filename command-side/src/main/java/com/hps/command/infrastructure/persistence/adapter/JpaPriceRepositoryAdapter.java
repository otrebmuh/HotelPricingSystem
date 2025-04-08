package com.hps.command.infrastructure.persistence.adapter;

import com.hps.command.domain.repository.PriceRepository;
import com.hps.command.infrastructure.persistence.entity.JpaPrice;
import com.hps.command.infrastructure.persistence.repository.JpaPriceRepository;
import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Price;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA adapter for the Price repository.
 */
@Component
public class JpaPriceRepositoryAdapter implements PriceRepository {
    
    private final JpaPriceRepository jpaPriceRepository;
    
    public JpaPriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }
    
    @Override
    public Optional<Price> findById(UUID id) {
        return jpaPriceRepository.findById(id)
                .map(JpaPrice::toDomain);
    }
    
    @Override
    public Optional<Price> findByRateIdAndDateRange(UUID rateId, DateRange dateRange) {
        return jpaPriceRepository.findByRateIdAndDateRange(
                rateId,
                dateRange.getStartDate(),
                dateRange.getEndDate()
        ).map(JpaPrice::toDomain);
    }
    
    @Override
    public Price save(Price price) {
        JpaPrice jpaPrice = JpaPrice.fromDomain(price);
        JpaPrice savedJpaPrice = jpaPriceRepository.save(jpaPrice);
        return savedJpaPrice.toDomain();
    }
} 