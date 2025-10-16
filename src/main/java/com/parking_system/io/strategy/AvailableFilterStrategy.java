package com.parking_system.io.strategy;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.VehicleType;
import com.parking_system.io.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AVAILABLE")
public class AvailableFilterStrategy implements ParkingSlotFilterStrategy {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    private boolean available;

    public void setParams(boolean available) {
        this.available = available;
    }

    @Override
    public List<ParkingSlot> filter() {
        return parkingSlotRepository.findByIsAvailable(available);
    }
}

