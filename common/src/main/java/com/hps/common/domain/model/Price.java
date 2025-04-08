package com.hps.common.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Price entity representing a price for a rate during a specific date range.
 */
public class Price {
    private final UUID id;
    private final UUID rateId;
    private final DateRange dateRange;
    private Money basePrice;
    private String calculationStrategy;
    private LocalDateTime lastModified;
    
    public Price(UUID id, UUID rateId, DateRange dateRange, Money basePrice, String calculationStrategy, LocalDateTime lastModified) {
        this.id = id;
        this.rateId = rateId;
        this.dateRange = dateRange;
        this.basePrice = basePrice;
        this.calculationStrategy = calculationStrategy;
        this.lastModified = lastModified;
    }
    
    public static Price create(UUID rateId, DateRange dateRange, Money basePrice, String calculationStrategy) {
        return new Price(UUID.randomUUID(), rateId, dateRange, basePrice, calculationStrategy, LocalDateTime.now());
    }
    
    public UUID getId() {
        return id;
    }
    
    public UUID getRateId() {
        return rateId;
    }
    
    public DateRange getDateRange() {
        return dateRange;
    }
    
    public Money getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(Money basePrice) {
        this.basePrice = basePrice;
        this.lastModified = LocalDateTime.now();
    }
    
    public String getCalculationStrategy() {
        return calculationStrategy;
    }
    
    public void setCalculationStrategy(String calculationStrategy) {
        this.calculationStrategy = calculationStrategy;
        this.lastModified = LocalDateTime.now();
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return id.equals(price.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", rateId=" + rateId +
                ", dateRange=" + dateRange +
                ", basePrice=" + basePrice +
                ", calculationStrategy='" + calculationStrategy + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
} 