package com.parking_system.io.repository;

import com.parking_system.io.models.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<ParkingTicket, UUID> {
}
