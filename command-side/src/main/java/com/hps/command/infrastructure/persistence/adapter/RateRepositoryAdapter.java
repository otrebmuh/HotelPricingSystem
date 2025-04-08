package com.hps.command.infrastructure.persistence.adapter;

import com.hps.command.domain.repository.RateRepository;
import com.hps.command.infrastructure.persistence.entity.JpaRate;
import com.hps.command.infrastructure.persistence.entity.JpaRoomType;
import com.hps.command.infrastructure.persistence.repository.JpaRateRepository;
import com.hps.common.domain.model.Rate;
import com.hps.common.domain.model.RoomType;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter that implements the RateRepository interface using JPA.
 */
@Component
public class RateRepositoryAdapter implements RateRepository {
    
    private final JpaRateRepository jpaRateRepository;
    
    public RateRepositoryAdapter(JpaRateRepository jpaRateRepository) {
        this.jpaRateRepository = jpaRateRepository;
    }
    
    @Override
    public Optional<Rate> findById(UUID id) {
        return jpaRateRepository.findById(id)
                .map(JpaRate::toDomain);
    }
    
    @Override
    public Optional<RoomType> findRoomTypeById(UUID id) {
        return jpaRateRepository.findRoomTypeById(id)
                .map(JpaRoomType::toDomain);
    }
    
    @Override
    public Rate save(Rate rate) {
        JpaRate jpaRate = JpaRate.fromDomain(rate);
        return jpaRateRepository.save(jpaRate).toDomain();
    }
} 