package com.hps.command.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hps.common.domain.model.DateRange;
import com.hps.common.domain.model.Money;
import com.hps.common.domain.model.Price;

/**
 * JPA entity for Price.
 */
@Entity
@Table(name = "prices")
public class JpaPrice {
    
    @Id
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "rate_id", nullable = false)
    private UUID rateId;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;
    
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    
    @Column(name = "calculation_strategy", nullable = false)
    private String calculationStrategy;
    
    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;
    
    // Default constructor required by JPA
    protected JpaPrice() {
    }
    
    public JpaPrice(UUID id, UUID rateId, LocalDate startDate, LocalDate endDate,
                   BigDecimal basePrice, String currencyCode, String calculationStrategy,
                   LocalDateTime lastModified) {
        this.id = id;
        this.rateId = rateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.basePrice = basePrice;
        this.currencyCode = currencyCode;
        this.calculationStrategy = calculationStrategy;
        this.lastModified = lastModified;
    }
    
    /**
     * Convert from domain model to JPA entity.
     */
    public static JpaPrice fromDomain(Price price) {
        return new JpaPrice(
                price.getId(),
                price.getRateId(),
                price.getDateRange().getStartDate(),
                price.getDateRange().getEndDate(),
                price.getBasePrice().getAmount(),
                price.getBasePrice().getCurrency().getCurrencyCode(),
                price.getCalculationStrategy(),
                price.getLastModified()
        );
    }
    
    /**
     * Convert from JPA entity to domain model.
     */
    public Price toDomain() {
        return new Price(
                id,
                rateId,
                DateRange.of(startDate, endDate),
                Money.of(basePrice, java.util.Currency.getInstance(currencyCode)),
                calculationStrategy,
                lastModified
        );
    }
    
    // Getters and setters
    
    public UUID getId() {
        return id;
    }
    
    public UUID getRateId() {
        return rateId;
    }
    
    public void setRateId(UUID rateId) {
        this.rateId = rateId;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    public String getCalculationStrategy() {
        return calculationStrategy;
    }
    
    public void setCalculationStrategy(String calculationStrategy) {
        this.calculationStrategy = calculationStrategy;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
} 