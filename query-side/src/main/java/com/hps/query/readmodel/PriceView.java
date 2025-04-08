package com.hps.query.readmodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;

/**
 * Read model entity for price queries.
 */
@Entity
@Table(name = "price_view", indexes = {
    @Index(name = "idx_price_view_hotel_date", columnList = "hotel_id, date"),
    @Index(name = "idx_price_view_room_type_date", columnList = "room_type_id, date")
})
public class PriceView {
    
    @Id
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "price_id", nullable = false)
    private UUID priceId;
    
    @Column(name = "rate_id", nullable = false)
    private UUID rateId;
    
    @Column(name = "room_type_id", nullable = false)
    private UUID roomTypeId;
    
    @Column(name = "hotel_id", nullable = false)
    private UUID hotelId;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "base_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseAmount;
    
    @Column(name = "calculated_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal calculatedAmount;
    
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    
    @Column(name = "calculation_strategy", nullable = false)
    private String calculationStrategy;
    
    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;
    
    // Default constructor required by JPA
    protected PriceView() {
    }
    
    public PriceView(UUID id, UUID priceId, UUID rateId, UUID roomTypeId, UUID hotelId,
                    LocalDate date, BigDecimal baseAmount, BigDecimal calculatedAmount,
                    String currencyCode, String calculationStrategy, LocalDateTime lastModified) {
        this.id = id;
        this.priceId = priceId;
        this.rateId = rateId;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.date = date;
        this.baseAmount = baseAmount;
        this.calculatedAmount = calculatedAmount;
        this.currencyCode = currencyCode;
        this.calculationStrategy = calculationStrategy;
        this.lastModified = lastModified;
    }
    
    // Getters and setters
    
    public UUID getId() {
        return id;
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
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
    
    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }
    
    public BigDecimal getCalculatedAmount() {
        return calculatedAmount;
    }
    
    public void setCalculatedAmount(BigDecimal calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
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