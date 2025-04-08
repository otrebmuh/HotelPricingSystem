package com.hps.common.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event that represents a price change in the system.
 * Used to propagate price changes between command and query side.
 */
public class PriceChangedEvent {
    
    private UUID id;
    private UUID priceId;
    private UUID rateId;
    private UUID roomTypeId;
    private UUID hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;
    private String currencyCode;
    private String calculationStrategy;
    private LocalDateTime modifiedAt;
    
    // Default constructor for serialization
    public PriceChangedEvent() {
    }
    
    public PriceChangedEvent(UUID id, UUID priceId, UUID rateId, UUID roomTypeId, UUID hotelId,
                           LocalDate startDate, LocalDate endDate, BigDecimal amount,
                           String currencyCode, String calculationStrategy, LocalDateTime modifiedAt) {
        this.id = id;
        this.priceId = priceId;
        this.rateId = rateId;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.calculationStrategy = calculationStrategy;
        this.modifiedAt = modifiedAt;
    }
    
    public static PriceChangedEvent create(UUID priceId, UUID rateId, UUID roomTypeId, UUID hotelId,
                                      LocalDate startDate, LocalDate endDate, BigDecimal amount,
                                      String currencyCode, String calculationStrategy) {
        return new PriceChangedEvent(
                UUID.randomUUID(),
                priceId,
                rateId,
                roomTypeId,
                hotelId,
                startDate,
                endDate,
                amount,
                currencyCode,
                calculationStrategy,
                LocalDateTime.now()
        );
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getPriceId() {
        return priceId;
    }
    
    public void setPriceId(UUID priceId) {
        this.priceId = priceId;
    }
    
    public UUID getRateId() {
        return rateId;
    }
    
    public void setRateId(UUID rateId) {
        this.rateId = rateId;
    }
    
    public UUID getRoomTypeId() {
        return roomTypeId;
    }
    
    public void setRoomTypeId(UUID roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    
    public UUID getHotelId() {
        return hotelId;
    }
    
    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
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
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    @Override
    public String toString() {
        return "PriceChangedEvent{" +
                "id=" + id +
                ", priceId=" + priceId +
                ", rateId=" + rateId +
                ", roomTypeId=" + roomTypeId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", calculationStrategy='" + calculationStrategy + '\'' +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}