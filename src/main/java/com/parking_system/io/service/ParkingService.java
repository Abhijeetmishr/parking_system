package com.parking_system.io.service;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.ParkingTicket;
import com.parking_system.io.models.Vehicle;
import com.parking_system.io.repository.ParkingRepository;
import com.parking_system.io.repository.ParkingSlotRepository;
import com.parking_system.io.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ParkingRepository ticketRepository;

    public ParkingTicket parkVehicle(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        ParkingSlot slot = parkingSlotRepository.findByVehicleType(vehicle.getVehicleType())
                .stream().findFirst().orElseThrow(() -> new IllegalArgumentException("slot not found"));

        // Mark slot as occupied
        slot.setAvailable(false);
        parkingSlotRepository.save(slot);

        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setParkingSlot(slot);
        ticket.setEntryTime(LocalDateTime.now());

        ParkingTicket savedTicket = ticketRepository.save(ticket);

        LOG.info("Vehicle {} parked in slot {}", vehicle.getLicensePlate(), slot.getId());
        return savedTicket;
    }

    public ParkingTicket unparkVehicle(UUID ticketId) {
        ParkingTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getExitTime() != null)
            throw new IllegalStateException("Vehicle already unparked");

        ParkingSlot slot = ticket.getParkingSlot();
        slot.setAvailable(true);
        parkingSlotRepository.save(slot);

        ticket.setExitTime(LocalDateTime.now());
        ticketRepository.save(ticket);

        LOG.info("Vehicle {} unparked from slot {}", ticket.getVehicle().getLicensePlate(), slot.getId());
        return ticket;
    }

    // 🎫 GET TICKET DETAILS
    public ParkingTicket getTicket(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }
}

