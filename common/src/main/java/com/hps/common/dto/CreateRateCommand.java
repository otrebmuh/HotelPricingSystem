package com.hps.common.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Command DTO for creating new rates.
 */
public class CreateRateCommand {
    
    @NotNull(message = "Room type ID is required")
    private UUID roomTypeId;
    
    @NotBlank(message = "Rate name is required")
    private String name;
    
    private String description;
    
    private boolean active = true;
    
    // Default constructor for serialization
    public CreateRateCommand() {
    }
    
    public CreateRateCommand(UUID roomTypeId, String name, String description, boolean active) {
        this.roomTypeId = roomTypeId;
        this.name = name;
        this.description = description;
        this.active = active;
    }
    
    public UUID getRoomTypeId() {
        return roomTypeId;
    }
    
    public void setRoomTypeId(UUID roomTypeId) {
        this.roomTypeId = roomTypeId;
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
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return "CreateRateCommand{" +
                "roomTypeId=" + roomTypeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
} 