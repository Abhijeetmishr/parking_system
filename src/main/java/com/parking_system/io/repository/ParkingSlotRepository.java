package com.parking_system.io.repository;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {
    List<ParkingSlot> findByIsAvailable(boolean isAvailable);
    List<ParkingSlot> findByVehicleType(VehicleType vehicleType);
    List<ParkingSlot> findByIsAvailableAndVehicleType(boolean isAvailable, VehicleType vehicleType);
}
