package com.parking_system.io.strategy;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.VehicleType;
import com.parking_system.io.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AVAILABLE_AND_TYPE")
public class AvailableAndTypeFilterStrategy implements ParkingSlotFilterStrategy {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    private boolean available;
    private VehicleType type;

    public void setParams(boolean available, VehicleType type) {
        this.available = available;
        this.type = type;
    }

    @Override
    public List<ParkingSlot> filter() {
        return parkingSlotRepository.findByIsAvailableAndVehicleType(available, type);
    }
}

