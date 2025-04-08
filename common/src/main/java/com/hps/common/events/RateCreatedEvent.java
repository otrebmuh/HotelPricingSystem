package com.hps.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event that represents a new rate being created in the system.
 * Used to propagate rate creation between command and query side.
 */
public class RateCreatedEvent {
    
    private UUID id;
    private UUID rateId;
    private UUID roomTypeId;
    private UUID hotelId;
    private String name;
    private String description;
    private boolean isActive;
    private LocalDateTime createdAt;
    
    // Default constructor for serialization
    public RateCreatedEvent() {
    }
    
    public RateCreatedEvent(UUID id, UUID rateId, UUID roomTypeId, UUID hotelId, 
                        String name, String description, boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.rateId = rateId;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    public static RateCreatedEvent create(UUID rateId, UUID roomTypeId, UUID hotelId, 
                                     String name, String description, boolean isActive) {
        return new RateCreatedEvent(
                UUID.randomUUID(),
                rateId,
                roomTypeId,
                hotelId,
                name,
                description,
                isActive,
                LocalDateTime.now()
        );
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "RateCreatedEvent{" +
                "id=" + id +
                ", rateId=" + rateId +
                ", roomTypeId=" + roomTypeId +
                ", hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
} 