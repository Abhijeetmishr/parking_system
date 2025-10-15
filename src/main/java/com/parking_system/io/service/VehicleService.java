package com.parking_system.io.service;

import com.parking_system.io.models.Vehicle;
import com.parking_system.io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public void register(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(String licensePlate) {
       return vehicleRepository.findByLicensePlate(licensePlate);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
