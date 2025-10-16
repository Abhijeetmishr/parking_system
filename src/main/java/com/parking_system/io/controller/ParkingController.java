package com.parking_system.io.controller;

import com.parking_system.io.models.ParkingTicket;
import com.parking_system.io.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ParkingController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private ParkingService parkingService;

    // 🚗 PARK VEHICLE
    @PostMapping("/park")
    public ResponseEntity<?> parkVehicle(@RequestBody ParkingTicket parkingTicket) {
        try {
            ParkingTicket ticket = parkingService.parkVehicle(parkingTicket.getVehicle().getLicensePlate());
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOG.error("Failed to park vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            LOG.error("Unexpected error while parking vehicle", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/unpark/{ticketId}")
    public ResponseEntity<?> unparkVehicle(@PathVariable UUID ticketId) {
        try {
            ParkingTicket ticket = parkingService.unparkVehicle(ticketId);
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOG.error("Failed to unpark: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            LOG.error("Unexpected error while unparking", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    // 🎫 GET TICKET DETAILS
    @GetMapping("/tickets/{id}")
    public ResponseEntity<?> getTicket(@PathVariable UUID id) {
        try {
            ParkingTicket ticket = parkingService.getTicket(id);
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException e) {
            LOG.error("Ticket not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}

