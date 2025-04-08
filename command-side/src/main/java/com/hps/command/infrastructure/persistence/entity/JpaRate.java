package com.hps.command.infrastructure.persistence.entity;

import com.hps.common.domain.model.Rate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * JPA entity representing a Rate.
 */
@Entity
@Table(name = "rates")
public class JpaRate {
    
    @Id
    private UUID id;
    
    @Column(name = "room_type_id", nullable = false)
    private UUID roomTypeId;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    /**
     * Default constructor required by JPA.
     */
    protected JpaRate() {
    }
    
    /**
     * Create a JPA Rate from a domain Rate.
     *
     * @param rate The domain Rate entity
     * @return A new JPA Rate entity
     */
    public static JpaRate fromDomain(Rate rate) {
        JpaRate jpaRate = new JpaRate();
        jpaRate.id = rate.getId();
        jpaRate.roomTypeId = rate.getRoomTypeId();
        jpaRate.name = rate.getName();
        jpaRate.description = rate.getDescription();
        jpaRate.isActive = rate.isActive();
        return jpaRate;
    }
    
    /**
     * Convert this JPA Rate to a domain Rate.
     *
     * @return A domain Rate entity
     */
    public Rate toDomain() {
        return new Rate(
                id,
                roomTypeId,
                name,
                description,
                isActive
        );
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
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
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
} 