package com.parking_system.io.factory;

import com.parking_system.io.models.SlotFilterType;
import com.parking_system.io.strategy.ParkingSlotFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParkingSlotFilterFactory {
    private final Map<String, ParkingSlotFilterStrategy> strategies;

    public ParkingSlotFilterFactory(Map<String, ParkingSlotFilterStrategy> strategies) {
        this.strategies = strategies;
    }

    public ParkingSlotFilterStrategy getStrategy(SlotFilterType type) {
        return strategies.getOrDefault(type.name(), strategies.get("ALL"));
    }
}

