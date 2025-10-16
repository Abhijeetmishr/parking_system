package com.parking_system.io.strategy;

import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoFilterStrategy implements ParkingSlotFilterStrategy {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Override
    public List<ParkingSlot> filter() {
        return parkingSlotRepository.findAll();
    }
}

