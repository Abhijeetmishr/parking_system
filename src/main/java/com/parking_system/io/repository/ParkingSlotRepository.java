package com.parking_system.io.repository;

import com.parking_system.io.models.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {

}
