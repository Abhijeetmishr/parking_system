package com.parking_system.io.controller;

import com.parking_system.io.models.Vehicle;
import com.parking_system.io.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private static final Logger LOG = LoggerFactory.getLogger(VehicleController.class);
    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle) {
        LOG.info("Received request to register vehicle with license plate: {}", vehicle.getLicensePlate());

        try {
            vehicleService.register(vehicle);
            LOG.info("Successfully registered vehicle: {}", vehicle.getLicensePlate());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "vehicle registered"));
        }
        catch (IllegalArgumentException e) {
            LOG.error("Validation error while registering vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
        catch (Exception e) {
            LOG.error("Unexpected error while registering vehicle", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong while saving the vehicle."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        LOG.info("Received request to fetch vehicle with ID: {}", id);

        try {
            Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);

            if (vehicle.isEmpty()) {
                LOG.warn("No vehicle found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehicle not found"));
            }

            LOG.info("Successfully fetched vehicle with ID: {}", id);
            return ResponseEntity.ok(vehicle);
        }
        catch (IllegalArgumentException e) {
            LOG.error("Invalid vehicle ID provided: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
        catch (Exception e) {
            LOG.error("Unexpected error while fetching vehicle with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong while retrieving the vehicle."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        LOG.info("Received request to list all vehicles");

        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();

            if (vehicles.isEmpty()) {
                LOG.info("No vehicles found in the system");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("message", "No vehicles registered yet", "data", List.of()));
            }

            LOG.info("Successfully fetched {} vehicles", vehicles.size());
            return ResponseEntity.ok(vehicles);
        }
        catch (Exception e) {
            LOG.error("Unexpected error while fetching all vehicles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch vehicles due to an internal error"));
        }
    }
}
