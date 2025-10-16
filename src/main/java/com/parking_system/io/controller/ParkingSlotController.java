package com.parking_system.io.controller;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.SlotFilterType;
import com.parking_system.io.models.VehicleType;
import com.parking_system.io.service.ParkingSlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/slots")
public class ParkingSlotController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingSlotController.class);

    @Autowired
    private ParkingSlotService parkingSlotService;

    // Create a new parking slot
    @PostMapping
    public ResponseEntity<?> createSlot(@RequestBody ParkingSlot slot) {
        LOG.info("Received request to create parking slot: {}", slot.getSlotNumber());

        try {
            parkingSlotService.createSlot(slot);
            LOG.info("Successfully created parking slot: {}", slot.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Slot created successfully"));
        } catch (Exception e) {
            LOG.error("Error creating parking slot", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create parking slot"));
        }
    }

    // List all parking slots (with optional filters)
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) VehicleType type) {

        LOG.info("Fetching all slots with filters - available: {}, type: {}", available, type);

        try {
            List<ParkingSlot> slots = parkingSlotService.getAllSlots(available, type);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            LOG.error("Error fetching parking slots", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

