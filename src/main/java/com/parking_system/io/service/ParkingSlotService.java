package com.parking_system.io.service;

import com.parking_system.io.factory.ParkingSlotFilterFactory;
import com.parking_system.io.models.ParkingSlot;
import com.parking_system.io.models.SlotFilterType;
import com.parking_system.io.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {
    @Autowired
    private ParkingSlotRepository parkingSlotRepository;
    @Autowired
    private ParkingSlotFilterFactory parkingSlotFilterFactory;

    public void createSlot(ParkingSlot parkingSlot){
        parkingSlotRepository.save(parkingSlot);
    }

    public List<ParkingSlot> getAllSlots(SlotFilterType filterType){
        return parkingSlotFilterFactory.getStrategy(filterType).filter();
    }

}
