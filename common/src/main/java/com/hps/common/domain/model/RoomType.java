package com.hps.common.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * RoomType entity representing a type of room in a hotel.
 */
public class RoomType {
    private final UUID id;
    private final UUID hotelId;
    private String name;
    private String description;
    private int maxOccupancy;
    private int totalRooms;
    private final Set<Rate> rates = new HashSet<>();
    
    public RoomType(UUID id, UUID hotelId, String name, String description, int maxOccupancy, int totalRooms) {
        this.id = id;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.maxOccupancy = maxOccupancy;
        this.totalRooms = totalRooms;
    }
    
    public static RoomType create(UUID hotelId, String name, String description, int maxOccupancy, int totalRooms) {
        return new RoomType(UUID.randomUUID(), hotelId, name, description, maxOccupancy, totalRooms);
    }
    
    public UUID getId() {
        return id;
    }
    
    public UUID getHotelId() {
        return hotelId;
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
    
    public Set<Rate> getRates() {
        return Collections.unmodifiableSet(rates);
    }
    
    public void addRate(Rate rate) {
        rates.add(rate);
    }
    
    public void removeRate(Rate rate) {
        rates.remove(rate);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
        return id.equals(roomType.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", maxOccupancy=" + maxOccupancy +
                ", totalRooms=" + totalRooms +
                '}';
    }
} 