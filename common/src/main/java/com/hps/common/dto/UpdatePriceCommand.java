package com.hps.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Command DTO for price updates.
 */
public class UpdatePriceCommand {
    
    @NotNull(message = "Rate ID is required")
    private UUID rateId;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "Price amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency code is required")
    private String currencyCode;
    
    @NotBlank(message = "Calculation strategy is required")
    private String calculationStrategy;
    
    // Default constructor for serialization
    public UpdatePriceCommand() {
    }
    
    public UpdatePriceCommand(UUID rateId, LocalDate startDate, LocalDate endDate, 
                              BigDecimal amount, String currencyCode, 
                              String calculationStrategy) {
        this.rateId = rateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.calculationStrategy = calculationStrategy;
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
    
    @Override
    public String toString() {
        return "UpdatePriceCommand{" +
                "rateId=" + rateId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", calculationStrategy='" + calculationStrategy + '\'' +
                '}';
    }
} 