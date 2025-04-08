package com.hps.command.api;

import com.hps.command.application.PriceCommandService;
import com.hps.common.dto.UpdatePriceCommand;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.validation.Valid;

/**
 * REST controller for price commands.
 */
@RestController
@RequestMapping("/api/v1/prices")
public class PriceCommandController {
    
    private final PriceCommandService priceCommandService;
    
    public PriceCommandController(PriceCommandService priceCommandService) {
        this.priceCommandService = priceCommandService;
    }
    
    /**
     * Update or create a price.
     *
     * @param command The price update command
     * @return The ID of the created or updated price
     */
    @PostMapping
    public ResponseEntity<UUID> updatePrice(@Valid @RequestBody UpdatePriceCommand command) {
        UUID priceId = priceCommandService.handleUpdatePrice(command);
        return new ResponseEntity<>(priceId, HttpStatus.CREATED);
    }
} 