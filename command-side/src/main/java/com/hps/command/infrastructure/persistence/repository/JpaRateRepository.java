package com.hps.command.infrastructure.persistence.repository;

import com.hps.command.infrastructure.persistence.entity.JpaRate;
import com.hps.command.infrastructure.persistence.entity.JpaRoomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for Rate entities.
 */
@Repository
public interface JpaRateRepository extends JpaRepository<JpaRate, UUID> {
    
    /**
     * Find a rate by hotel ID and name.
     *
     * @param hotelId The hotel ID
     * @param name The rate name
     * @return An Optional containing the rate, or empty if not found
     */
    @Query("SELECT r FROM JpaRate r JOIN JpaRoomType rt ON r.roomTypeId = rt.id WHERE rt.hotelId = :hotelId AND r.name = :name")
    Optional<JpaRate> findByHotelIdAndName(
            @Param("hotelId") UUID hotelId,
            @Param("name") String name);
            
    /**
     * Find a room type by its ID.
     *
     * @param id The room type ID
     * @return An Optional containing the room type, or empty if not found
     */
    @Query("SELECT rt FROM JpaRoomType rt WHERE rt.id = :id")
    Optional<JpaRoomType> findRoomTypeById(@Param("id") UUID id);
} 