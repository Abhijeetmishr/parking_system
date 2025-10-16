package com.parking_system.io.strategy;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.VehicleType;
import com.parking_system.io.repository.ParkingSlotRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("TYPE")
public class TypeFilterStrategy implements ParkingSlotFilterStrategy {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Setter
    private VehicleType type;

    @Override
    public List<ParkingSlot> filter() {
        return parkingSlotRepository.findByVehicleType(type);
    }
}

