package com.hps.common.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Rate entity representing a rate plan for a room type.
 */
public class Rate {
    private final UUID id;
    private final UUID roomTypeId;
    private String name;
    private String description;
    private boolean isActive;
    private final Set<Price> prices = new HashSet<>();
    
    public Rate(UUID id, UUID roomTypeId, String name, String description, boolean isActive) {
        this.id = id;
        this.roomTypeId = roomTypeId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }
    
    public static Rate create(UUID roomTypeId, String name, String description, boolean isActive) {
        return new Rate(UUID.randomUUID(), roomTypeId, name, description, isActive);
    }
    
    public UUID getId() {
        return id;
    }
    
    public UUID getRoomTypeId() {
        return roomTypeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Set<Price> getPrices() {
        return Collections.unmodifiableSet(prices);
    }
    
    public void addPrice(Price price) {
        // Remove any existing prices that overlap with the new price's date range
        prices.removeIf(p -> p.getDateRange().overlaps(price.getDateRange()));
        prices.add(price);
    }
    
    public void removePrice(Price price) {
        prices.remove(price);
    }
    
    public Price getPriceForDate(java.time.LocalDate date) {
        return prices.stream()
                .filter(price -> price.getDateRange().contains(date))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return id.equals(rate.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", roomTypeId=" + roomTypeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
} 