package com.parking_system.io.strategy;

import com.parking_system.io.models.ParkingSlot;

import java.util.List;

public interface ParkingSlotFilterStrategy {
    public List<ParkingSlot> filter();
}
