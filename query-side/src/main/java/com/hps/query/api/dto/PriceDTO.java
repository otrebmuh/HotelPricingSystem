package com.hps.query.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for price query results.
 */
public class PriceDTO {
    
    private UUID id;
    private UUID rateId;
    private UUID roomTypeId;
    private UUID hotelId;
    private LocalDate date;
    private BigDecimal amount;
    private String currencyCode;
    
    // Default constructor for serialization
    public PriceDTO() {
    }
    
    public PriceDTO(UUID id, UUID rateId, UUID roomTypeId, UUID hotelId, 
                   LocalDate date, BigDecimal amount, String currencyCode) {
        this.id = id;
        this.rateId = rateId;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
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
} 