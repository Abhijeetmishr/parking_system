package com.parking_system.io.factory;

import com.parking_system.io.models.SlotFilterType;
import com.parking_system.io.models.VehicleType;
import com.parking_system.io.repository.ParkingSlotRepository;
import com.parking_system.io.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParkingSlotFilterFactory {

    private final AvailableAndTypeFilterStrategy availableAndTypeStrategy;
    private final AvailableFilterStrategy availableFilterStrategy;
    private final TypeFilterStrategy typeFilterStrategy;
    private final NoFilterStrategy noFilterStrategy;

    // All strategies are injected via constructor
    @Autowired
    public ParkingSlotFilterFactory(
            AvailableAndTypeFilterStrategy availableAndTypeStrategy,
            AvailableFilterStrategy availableFilterStrategy,
            TypeFilterStrategy typeFilterStrategy,
            NoFilterStrategy noFilterStrategy
    ) {
        this.availableAndTypeStrategy = availableAndTypeStrategy;
        this.availableFilterStrategy = availableFilterStrategy;
        this.typeFilterStrategy = typeFilterStrategy;
        this.noFilterStrategy = noFilterStrategy;
    }

    public ParkingSlotFilterStrategy getStrategy(Boolean available, VehicleType type) {
        if (available != null && type != null) {
            availableAndTypeStrategy.setParams(available, type);
            return availableAndTypeStrategy;
        } else if (available != null) {
            availableFilterStrategy.setParams(available);
            return availableFilterStrategy;
        } else if (type != null) {
            typeFilterStrategy.setType(type);
            return typeFilterStrategy;
        } else {
            return noFilterStrategy; // no filter → return all
        }
    }
}

