package com.hps.common.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Hotel entity representing a hotel in the system.
 */
public class Hotel {
    private final UUID id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int starRating;
    private final Set<RoomType> roomTypes = new HashSet<>();
    
    public Hotel(UUID id, String name, String address, String city, String country, int starRating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.starRating = starRating;
    }
    
    public static Hotel create(String name, String address, String city, String country, int starRating) {
        return new Hotel(UUID.randomUUID(), name, address, city, country, starRating);
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public int getStarRating() {
        return starRating;
    }
    
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
    
    public Set<RoomType> getRoomTypes() {
        return Collections.unmodifiableSet(roomTypes);
    }
    
    public void addRoomType(RoomType roomType) {
        roomTypes.add(roomType);
    }
    
    public void removeRoomType(RoomType roomType) {
        roomTypes.remove(roomType);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id.equals(hotel.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", starRating=" + starRating +
                '}';
    }
} 