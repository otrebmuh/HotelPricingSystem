package com.hps.command.infrastructure.persistence.entity;

import com.hps.common.domain.model.RoomType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * JPA entity representing a RoomType.
 */
@Entity
@Table(name = "room_types")
public class JpaRoomType {
    
    @Id
    private UUID id;
    
    @Column(name = "hotel_id", nullable = false)
    private UUID hotelId;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(name = "max_occupancy", nullable = false)
    private int maxOccupancy;
    
    @Column(name = "total_rooms", nullable = false)
    private int totalRooms;
    
    /**
     * Default constructor required by JPA.
     */
    protected JpaRoomType() {
    }
    
    /**
     * Create a JPA RoomType from a domain RoomType.
     *
     * @param roomType The domain RoomType entity
     * @return A new JPA RoomType entity
     */
    public static JpaRoomType fromDomain(RoomType roomType) {
        JpaRoomType jpaRoomType = new JpaRoomType();
        jpaRoomType.id = roomType.getId();
        jpaRoomType.hotelId = roomType.getHotelId();
        jpaRoomType.name = roomType.getName();
        jpaRoomType.description = roomType.getDescription();
        jpaRoomType.maxOccupancy = roomType.getMaxOccupancy();
        jpaRoomType.totalRooms = roomType.getTotalRooms();
        return jpaRoomType;
    }
    
    /**
     * Convert this JPA RoomType to a domain RoomType.
     *
     * @return A domain RoomType entity
     */
    public RoomType toDomain() {
        return new RoomType(
                id,
                hotelId,
                name,
                description,
                maxOccupancy,
                totalRooms
        );
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
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
    
    public int getMaxOccupancy() {
        return maxOccupancy;
    }
    
    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }
    
    public int getTotalRooms() {
        return totalRooms;
    }
    
    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }
} 